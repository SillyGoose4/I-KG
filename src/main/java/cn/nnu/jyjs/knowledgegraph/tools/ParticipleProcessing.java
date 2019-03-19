package cn.nnu.jyjs.knowledgegraph.tools;

import cn.nnu.jyjs.knowledgegraph.domain.Assmble;
import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 分词处理
 */
public class ParticipleProcessing {

    static Set<String> expectedNature = new HashSet<String>() {{add("n");add("v");add("vd");add("vn");add("vf");
        add("vx");add("vi");add("vl");add("vg");
        add("nt");add("nz");add("nw");add("nl");
        add("ng");add("userDefine"); }};

    private static double threshold = 0.008;

    private List<String> stopWords = new LinkedList<>();

    private List<String> noiseWord = new LinkedList<>();

    public ParticipleProcessing() throws IOException {
        this.loadWords(stopWords,"stoplist.txt");
        this.loadWords(noiseWord,"noiseWord.txt");
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
        assmble.setTerms(terms);
        Map<String, Vocabulary> map = new TreeMap<String, Vocabulary>();
        for(int i=0; i<terms.size(); i++) {
            int frequence = 1;
            String word = terms.get(i).getName(); //拿到词,以词为key
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)){
                if(!map.containsKey(word) ) {//新词
                    map.put(word, new Vocabulary(word,frequence));//存入map
                }else {
                    Vocabulary obj = map.get(word);
                    obj.setFrequence(obj.getFrequence()+1);
                }
            }
        }
        List<Vocabulary> ls = new LinkedList<>();
        for (String key : map.keySet()) {
            Vocabulary obj = (Vocabulary)map.get(key);
            ls.add(obj);
            int newHZ=obj.getFrequence();//得到当前词频
            int dic_id;
            String newNatureStr =obj.getNatureStr();
            System.out.println("Key = " + key +",NatureStr = " + newNatureStr+ ", HZ = " +newHZ);
            //dic_id=DBUnits.tdicInsert(key,newNatureStr,newHZ);//得到该条记录的dic_id
            //DBUnits.tdicbelongInsert(class_id,dic_id); //插入tdicbelong表
        }
        assmble.setMaps(map);
        assmble.setWords(ls);
        return assmble;
    }

    /**
     * this method is used to Binary compound word splicing
     * P(i)*P(i+1)
     * @param terms
     * @return
     */
    public static List<Vocabulary> duelJoint(Assmble as){
        List<Term> terms = as.getTerms();
        Map<String,Vocabulary> duels = new LinkedHashMap<String,Vocabulary>();
        List<Vocabulary> result = new LinkedList<Vocabulary>();
        Map<String, Vocabulary> ones=as.getMaps();
        int sumDuel = 0;
	    for(int i=0; i < (terms.size() - 1); i++){
            String previous = terms.get(i).getName();
            String latter = terms.get(i+1).getName();
            if(expectedNature.contains(terms.get(i).getNatureStr()) && expectedNature.contains(terms.get(i+1).getNatureStr())) {
                String new_ = previous + latter;
                System.out.println(new_);
                if (!duels.containsKey(new_)) {
                    // not contain this key
                    Vocabulary duel = new Vocabulary(new_);
                    duels.put(previous + latter, duel);
                } else {
                    // contain this key
                    Vocabulary duel = (Vocabulary) duels.get(new_);
                    duel.setFrequence(duel.getFrequence() + 1);
                }
                sumDuel++;
            }
        }
	    System.out.println("总数："+sumDuel);
        // calculate  whether vocabulary's frequence reach threshold value
        // filter
        for (Map.Entry<String, Vocabulary> entry: duels.entrySet()) {
            double fre = 1.0*entry.getValue().getFrequence() / sumDuel;
            List<Term> term = ToAnalysis.parse(entry.getKey()).getTerms();
            if(ones.containsKey(term.get(0).getName()) && ones.containsKey(term.get(1).getName())){
                if(!clotting(1.0*ones.get(term.get(0).getName()).getFrequence()/ones.size(),1.0*ones.get(term.get(1).getName()).getFrequence()/ones.size(),fre)){
                    continue;
                }
                System.out.println("");
            }
            //System.out.println(fre);
            if(fre >= threshold){
                System.out.println("this vocabulary : "+entry.getValue().getNatureStr());
                System.out.println("this vocabulary's frequence : "+entry.getValue().getFrequence());
                result.add(entry.getValue());
            }
        }
        return result;
    }

    /**
     * 计算凝结度，形如AB=P(A)*P(B)*threahold<P(AB)则AB认为凝结度较高
     * @param word1Fre
     * @param word2Fre
     * @param allFre
     * @return
     */
    public static boolean clotting(double word1Fre,double word2Fre,double allFre){
        return (allFre > (word1Fre * word2Fre * 10) )? true : false;
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

    public static void AutoClearn(){

    }
}

