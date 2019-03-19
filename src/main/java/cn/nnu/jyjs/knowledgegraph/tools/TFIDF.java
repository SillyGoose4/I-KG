package cn.nnu.jyjs.knowledgegraph.tools;

import cn.nnu.jyjs.knowledgegraph.domain.Assmble;
import cn.nnu.jyjs.knowledgegraph.domain.FileContent;
import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * HOW TO USE：
 *      there are many files,READ TO STRING BUFFER,and ALL to use ParticipleProcessing.processing(string).
 *
 */
public class TFIDF {

    /**
     * USE FILE_NAME:FILE_CONTENT MAP to contribute a
     * @param fileName_Content
     * @return
     */
    public static List<FileContent> getC(Map<String, String> fileName_Content){
        List<FileContent> fl = new LinkedList<>();
        for(Map.Entry<String,String> entry : fileName_Content.entrySet()){
            FileContent fi = new FileContent();
            Assmble as =ParticipleProcessing.processing(entry.getValue());
            fi.setWords(as.getWords());
            ParticipleProcessing.duelJoint(as);
            fi.setStr(new StringBuffer(entry.getValue()));
            fi.setFilename(entry.getKey());
            fl.add(fi);
        }
        setFiles(fl);
        return fl;
    }

    // 所有文件转换成StringBuffer存入该列表
    private static Map<String,FileContent> files;

    private static double threshold = 5;

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

    public static List<Vocabulary> CalcKey(String fileName){
        FileContent fileContent = files.get(fileName);
        List<Vocabulary> res = fileContent.getWords();
        for (Vocabulary v: fileContent.getWords()){
            // TF * IDF
            double tfidf = v.getFrequence() * CalcOneIDF(v.getNatureStr());
            if(tfidf >= threshold && v.getNatureStr().length() > 1){
                System.out.println("Word "+v.getNatureStr()+",Calculate result:"+tfidf);
            }
        }
        return res;
    }
}
