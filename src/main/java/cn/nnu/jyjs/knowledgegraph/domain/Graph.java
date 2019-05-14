package cn.nnu.jyjs.knowledgegraph.domain;

/**
 * 主要是为了前端需求数据
 *
 */
public class Graph {
    private String source;
    private String target;
    private String rela;
    private String type;
    private Node s;
    private Node t;

    public Graph(String source, String target, String rela) {
        this.source = source;
        this.target = target;
        this.rela = rela;
    }

    public Graph(String source, String target, String rela, String type) {
        this.source = source;
        this.target = target;
        this.rela = rela;
        this.type = type;
    }

    public Graph(String rela, Node s, Node t) {
        this.rela = rela;
        this.s = s;
        this.t = t;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRela() {
        return rela;
    }

    public void setRela(String rela) {
        this.rela = rela;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getS() {
        return s;
    }

    public void setS(Node s) {
        this.s = s;
    }

    public Node getT() {
        return t;
    }

    public void setT(Node t) {
        this.t = t;
    }
}
