package cn.nnu.jyjs.knowledgegraph.domain;

import org.ansj.domain.Term;

import java.util.LinkedList;
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


    private Map<String, Vocabulary> maps; //依据词名构建的词典

    private List<Vocabulary> before;

    public Map<String, Vocabulary> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Vocabulary> maps) {
        this.maps = maps;
    }

    // 去重后词
    public List<Vocabulary> getWords() {
        List<Vocabulary> list = new LinkedList<>();
        for (Map.Entry e:
             maps.entrySet()) {
            list.add((Vocabulary) e.getValue());
        }
        return list;
    }
    // 原词
    public void setBefore(List<Vocabulary> beforeWords) {
        this.before = beforeWords;
    }

    public List<Vocabulary> getBefore() {
        return before;
    }
}
