package cn.nnu.jyjs.knowledgegraph.domain.roles;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "OUT")
public class RoleOut extends Role{
    @Id
    @GeneratedValue
    private Long relationshipId;
    @Property(name = "name")
    private String type;        //  类型
    @Property(name = "description")
    private String desciption ; //  描述

    @StartNode
    private Node starter;
    @EndNode
    private Node ender;

    public RoleOut(){}

    public RoleOut(Node starter, Node ender,String type) {
        this.type = type;
        this.starter = starter;
        this.ender = ender;
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
