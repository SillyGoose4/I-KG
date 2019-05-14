package cn.nnu.jyjs.knowledgegraph.service;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import cn.nnu.jyjs.knowledgegraph.config.Neo4jConf;
import org.neo4j.driver.v1.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 老是出现数据库中的数据消失，怀疑是没有持久化
 */
public class Neo4JUtils {

    private static Session session;

    static {

    }




    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private NodeService service;

    public boolean addRelation(Node start, Node end, String rela){
        String cql = String.format("MATCH (a:Node),(b:Node) WHERE a.natureStr = \"%s\" AND b.natureStr = \"%s\" CREATE (a)-[r:rela{name:\"%s\"}]->(b)",start.getNatureStr(),end.getNatureStr(),rela);
        Node t1 = this.query(start.getNatureStr());
        Node t2 = this.query(end.getNatureStr());
        if (t1 == null)
            nodeRepository.save(start);
        if(t2 == null)
            nodeRepository.save(end);
        Transaction transaction = session.beginTransaction(); //持久化
        transaction.run(cql);
        transaction.success();
        return true;
    }

    /**
     * 唯一索引：natureStr
     * 应该唯一
     *
     * 用人家的更好
     * @param natureStr
     * @return
     */
    public Node query(String natureStr){
        Node node = null;
        String cql = String.format("MATCH(n:Node) WHERE n.natureStr = \"%s\" RETURN n", natureStr);
        StatementResult result = session.run(cql);
        if(result.hasNext()){
            Record re = result.next();
            for(Value v : re.values()){
                org.neo4j.driver.v1.types.Node node1 = v.asNode();
                Map<String, Object> m = node1.asMap();
                node = new Node();
                node.setNatureStr((String) m.get("natureStr"));
                node.setBaikeUrl((String) m.get("baikeUrl"));
            }
            //node =
        }
        return node;
    }
}
