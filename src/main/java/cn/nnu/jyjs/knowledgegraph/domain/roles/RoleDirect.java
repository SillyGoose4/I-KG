package cn.nnu.jyjs.knowledgegraph.domain.roles;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "DIRECTION")
public class RoleDirect extends Role{
    @Id
    @GeneratedValue
    private Long relationshipId;
    @Property(name = "name")
    private String type;        //  类型
    @Property(name = "description")
    private String desciption ; //  描述

    @StartNode @JsonIgnore // 防止无限循环
    private Node starter;
    @EndNode
    private Node ender;

    public RoleDirect(){}

    public RoleDirect(Node starter, Node ender, String type) {
        this.type = type;
        this.starter = starter;
        this.ender = ender;
    }

    public RoleDirect(String type, String desciption, Node starter, Node ender) {
        this.type = type;
        this.desciption = desciption;
        this.starter = starter;
        this.ender = ender;
    }

    private void getType(){
        type = this.TYPE;
        desciption = this.DECP;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Node getStarter() {
        return starter;
    }

    public void setStarter(Node starter) {
        this.starter = starter;
    }

    public Node getEnder() {
        return ender;
    }

    public void setEnder(Node ender) {
        this.ender = ender;
    }
}
