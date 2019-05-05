package cn.nnu.jyjs.knowledgegraph.service;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends CrudRepository<Node,Long> {
    Node findByNatureStr(String natureStr);
}
