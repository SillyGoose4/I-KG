package cn.nnu.jyjs.knowledgegraph.controller;

import cn.nnu.jyjs.knowledgegraph.domain.Assmble;
import cn.nnu.jyjs.knowledgegraph.domain.Graph;
import cn.nnu.jyjs.knowledgegraph.domain.SContent;
import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;
import cn.nnu.jyjs.knowledgegraph.service.Beans;
import cn.nnu.jyjs.knowledgegraph.tools.Apriori;
import cn.nnu.jyjs.knowledgegraph.tools.ParticipleProcessing;
import cn.nnu.jyjs.knowledgegraph.tools.TFIDF;
import net.sf.json.JSONArray;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * data controller, all request return json data.
 */
@RestController
@RequestMapping(value = "/data/")
public class DataController {

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
        List<Graph> ls = loadWords("math1.csv");
        JSONArray jsonArray = JSONArray.fromObject(ls);
        return jsonArray.toString();
    }

    @RequestMapping(value = "PhysicalData")
    public String getPhy1GraphData() throws IOException {
        List<Graph> ls = loadWords("physical1.csv");
        JSONArray jsonArray = JSONArray.fromObject(ls);
        return jsonArray.toString();
    }

    public List<Graph> loadWords(String path) throws IOException {
        Resource resource = Beans.createResourceLoader().getResource("classpath:"+path);
        //InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        InputStream is = resource.getInputStream();
        List<Graph> graphs = new LinkedList<>();
        byte[] temp = new byte[1024];
        String s;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        if(is == null){
            System.err.println("null");
        }
        while ((s = bufferedReader.readLine()) != null) {
            String[] t = s.split(",");
            System.out.println(t[0]+"    "+t[1]);
            Graph graph = new Graph(t[0],t[1],"相关关系");
            graphs.add(graph);
        }
        return graphs;
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
        JSONArray json;
        Map<String,String> m = getFile(request);
        for(Map.Entry e : m.entrySet()) {
            SContent.setData(request.getSession().getId(), (String)e.getKey(), (String)e.getValue());
            fileNames.add((String)e.getKey());
        }
        json = JSONArray.fromObject(fileNames);
        return json.toString();
    }

    private Map<String, String> getFile(HttpServletRequest request){
        try {
            Map<String, String> name_content = new LinkedHashMap<>();
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            for (MultipartFile multipartFile : files) {
                String sName = multipartFile.getOriginalFilename();
                String sContent = new String(multipartFile.getBytes());
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
        String content = SContent.getContent(request.getSession().getId(),fileName);
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
        String content = SContent.getContent(request.getSession().getId(),fileName);
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
        JSONArray jsonArray = JSONArray.fromObject(_t(_threa,fileName));
        return jsonArray.toString();
    }

    @RequestMapping(value = "calcKey",method = RequestMethod.POST)
    public String calcKey(@RequestParam(value = "fileName") String sName,
                          @RequestParam(value = "_threahold",required = false) Double _threa,
                          HttpServletRequest request){
        Map<String,String> map = getFile(request);
        TFIDF.setFileByMap(map);
        JSONArray jsonArray = JSONArray.fromObject(_t(_threa,sName));
        return jsonArray.toString();
    }

    private List<Vocabulary> _t(Double _threa, String sName){
        if(_threa != null){
            TFIDF.setThreshold(_threa);
        }
        List<Vocabulary> lists =TFIDF.CalcKey(sName);

        return lists;
    }

    private Map<String, Integer> _link(Double _threa, List<String> fileName){
        List<List<String>> d = new LinkedList<>();
        for (String s:
             fileName) {
            List<Vocabulary> vocabularies = _t(_threa, s);
            List<String> keys = new LinkedList<>();
            for (Vocabulary v:
                 vocabularies) {
                keys.add(v.getNatureStr());
            }
            d.add(keys);
        }
        return Apriori.calc(d);

    }

    @RequestMapping(value = "relationship")
    public String relationship(@RequestParam(value = "_threa",required = false) Double _threa
                            ,HttpServletRequest request){

        JSONArray jsonArray = JSONArray.fromObject(_link(_threa,SContent.getFileName(request.getSession().getId())));
        return jsonArray.toString();
    }
}
