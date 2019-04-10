package cn.nnu.jyjs.knowledgegraph.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;

/**
 * linked neo4j to save and query data
 * contrast to Vocabulary
 * created by wangj
 * create in 3/30/2019
 */
@org.neo4j.ogm.annotation.NodeEntity(label = "Node")
public class NodeEntity {
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
}
