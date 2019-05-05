package cn.nnu.jyjs.knowledgegraph.domain;

import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    private String natureStr;

    private String description;

    private String property;

    private String baikeUrl;

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

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    @Relationship(type = "CONNECTED", direction = Relationship.DIRECTION)
    public Set<Node> nodes;

    public void addRelationship(Node node) {
        if(this.nodes == null){
            nodes = new HashSet<>();
        }
        nodes.add(node);
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
        return Objects.equals(nodeId, node.nodeId) &&
                natureStr.equals(node.natureStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, natureStr);
    }
}
