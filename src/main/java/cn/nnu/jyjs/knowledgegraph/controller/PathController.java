package cn.nnu.jyjs.knowledgegraph.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class PathController {

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
}
