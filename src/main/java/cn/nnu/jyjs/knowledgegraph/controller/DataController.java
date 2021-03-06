package cn.nnu.jyjs.knowledgegraph.controller;

import cn.nnu.jyjs.knowledgegraph.Interface.VocabularySort;
import cn.nnu.jyjs.knowledgegraph.domain.*;
import cn.nnu.jyjs.knowledgegraph.service.Beans;
import cn.nnu.jyjs.knowledgegraph.service.NodeRepository;
import cn.nnu.jyjs.knowledgegraph.service.NodeService;
import cn.nnu.jyjs.knowledgegraph.tools.Apriori;
import cn.nnu.jyjs.knowledgegraph.tools.FileReader;
import cn.nnu.jyjs.knowledgegraph.tools.ParticipleProcessing;
import cn.nnu.jyjs.knowledgegraph.tools.TFIDF;
import com.hankcs.hanlp.HanLP;
import net.sf.json.JSONArray;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

/**
 * data controller, all request return json data.
 */
@RestController
@RequestMapping(value = "/data/", produces = "application/json; charset=utf-8")
public class DataController {

    NodeService nodeService;

    private Logger logger = Logger.getLogger("DataController===");
    /**
     * for test
     * @return
     */
    @RequestMapping(value = "GraphData")
    public String getGraphData(){
        List<Graph> ls = new LinkedList<>();


        JSONArray jsonArray = JSONArray.fromObject(ls);
        return jsonArray.toString();
    }

    @RequestMapping(value = "Math1Data")
    public String getMath1GraphData() throws IOException {
        List<Graph> ls = FileReader.loadWords("math1.csv");
        // 从数据库中读

        JSONArray jsonArray = JSONArray.fromObject(ls);
        return jsonArray.toString();
    }

    @RequestMapping(value = "PhysicalData")
    public String getPhy1GraphData() throws IOException {
        List<Graph> ls = FileReader.loadWords("physical1.csv");
        // 同上
        JSONArray jsonArray = JSONArray.fromObject(ls);
        return jsonArray.toString();
    }


    /**
     * --NO USE--
     * @param fileName
     * @param request
     * @return
     */
    @RequestMapping(value="postText",method = RequestMethod.POST)
    public String postText(@RequestBody String fileName,
                           HttpServletRequest request){
        System.out.println(fileName);

        //SContent.setData(request.getSession().getId(),fileName);
        return "Success";
    }

    /**
     * 上传文件，一般为多文件
     * @param request 要求包含参数"file"
     * @return
     */
    @RequestMapping(value="upload",method = RequestMethod.POST)
    public String handleFileUpload(HttpServletRequest request) {
        List<String> fileNames = new LinkedList<>();
        HttpSession session = request.getSession();
        JSONArray json;
        Map<String,String> m = getFile(request);
        for(Map.Entry e : m.entrySet()) {
            SContent.setData(session.getId(), (String)e.getKey(), (String)e.getValue());
            fileNames.add((String)e.getKey());
        }
        json = JSONArray.fromObject(fileNames);
        return json.toString();
    }

    /**
     * 只能处理utf-8 纯文本
     * @param request
     * @return
     */
    private Map<String, String> getFile(HttpServletRequest request){
        try {
            Map<String, String> name_content = new LinkedHashMap<>();
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            for (MultipartFile multipartFile : files) {
                String sName = multipartFile.getOriginalFilename();
                logger.info(multipartFile.getContentType());
                //InputStreamReader ioReader = new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8);
                //char[] buf = new char[1024];
                //StringBuilder builder = new StringBuilder();
                //int i = 0;
                //while((i = ioReader.read(buf,0,buf.length)) < 0){
                //    builder.append(buf,0,i);
                //}
                String sContent = new String(multipartFile.getBytes(),0,multipartFile.getBytes().length, StandardCharsets.UTF_8);
                logger.info(sContent);
                name_content.put(sName,sContent);
            }
            return name_content;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件名列表
     * @return fileNameList
     */
    @RequestMapping(value = "/fileName")
    public String getNames(HttpServletRequest request){
        List<String> fileNames = SContent.getFileName(request.getSession().getId());
        JSONArray jsonArray = JSONArray.fromObject(fileNames);
        return jsonArray.toString();
    }

    /**
     * 获取对应文件的分词结果
     * @param fileName 文件名
     * @param request List
     * @return
     */
    @RequestMapping(value = "participate",method = RequestMethod.GET)
    public String getPart(@RequestParam(value = "fileName") String fileName, HttpServletRequest request){
        HttpSession session = request.getSession();
        String content = SContent.getContent(session.getId(),fileName);
        Assmble assmble = ParticipleProcessing.processing(content);
        List<String> words = new LinkedList<>();
        for(Vocabulary vocabulary : assmble.getWords()){
            words.add(vocabulary.getNatureStr());
        }
        JSONArray jsonArray = JSONArray.fromObject(words);

        return jsonArray.toString();
    }

    /**
     *
     * @param fileName
     * @param request
     * @return
     */
    @RequestMapping(value = "frequency",method = RequestMethod.GET)
    public String getFreq(@RequestParam(value = "fileName") String fileName,HttpServletRequest request){
        HttpSession session = request.getSession();
        String content = SContent.getContent(session.getId(),fileName);
        Assmble assmble = ParticipleProcessing.processing(content);
        List<String> words = new LinkedList<>();
        List<Vocabulary> vocabularies = assmble.getWords().subList(0,assmble.getWords().size());
        vocabularies.sort(new VocabularySort());
        for(Vocabulary vocabulary : vocabularies){
            words.add(vocabulary.getNatureStr());
            words.add(": "+vocabulary.getFrequence());
            words.add("\n");
        }
        JSONArray jsonArray = JSONArray.fromObject(words);

        return jsonArray.toString();
    }

    @RequestMapping(value="duel",method = RequestMethod.GET)
    public String getDuel(@RequestParam(value = "fileName") String fileName,HttpServletRequest request){
        String content = SContent.getContent(request.getSession().getId(),fileName);
        List<Vocabulary> words = ParticipleProcessing.duelJoint(ParticipleProcessing.processing(content),15);
        JSONArray jsonArray = JSONArray.fromObject(words);
        return jsonArray.toString();
    }

    @RequestMapping(value = "tfidf",method = RequestMethod.GET)
    public String getTfidf(@RequestParam(value = "fileName") String fileName,
                           @RequestParam(value = "_threahold", required = false) Double _threa,
                           HttpServletRequest request){
        String content = SContent.getContent(request.getSession().getId(),fileName);
        TFIDF.setFileByMap(SContent.getMap(request.getSession().getId()));
        JSONArray jsonArray = JSONArray.fromObject(_t(_threa,fileName,content));
        return jsonArray.toString();
    }

    @RequestMapping(value = "calcKey",method = RequestMethod.POST)
    public String calcKey(@RequestParam(value = "fileName") String sName,
                          @RequestParam(value = "_threahold",required = false) Double _threa,
                          HttpServletRequest request){
        HttpSession session = request.getSession();
        String content = SContent.getContent(session.getId(),sName);
        Map<String,String> map = getFile(request);
        TFIDF.setFileByMap(map);
        JSONArray jsonArray = JSONArray.fromObject(_t(_threa,sName,content));
        return jsonArray.toString();
    }

    /**
     * 公共过程， 计算关键词
     * @param _threa    阈值
     * @param sName     文本名称
     * @param content   文本内容
     * @return
     */
    private List<Vocabulary> _t(Double _threa, String  sName, String content){
        if(_threa != null){
            TFIDF.setThreshold(_threa);
        }
        KeyWordComputer kwc = new KeyWordComputer(5);
        Collection<Keyword> result = kwc.computeArticleTfidf(sName, content);
        List<Vocabulary> lists =TFIDF.CalcKey(sName,null);
        for(Keyword k:result){
            Vocabulary v = new Vocabulary();
            v.setNatureStr(k.getName());
            lists.add(v);
        }
        addGraphNode(lists);
        return lists;
    }

    /**
     * 计算关系的
     * @param _threa
     * @param fileName
     * @param request
     * @return
     */
    private List<List<Vocabulary>> _link(Double _threa, List<String> fileName, HttpServletRequest request){
        List<List<String>> d = new LinkedList<>();
        HttpSession session = request.getSession();
        for (String s:
             fileName) {
            List<Vocabulary> vocabularies = _t(_threa, s,SContent.getContent(session.getId(),s));
            List<String> keys = new LinkedList<>();
            for (Vocabulary v:
                 vocabularies) {
                keys.add(v.getNatureStr());
            }
            d.add(keys);
        }
        List<List<Vocabulary>> map = Apriori.calc(d);

        return map;

    }

    /**
     * 添加图结点（实体）
     * @param vocabularies
     */
    private void addGraphNode(List<Vocabulary> vocabularies){
        for(Vocabulary v:vocabularies){
            //nodeRepo.save(new Node(v));
        }
    }

    /**
     * 添加图连结（关系）
     * @param link
     */
    private void addGraphLink(List<List<Vocabulary>> link){
        for(List<Vocabulary> ls : link){
            //Node f = nodeRepo.findByNatureStr(ls.get(0).getNatureStr());
            for(Vocabulary v:ls){
               // Node n = nodeRepo.findByNatureStr(v.getNatureStr());
                //if(!v.getNatureStr().equals(f.getNatureStr())){
                    //f.addRelationship(n);
                //}
            }
        }
    }

    private void find(){

    }

    /**
     * 计算关系
     * @param _threa
     * @param request
     * @return
     */
    @RequestMapping(value = "relationship")
    public String relationship(@RequestParam(value = "_threa",required = false) Double _threa
                            ,HttpServletRequest request){
        List<List<Vocabulary>> ls=_link(_threa,SContent.getFileName(request.getSession().getId()),request);
        addGraphLink(ls);
        JSONArray jsonArray = JSONArray.fromObject(ls);
        return jsonArray.toString();
    }

    /**
     * 计算关键词通过Text Rank算法
     * @param fileName
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(value = "textRank")
    public String getWordsByTextRank(@RequestParam(value = "fileName") String fileName,
                                     @RequestParam(value = "size", required = false) Integer size,
                                     HttpServletRequest request){
        if(size == null)
            size = 6;
        String content = SContent.getContent(request.getSession().getId(),fileName);
        List<String> keywords = HanLP.extractKeyword(content,size);
        JSONArray jsonArray = JSONArray.fromObject(keywords);
        return jsonArray.toString();
    }

}
