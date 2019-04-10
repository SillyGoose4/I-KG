package cn.nnu.jyjs.knowledgegraph.tools;

import cn.nnu.jyjs.knowledgegraph.Interface.VocabularySort;
import cn.nnu.jyjs.knowledgegraph.domain.Assmble;
import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.collections.map.LinkedMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 分词处理
 * create by wangj
 * create in 3/12/2019
 * LATEST UPGRADE 3/26: add threshold
 */
public class ParticipleProcessing {

    static Set<String> expectedNature = new HashSet<String>() {{add("n");add("v");add("vd");add("vn");add("vf");
        add("vx");add("vi");add("vl");add("vg");
        add("nt");add("nz");add("nw");add("nl");
        add("ng");add("userDefine"); }};

    private static double threshold = 0.008;

    private static List<String> stopWords = new LinkedList<>();

    private static List<String> noiseWord = new LinkedList<>();

    public ParticipleProcessing() throws IOException {
        this.loadWords(stopWords,"res/stoplist.txt");
        this.loadWords(noiseWord,"res/noiseWord.txt");
    }


    /**
     * this method include write into database and get result.
     * @param _str
     * @return
     */
	public static Assmble processing (String _str) {
        Assmble assmble = new Assmble();
        String str = _str ;
        Result result = ToAnalysis.parse(str); //分词结果的一个封装，主要是一个List<Term>的terms
        List<Term> terms = result.getTerms(); //拿到terms
        Map<String, Vocabulary> map = new LinkedHashMap<>();
        List<Vocabulary> lists = new LinkedList<>();
        for(int i=0; i<terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词,以词为key
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            Vocabulary v = new Vocabulary(word);
            v.setProperty(natureStr);
            lists.add(v);
            if(expectedNature.contains(natureStr)){
                if(!map.containsKey(word) ) {//新词
                    Vocabulary vocabulary =new Vocabulary(word,1);
                    vocabulary.setProperty(natureStr);
                    map.put(word, vocabulary);//存入map
                }else {
                    Vocabulary obj = map.get(word);
                    obj.setFrequence(obj.getFrequence()+1);
                }
                System.err.println(i+" "+word +"\\"+natureStr+"\t\t");
            }

        }
        assmble.setMaps(map);
        assmble.setBefore(lists);
        return assmble;
    }

    /**
     * this method is used to splicing Binary compound word
     * P(i)*P(i+1)
     * @param
     * @return
     */
    public static List<Vocabulary> duelJoint(Assmble as, double _thres){
        List<Vocabulary> result = new LinkedList<Vocabulary>();

        int sumDuel = as.getBefore().size()-1;
        List<Vocabulary> duelCondidate = combine(as.getBefore(),2);
        // calculate  whether vocabulary's frequence reach threshold value
        // filter
        for (Vocabulary v: duelCondidate) {
            double fre = 1.0*v.getFrequence();
            double[] wordFre = {0,0};
            for(int i=0; i<2; i++){
                wordFre[i] = 1.0*as.getMaps().get(v.getParticle()[i]).getFrequence()/sumDuel;
                System.err.println(wordFre[i]);
            }
            if(!clotting(wordFre[0],wordFre[1],v.getFrequence(),_thres*sumDuel)){
                System.out.println(" 去除拼接词 ： " + v.getNatureStr() + "    " + fre);
                continue;
            }
            System.out.println("       已选复合词 ： " + v.getNatureStr() + "    " + fre);

            //System.out.println(fre);
            if(fre >= threshold){
                System.out.println("this vocabulary : " + v.getNatureStr());
                System.out.println("this vocabulary's frequence : " + v.getFrequence());
                result.add(v);
            }
        }
        return result;
    }

    public static List<Vocabulary> combine(List<Vocabulary> lists, int _T){
        Map<String,Vocabulary> duelCandidate = new LinkedMap();
        for(int i=0; i <= lists.size()-_T;i++){
            StringBuffer str = new StringBuffer();String[] temp = new String[_T];
            boolean r = false;
            for(int j=0; j<_T; j++){
                temp[j] = lists.get(i+j).getNatureStr();
                if(!expectedNature.contains(lists.get(i+j).getProperty())){
                    r = true;
                }
                str.append(temp[j]);
            }
            if(r){
                continue;
            }
            //System.out.println(str.toString());
            if(!duelCandidate.containsKey(str.toString())){
                Vocabulary vocabulary = new Vocabulary(str.toString(),1);
                vocabulary.setProperty("complex");
                vocabulary.setParticle(temp);
                duelCandidate.put(str.toString(),vocabulary);
            }else{
                Vocabulary vocabulary = duelCandidate.get(str.toString());
                vocabulary.setFrequence(vocabulary.getFrequence() + 1);
            }
        }
        List<Vocabulary> res = new LinkedList<>();
        for(Map.Entry<String,Vocabulary> entry : duelCandidate.entrySet()){
            res.add(entry.getValue());
        }
        return res;
    }

    /**
     * 计算凝结度，形如AB的词 if P(A)*P(B)*threahold < P(AB)  则AB认为凝结度较高
     * @param word1Fre
     * @param word2Fre
     * @param allFre
     * @return
     */
    public static boolean clotting(double word1Fre,double word2Fre,double allFre,double _thres){
        return (allFre > (word1Fre * word2Fre * _thres) )? true : false;
    }

    /**
     * 加载文件如停用词、噪声词
     * @param words
     * @param path
     * @throws IOException
     */
    public void loadWords(List<String> words,String path) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        byte[] temp = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer();
        while (is.read(temp) != -1) {
            stringBuffer.append(new String(temp));
        }
        for (String s : stringBuffer.toString().split(System.lineSeparator())) {
            words.add(s);
        }
    }

    /**
     * 不可逆，噪声词、停用词去重
     * @param lists
     */
    public static void AutoClearn(List<Vocabulary> lists){
        for(Vocabulary vocabulary : lists){
            if(stopWords.contains(vocabulary.getNatureStr()) || noiseWord.contains(vocabulary.getNatureStr())){
                lists.remove(vocabulary);
            }
        }
    }

    /**
     * 不可逆，依照频率排序
     * @param lists
     */
    public static void sortByFreq(List<Vocabulary> lists){
        lists.sort(new VocabularySort());
    }

}

