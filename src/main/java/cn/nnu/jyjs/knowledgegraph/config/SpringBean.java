package cn.nnu.jyjs.knowledgegraph.config;

import cn.nnu.jyjs.knowledgegraph.service.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringBean {

    private final NodeRepository repo;

    @Autowired
    public SpringBean(NodeRepository repo){
        this.repo = repo;
    }
}
