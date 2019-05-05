package cn.nnu.jyjs.knowledgegraph.tools;

import cn.nnu.jyjs.knowledgegraph.domain.Assmble;
import cn.nnu.jyjs.knowledgegraph.domain.FileContent;
import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * HOW TO USE：
 *      there are many files,READ TO STRING BUFFER,and ALL to use ParticipleProcessing.processing(string).
 * create by wangj
 * in 3/12/2019
 * LATEST 3/25: add set file by map.
 */
public class TFIDF {

    /**
     * USE FILE_NAME:FILE_CONTENT MAP to contribute a
     * @param fileName_Content
     * @return
     */
    public static List<FileContent> setFileByMap(Map<String, String> fileName_Content){
        List<FileContent> fl = new LinkedList<>();
        for(Map.Entry<String,String> entry : fileName_Content.entrySet()){
            FileContent fi = new FileContent();
            List<Vocabulary> lists = new LinkedList<>();
            Assmble as =ParticipleProcessing.processing(entry.getValue()); // 分词
            lists.addAll(as.getWords());
            lists.addAll(ParticipleProcessing.duelJoint(as,_thres));// 复合词拼接(二元)
            fi.setWords(as.getWords()); // 存储词
            fi.setStr(new StringBuffer(entry.getValue()));
            fi.setFilename(entry.getKey());
            fl.add(fi);
        }
        setFiles(fl);
        return fl;
    }

    // 所有文件转换成StringBuffer存入该列表
    private static Map<String,FileContent> files;

    private static double _thres = 5;   // 复合词拼接阈值

    private static double threshold = 2.5;    // TFIDF阈值

    /**
     * building index from files
     * @param _files
     */
    public static void setFiles(List<FileContent> _files) {
        files = new LinkedHashMap<>();
        for(FileContent f:_files){
            files.put(f.filename,f);
        }
    }

    /**
     * NOT USE
     * @param fileName

    public static void CalcTF(String fileName){
        FileContent file = files.get(fileName);
        int length = file.words.size();         //获取一篇文章分得的词数
        for (Vocabulary w:
             file.words) {

        }
    }*/

    /**
     * log(D+1/D(x))
     * calculate one word IDF
     */
    public static double CalcOneIDF(String word){
        int Dx = 0;
        int D = files.size();
        for(Map.Entry<String,FileContent> ent : files.entrySet()){
            FileContent f = ent.getValue();
            for(Vocabulary j: f.getWords()){
                if(j.getNatureStr().equals(word)){
                    Dx++;
                }
            }
        }
        return Math.log(1.0*(D+1)/Dx);
    }

    /**
     * 直接从标题抽效果更好
     */
    public static List<Vocabulary> extractFromTitle(String title){
        List<Vocabulary> vocabularies = new LinkedList<>();
        JSONArray jsonArray = JSONArray.fromObject(BaiduApi.request(title));

        return vocabularies;
    }

    public static void setThreshold(double _threshold){
        threshold = _threshold;
    }

    public static List<Vocabulary> CalcKey(String fileName, Double weight){
        FileContent fileContent = files.get(fileName);
        List<Vocabulary> res = new LinkedList<>();
        int size = fileContent.getWords().size();
        if(weight == null)
            weight = 1.15;
        for (Vocabulary v: fileContent.getWords()){
            // TF * IDF
            double tfidf = v.getFrequence()*1.0 /size * weight * CalcOneIDF(v.getNatureStr()) * 100;
            if(tfidf >= threshold && v.getNatureStr().length() > 1){
                res.add(v);
                v.setTfidf(tfidf);
                System.out.println("Word "+v.getNatureStr()+",Calculate result:"+tfidf);
            }
        }
        return res;
    }

    public static double get_thres() {
        return _thres;
    }

    public static double getThreshold() {
        return threshold;
    }

    public static void set_thres(double _thres) {
        TFIDF._thres = _thres;
    }

    public static void setFiles(Map<String, FileContent> files) {
        TFIDF.files = files;
    }

}
