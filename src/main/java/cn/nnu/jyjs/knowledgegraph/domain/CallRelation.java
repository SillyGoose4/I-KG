package cn.nnu.jyjs.knowledgegraph.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "call")
public class CallRelation {

    public CallRelation() {
        this.name = "call";
    }


    public CallRelation(NodeEntity start, NodeEntity end, Long count) {
        this();
        this.startNode = start;
        this.endNode = end;
        this.count = count;
    }

    /**
     * Neo4j会分配的ID（节点唯一标识 当前类中有效）
     */
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 定义关系的起始节点 == StartNode
     */

    @StartNode
    private NodeEntity startNode;

    /**
     * 定义关系的终止节点 == EndNode
     */

    @EndNode
    private NodeEntity endNode;


    /**
     * 定义关系的属性
     */

    @Property(name = "count")
    private Long count;

    public NodeEntity getStartNode() {
        return startNode;
    }

    public void setStartNode(NodeEntity startNode) {
        this.startNode = startNode;
    }

    public NodeEntity getEndNode() {
        return endNode;
    }

    public void setEndNode(NodeEntity endNode) {
        this.endNode = endNode;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
