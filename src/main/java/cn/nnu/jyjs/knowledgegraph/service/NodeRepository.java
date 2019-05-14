package cn.nnu.jyjs.knowledgegraph.service;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends Neo4jRepository<Node,Long> {

    Node findByNatureStr(String natureStr);

    @Query("match(s:Node),(e:Node) WHERE s.natureStr={start} and e.natureStr={end} CREATE p=(s)-[r:rela]->(e)")
    void insertRelaByExist(@Param("start") String start,@Param("end") String end,@Param("rela") String rela);
}
