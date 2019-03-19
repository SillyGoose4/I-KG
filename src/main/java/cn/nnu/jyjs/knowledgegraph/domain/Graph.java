package cn.nnu.jyjs.knowledgegraph.domain;

public class Graph {
    private String source;
    private String target;
    private String rela;
    private String type;

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
}
