package cn.nnu.jyjs.knowledgegraph;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import cn.nnu.jyjs.knowledgegraph.service.NodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KnowledgegraphApplicationTests {

    @Autowired
    NodeRepository nodeRepo;

    @Test
    public void contextLoads() {
        nodeRepo.deleteAll();
    }

    @Test
    public void test(){
        Iterable<Node> it =nodeRepo.findAll();
        for(Node n : it){

        }
    }

}
