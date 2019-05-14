package cn.nnu.jyjs.knowledgegraph.domain;

import cn.nnu.jyjs.knowledgegraph.domain.roles.Role;
import cn.nnu.jyjs.knowledgegraph.domain.roles.RoleDirect;
import cn.nnu.jyjs.knowledgegraph.domain.roles.RoleIn;
import cn.nnu.jyjs.knowledgegraph.domain.roles.RoleOut;
import net.sf.json.JSONObject;
import org.neo4j.ogm.annotation.*;

import java.util.*;

/**
 * linked neo4j to save and query data
 * contrast to Vocabulary，
 * created by wangj
 * create in 3/30/2019
 */
@NodeEntity
public class Node {
    @Id @GeneratedValue
    private Long nodeId;

    @Property(name = "natureStr")
    private String natureStr;

    @Property(name = "description")
    private String description;

    @Property(name = "property")
    private String property;

    @Property(name = "baikeUrl")
    private String baikeUrl;

    @Property(name = "label")
    private String label;

    @Labels
    private List<String> labels = new ArrayList<>();

    @Relationship(type = "IN", direction = Relationship.INCOMING)
    private Set<RoleIn> role_in;

    @Relationship(type = "OUT", direction = Relationship.OUTGOING)
    private Set<RoleOut> role_out;

    @Relationship(type = "DIRECTION", direction = Relationship.DIRECTION)
    private Set<RoleDirect> role_direct;


    public Node(){}

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNatureStr() {
        return natureStr;
    }

    public void setNatureStr(String natureStr) {
        this.natureStr = natureStr;
    }

    public String getBaikeUrl() {
        return baikeUrl;
    }

    public void setBaikeUrl(String baikeUrl) {
        this.baikeUrl = baikeUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRole_in(Set<RoleIn> role_in) {
        this.role_in = role_in;
    }

    public void setRole_out(Set<RoleOut> role_out) {
        this.role_out = role_out;
    }

    public void setRole_direct(Set<RoleDirect> role_direct) {
        this.role_direct = role_direct;
    }

    public Set<RoleIn> getRole_in() {
        return role_in;
    }

    public Set<RoleOut> getRole_out() {
        return role_out;
    }

    public Set<RoleDirect> getRole_direct() {
        return role_direct;
    }

    /**
     *
     * @param toNode
     * @param type
     */
    public void addRelationship(Node toNode, String type, Role role) {
        if(this.role_in == null)
            role_in = new HashSet<>();
        if(this.role_direct == null)
            role_direct = new HashSet<>();
        if(this.role_out == null)
            role_out = new HashSet<>();
        switch (type){
            case "IN":
                RoleIn roleIn = (RoleIn)role;
                roleIn.setEnder(toNode);
                roleIn.setStarter(this);
                this.role_in.add(roleIn);
                break;
            case "OUT":
                RoleOut roleOut = (RoleOut)role;
                roleOut.setEnder(toNode);
                roleOut.setStarter(this);
                this.role_out.add(roleOut);
                break;
            case "DIRECT":
                RoleDirect roleDirect = (RoleDirect) role;
                roleDirect.setEnder(toNode);
                roleDirect.setStarter(this);
                this.role_direct.add(roleDirect);
                //this.role_direct.add((Role));
                break;
        }
    }

    public boolean isLinked(Node ether){
        return this.role_direct.contains(ether) || this.role_out.contains(ether) || this.role_in.contains(ether);
    }


    public Node(Vocabulary v){
        this.natureStr = v.getNatureStr();
        this.description = v.getDesciption();
        this.property = v.getProperty();
    }

    public Node(Vocabulary v, String baikeUrl){
        this.natureStr = v.getNatureStr();
        this.description = v.getDesciption();
        this.property = v.getProperty();
        this.baikeUrl = baikeUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return
                natureStr.equals(node.natureStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, natureStr);
    }

    @Override
    public String toString(){
        JSONObject js = JSONObject.fromObject(this);
        return js.toString();
    }
}
/*
无用数据。。
@Relationship(type = "依赖", direction = Relationship.OUTGOING)
    public Set<Node> rely;

    @Relationship(type = "属于", direction = Relationship.OUTGOING)
    public Set<Node> belong;

    @Relationship(type = "同义", direction = Relationship.DIRECTION)
    public Set<Node> syno;

    @Relationship(type = "反义", direction = Relationship.DIRECTION)
    public Set<Node> anto;

    @Relationship(type = "近义", direction = Relationship.DIRECTION)
    public Set<Node> simi;

    @Relationship(type = "属性", direction = Relationship.DIRECTION)
    public Set<Node> Attr;

    @Relationship(type = "同位", direction = Relationship.DIRECTION)
    public Set<Node> appo;

    @Relationship(type = "其他", direction = Relationship.DIRECTION)
    public Set<Node> others;

    @Relationship(type = "none", direction = Relationship.DIRECTION)
    public Set<Node> None;

    @Relationship(type = "被依赖", direction = Relationship.DIRECTION)
    public Set<Node> brely;

    @Relationship(type = "包含", direction = Relationship.DIRECTION)
    public Set<Node> bbelong;

    @Relationship(type = "拥有", direction = Relationship.DIRECTION)
    public Set<Node> battr;
 */