package cn.nnu.jyjs.knowledgegraph;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import cn.nnu.jyjs.knowledgegraph.service.Neo4JUtils;
import cn.nnu.jyjs.knowledgegraph.service.NodeRepository;
import cn.nnu.jyjs.knowledgegraph.service.NodeService;
import cn.nnu.jyjs.knowledgegraph.tools.FileReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KnowledgegraphApplicationTests {

    @Autowired
    NodeService nodeService;

    private Logger logger = Logger.getLogger("TEST");

    @Test
    public void testFind(){
        Node n = nodeService.findByName("无理数");

        System.out.println(n.getNatureStr());
    }
/*
    @Test
    public void testInsert() throws IOException {
        List<String> ls = null;
        try {
            ls = FileReader.loadCSV("math_entity.csv");
            for (String s:
                    ls) {
                Node node = new Node();
                node.setNatureStr(s);
                node.setLabel("##数学##");
                node.setBaikeUrl("https://baike.baidu.com/search/word?word="+s);
                node.setDescription("##非算法提取，需要补充##");
                node.setProperty("None");
                if(nodeService.saved(node))
                    logger.info("========Saved "+ node.getNatureStr()+" succeed! ");
                else logger.warning("WARNING!  ===  Already has");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddRela() throws IOException{
        List<String> ls = null;
        ls = FileReader.loadCSV("math_relationship.csv");
        for(String s:ls){
            String[] ss = s.split(",");
            Node n1 = nodeService.findByName(ss[0]);
            Node n2 = nodeService.findByName(ss[2]);
            if(n1 == null){
                n1 = new Node();
                n1.setNatureStr(ss[0]);
                n1.setLabel("##数学##");
                n1.setBaikeUrl("https://baike.baidu.com/search/word?word="+ss[0]);
                n1.setDescription("##非算法提取，需要补充##");
                n1.setProperty("None");
                nodeService.saved(n1);
            }
            if(n2 == null){
                n2 = new Node();
                n2.setNatureStr(ss[2]);
                n2.setLabel("##数学##");
                n2.setBaikeUrl("https://baike.baidu.com/search/word?word="+ss[2]);
                n2.setDescription("##非算法提取，需要补充##");
                n2.setProperty("None");
                nodeService.saved(n2);
            }
            //Neo4JUtils utils = new Neo4JUtils();
            //if(!ss[1].equals("无关"))
             //   utils.addRelation(n1,n2,ss[1]);
            //nodeRepo.insertRelaByExist(n1.getNatureStr(),n2.getNatureStr(),ss[1]);
            if(nodeService.addLinked(n1,n2,ss[1]))
                logger.info("============Saved the relation "+ss[1]+" between "+ n1.getNatureStr() +" and "+ n2.getNatureStr() + " Succeed!");
            else
                logger.warning("WARNING! === NOT A Relation!");
        }
    }
*/

}
