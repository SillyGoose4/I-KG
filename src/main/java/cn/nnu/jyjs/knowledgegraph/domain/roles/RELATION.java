package cn.nnu.jyjs.knowledgegraph.domain.roles;

public enum RELATION {
    rely("依赖"),
    belong("属于"),
    syno("同义"),
    anto("反义"),
    simi("近义"),
    attr("属性"),
    suffcient("充分条件"),
    essential("必要条件"),
    appo("同位"),
    other("其他"),
    none("无关"),
    b_rely("被依赖"),
    b_belong("包含"),
    b_attr("拥有")
    ;

    String sName;

    RELATION(){}

    RELATION(String s) {
        this.sName = s;
    }
    /**
     * 根据类型的名称，返回类型的枚举实例。 
     *
     * @param typeName 类型名称 
     */
    public static RELATION fromName(String typeName) {
        for (RELATION type : RELATION.values()) {
            if (type.getTypeName().equals(typeName)) {
                return type;
            }
        }
        return null;
    }

    private String getTypeName() {
        return this.sName();
    }

    private String sName() {
        return sName;
    }
}
