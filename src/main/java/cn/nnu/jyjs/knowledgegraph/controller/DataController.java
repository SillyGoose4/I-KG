package cn.nnu.jyjs.knowledgegraph.controller;

import cn.nnu.jyjs.knowledgegraph.domain.Assmble;
import cn.nnu.jyjs.knowledgegraph.domain.Graph;
import cn.nnu.jyjs.knowledgegraph.domain.SContent;
import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;
import cn.nnu.jyjs.knowledgegraph.tools.ParticipleProcessing;
import cn.nnu.jyjs.knowledgegraph.tools.TFIDF;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/data/")
public class DataController {

    @RequestMapping(value = "GraphData")
    public String getGraphData(){
        List<Graph> ls = new LinkedList<>();
        ls.add(new Graph("数学","集合","上下位关系","resolved"));
        ls.add(new Graph("数学","函数","上下位关系","resolved"));
        ls.add(new Graph("代数","函数","上下位关系","resolved"));
        ls.add(new Graph("数学","几何","上下位关系","resolved"));
        ls.add(new Graph("数学","物理","相关关系"));
        JSONArray jsonArray = JSONArray.fromObject(ls);
        return jsonArray.toString();
    }

    @RequestMapping(value="postText",method = RequestMethod.POST)
    public String postText(@RequestBody String fileName,
                           HttpServletRequest request){
        System.out.println(fileName);

        //SContent.setData(request.getSession().getId(),fileName);
        return "Success";
    }
    @RequestMapping(value="upload",method = RequestMethod.POST)
    public String handleFileUpload(HttpServletRequest request) {
        try {
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            for (MultipartFile multipartFile : files) {
                String sName = multipartFile.getOriginalFilename();
                String sContent = new String(multipartFile.getBytes());
                System.out.println(sContent);
                System.out.println(sName);
                SContent.setData(request.getSession().getId(),sName,sContent);
            }
        }catch (IOException e){
            return "FAILURE";
        }
        return "SUCCESS";
    }

    @RequestMapping(value = "participate",method = RequestMethod.GET)
    public String getPart(HttpServletRequest request){
        String content = SContent.getContent(request.getSession().getId());
        Assmble assmble = ParticipleProcessing.processing(content);
        List<String> words = new LinkedList<>();
        for(Vocabulary vocabulary : assmble.getWords()){
            words.add(vocabulary.getNatureStr());
        }

        JSONArray jsonArray = JSONArray.fromObject(words);

        return jsonArray.toString();
    }
    @RequestMapping(value = "frequency",method = RequestMethod.GET)
    public String getFreq(HttpServletRequest request){
        String content = SContent.getContent(request.getSession().getId());
        Assmble assmble = ParticipleProcessing.processing(content);
        List<String> words = new LinkedList<>();
        for(Vocabulary vocabulary : assmble.getWords()){
            words.add(vocabulary.getNatureStr());
            words.add(": "+vocabulary.getFrequence());
            words.add("\n");
        }
        JSONArray jsonArray = JSONArray.fromObject(words);

        return jsonArray.toString();
    }

    @RequestMapping(value="duel",method = RequestMethod.GET)
    public String getDuel(HttpServletRequest request){
        String content = SContent.getContent(request.getSession().getId());
        List<Vocabulary> words = ParticipleProcessing.duelJoint(ParticipleProcessing.processing(content));
        JSONArray jsonArray = JSONArray.fromObject(words);
        return jsonArray.toString();
    }
    /*
    @RequestMapping(value = "tfidf",method = RequestMethod.GET)
    public String getTfidf(HttpServletRequest request){
        String content = SContent.getContent(request.getSession().getId());
        List<Vocabulary> words = TFIDF.
        JSONArray jsonArray = JSONArray.fromObject(words);
        return jsonArray.toString();

    }*/
}
