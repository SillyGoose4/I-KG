package cn.nnu.jyjs.knowledgegraph.domain;

import java.util.*;

public class SContent {
    private static SContent instance;
    private static Map<String, Map<String,String>> contents;
    private SContent(){
        this.contents = new LinkedHashMap<>();
    }
    public static SContent getInstance(){
        if(instance != null){
            return instance;
        }else{
            synchronized (SContent.class){
                instance = new SContent();
            }
        }
        return instance;
    }
    public static void setData(String sessionId, String fileName,String content){
        if(contents.get(sessionId) == null) {
            Map<String, String> c = new HashMap<>();
            c.put(fileName,content);
            contents.put(sessionId, c);
        }else{
            Map<String,String> c = contents.get(sessionId);
            c.put(fileName,content);
        }
    }
    public static Map<String,String> getMap(String sessionId){
        return contents.get(sessionId);
    }
    public static String getContent(String sessionId, String name){
        return contents.get(sessionId).get(name);
    }
    /*
    public static String getContent(String sessionId){
        return contents.get(sessionId);
    }*/
    public static List<String> getFileName(String sessionId){
        Set<String> strings=contents.get(sessionId).keySet();
        if(strings == null){
            return null;
        }
        List<String> list = new LinkedList<>();
        for (String s:
             strings) {
            list.add(s);
        }
        return list;
    }
}
