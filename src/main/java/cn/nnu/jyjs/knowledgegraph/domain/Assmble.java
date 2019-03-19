package cn.nnu.jyjs.knowledgegraph.domain;

import org.ansj.domain.Term;

import java.util.List;
import java.util.Map;

/**
 * @Project :   participleTool
 * @Time :   12/18/2018 00:13
 * @Auther :   wangj
 * @Description:
 *  复合类，包含分词工具生成的terms
 *             自定义词的vocabulary
 */
public class Assmble {

    private List<Term> terms;

    private Map<String, Vocabulary> maps; //依据词名构建的词典

    private List<Vocabulary> words;


    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public Map<String, Vocabulary> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Vocabulary> maps) {
        this.maps = maps;
    }

    public List<Vocabulary> getWords() {
        return words;
    }

    public void setWords(List<Vocabulary> words) {
        this.words = words;
    }
}
