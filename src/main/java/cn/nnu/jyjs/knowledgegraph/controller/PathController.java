package cn.nnu.jyjs.knowledgegraph.controller;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import cn.nnu.jyjs.knowledgegraph.service.NodeService;
import cn.nnu.jyjs.knowledgegraph.tools.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * url controller, all method return html page.
 */
@Controller
@RequestMapping(value = "/")
public class PathController {

    NodeService nodeService;

    private Logger logger = Logger.getLogger("TEST");

    @RequestMapping(value = "index")
    public String skipIndex(){
        return "index";
    }

    @RequestMapping(value = "participate")
    public String skipParticipate(){
        return "participate";
    }

    @RequestMapping(value = "duel")
    public String skipDuel(){
        return "duel";
    }

    @RequestMapping(value = "tfidf")
    public String skipTfidf(){
        return "tfidf";
    }

    @RequestMapping(value = "apriori")
    public String skipApriori(){
        return "apriori";
    }

    @RequestMapping(value = "about")
    public String skipAbout(){
        return "about";
    }

    @RequestMapping(value = "textmatch")
    public String skipTextMatch(){
        return "textmatch";
    }

    @RequestMapping(value = "frequency")
    public String skipFrequency(){
        return "frequency";
    }

    @RequestMapping(value = "Graphshow")
    public String skipGraph(){
        return "Graphshow";
    }

    @RequestMapping(value = "MathShow")
    public String skipMath1Graph(){
        return "MathGraph";
    }

    @RequestMapping(value = "PhysicsShow")
    public String skipPhysical1Graph(){
        return "PhysicalGraph";
    }

    @RequestMapping(value = "ForTest")
    public void skip(){
        List<String> ls = null;
        try {
            ls = FileReader.loadCSV("math_entity.csv");
            for (String s:
                    ls) {
                Node node = new Node();
                node.setNatureStr(s);
                node.setLabel("##数学##");
                node.setBaikeUrl("http://baike.baidu.com/");
                node.setDescription("/*非算法提取，需要补充*/");
                node.setProperty("None");
                if(nodeService.saved(node))
                    logger.info("========Saved "+ node.getNatureStr()+" succeed! ");
                logger.warning("WARNING!  ===  Already has");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
