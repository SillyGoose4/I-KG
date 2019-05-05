package cn.nnu.jyjs.knowledgegraph.tools;

import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;

import java.util.*;

/**
 * 关系抽取算法
 * created by wangj
 * created in 3/12/2019
 *  老是出错，玛德没有用
 *  最后的挣扎 ： 4/11/2019
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *
 */
public class Apriori {
    //frequent item sets

    private static List<List<String>> record = new ArrayList<>();

    private static double MIN_SUPPORT = 0.06;                   // 最小支持度

    private static double MIN_CONFIDENCE = 0.6;                 // 最小置信度

    private static int SIZE = 0;

    public Apriori(){}

    public Apriori(List<List<String>> D){
        //this.D = D;
    }

    public static void setMinSupport(double minSupport){
        MIN_SUPPORT = minSupport;
    }

    public static void setData(List<List<String>> Data){

    }


    /**
     * 连接步，由L1得到候选项集C2
     */
    private static List<data> generatorC2(Map<String,Integer> lItemset){
        List<String> out=new ArrayList<String>();

        List<String> list=new ArrayList<String>();//存储L1中的项，使用list便于遍历
        Map<String[],Integer> C2ItemSet=new HashMap<String[],Integer>();//存储候选2-项集及其支持度
        List<String[]> combineList=new ArrayList<String[]>();//存储自连接产生的候选2-项集
        Iterator<Map.Entry<String, Integer>> it = lItemset.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            list.add(key);
        }
        //遍历list
        for(int i=0;i<list.size();i++){
            for(int j=i+1;j<list.size();j++){
                String[] itemset=new String[2];//存储产生的候选2-项集
                itemset[0]=list.get(i);
                itemset[1]=list.get(j);
                combineList.add(itemset);
            }
        }
        //System.out.println("候选2-项集为：");
        //遍历combineList,将L2ItemSet中的支持度都设为0
        for(int i=0;i<combineList.size();i++){
            //System.out.println(combineList.get(i)[0]+","+combineList.get(i)[1]);
            C2ItemSet.put(combineList.get(i), 0);
        }
        //计算候选2-项集的支持度
        for(int i=0;i<record.size();i++){//事务型记录
            for(int j=0;j<combineList.size();j++){//候选2-项集
                if(record.get(i).contains(combineList.get(j)[0])&& record.get(i).contains(combineList.get(j)[1])){
                    //System.out.println(i+":"+combineList.get(j)[0]+","+combineList.get(j)[1]);
                    C2ItemSet.put(combineList.get(j), C2ItemSet.get(combineList.get(j)).intValue()+1);
                }
            }
        }
        //获取频繁2-项集L2
        Map<String[],Integer> L2ItemSet=getSupported2ItemSet(C2ItemSet);
        //输出频繁2-项集
        //计算频繁2-项集的置信度  P(A|B)和P(B|A)，用AB共同出现的次数除以A或B出现的次数
        //即频繁2-项集的支持度除以频繁1-项集的支持度
        //存储最终结果
        List<data> resultList=new ArrayList<data>();
        Iterator<Map.Entry<String[], Integer>> it5 = L2ItemSet.entrySet().iterator();
        while (it5.hasNext()) {
            data result=new data(); //存储一条记录
            Map.Entry entry = (Map.Entry) it5.next();
            String[] key = (String[]) entry.getKey();
            int value=(int)entry.getValue();
            result.setName(key);
            result.setSup(value);//支持度
            //置信度1
            double conf1=value/lItemset.get(key[0]).doubleValue();
            //置信度2
            double conf2=value/lItemset.get(key[1]).doubleValue();
            result.setConf1(conf1);
            result.setConf2(conf2);
            resultList.add(result);
        }
        return resultList;
        /*
        for(int i=0;i<resultList.size();i++){
            double conf1=resultList.get(i).getConf1();
            double conf2=resultList.get(i).getConf2();
            if(conf1>=0.8 && conf1>conf2 ){
                //表示第2个概念包含第1个概念
                //System.out.println("2包含1上下位关系对："+resultList.get(i).getName()[0]+","+resultList.get(i).getName()[1]+"  "+conf1+"  "+conf2);
                out.add("2包含1上下位关系对："+resultList.get(i).getName()[0]+","+resultList.get(i).getName()[1]+"  "+conf1+"  "+conf2);
                continue;
            }
            if(conf2>=0.8 && conf2>conf1 ){
                //表示第1个概念包含第2个概念
                //System.out.println("1包含2上下位关系对："+resultList.get(i).getName()[0]+","+resultList.get(i).getName()[1]+"  "+conf2+"  "+conf1);
                out.add("1包含2上下位关系对："+resultList.get(i).getName()[0]+","+resultList.get(i).getName()[1]+"  "+conf2+"  "+conf1);
                continue;
            }
        }*/
        //return out;
    }

    /**
     * @param cItemset
     * 求出cItemset中满足最低支持度集合,频繁1-项集
     */
    private static Map<String,Integer> getSupportedItemset(Map<String,Integer> cItemset) {
        System.out.println("最小支持度为："+MIN_SUPPORT*(record.size()));
        Map<String,Integer> supportedItemset = new HashMap<String,Integer>();
        Iterator<Map.Entry<String, Integer>> iter = cItemset.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            int value=(int) entry.getValue();
            if(value>=MIN_SUPPORT*(record.size())){
                supportedItemset.put(key, value);
            }
        }
        return supportedItemset;
    }
    /**
     * 求出C2ItemSet中满足最小支持度集合，频繁2-项集
     */
    private static Map<String[],Integer> getSupported2ItemSet(Map<String[],Integer> C2ItemSet){
        Map<String[],Integer> L2ItemSet=new HashMap<String[],Integer>();
        System.out.println("最小支持度为："+MIN_SUPPORT*(record.size()));
        Iterator<Map.Entry<String[], Integer>> it3 = C2ItemSet.entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            String[] key = (String[]) entry.getKey();
            int value=(int) entry.getValue();
            if(value>=MIN_SUPPORT*(record.size())){
                L2ItemSet.put(key, value);
            }
        }
        return L2ItemSet;
    }
    /**
     *根据数据库记录求出候选项集C1，得到支持度计数
     */
    private static HashMap<String,Integer> findFirstCandidate() {

        HashMap<String,Integer> lineList = new HashMap<String,Integer>();//存储每个项及其计数
        //多少条记录
        for (int i = 0; i < record.size(); i++) {
            for (int j = 0; j < record.get(i).size(); j++) {
                //System.out.println(record.get(i).get(j));
                if (!lineList.containsKey(record.get(i).get(j))) {
                    //如果不包含此项，则将其加入到list中
                    lineList.put(record.get(i).get(j),1);
                }else{
                    //如果包含此项，则计数加1
                    int count=lineList.get(record.get(i).get(j)).intValue();//获取当前项的计数
                    lineList.put(record.get(i).get(j),count+1);
                }
            }
        }

        return lineList;
    }

    /**
     * 留给外部的接口，直接调用这个就行
     * 由于只返回二元关系，所以只使用一次计算频繁项集即可
     * @param Data 二维数据列表，行：文件的关键词，列：所有文件
     * @return List<List<Vocabulary>> 同返回二元列表，行：有关联的关键词，列：所有数据
     */
    public static List<List<Vocabulary>> calc(List<List<String>> Data){
        setData(Data);
        List<List<Vocabulary>> res = new LinkedList<>();
        record = Data;// 获取原始数据记录
        HashMap<String,Integer> cItemset = findFirstCandidate();// 获取候选1-项集
        Map<String,Integer> lItemset = getSupportedItemset(cItemset);// 获取频繁1-项集
        List<data> result =generatorC2(lItemset);
        for (data d:result){
            List<Vocabulary> ls = new LinkedList<>();
            ls.add(new Vocabulary(d.getName()[0]));
            ls.add(new Vocabulary(d.getName()[1]));
            res.add(ls);
        }
        return res;
    }


    /**
     * 以下无用  我是闲得了做快排。。。。。
     * @param list
     * @return
     */
    public List<String> sort(List<String> list){
        quickSort(0, list.size() - 1,list);
        return list;
    }

    private void quickSort(int left, int right, List<String> strings){
        if(left < right){
            int p = position(left, right,strings);
            quickSort(left, p - 1,strings);
            quickSort(p+1, right, strings);
        }
    }

    /**
     * find median position
     * @param left
     * @param right
     * @param list
     * @return
     */
    private int position(int left, int right, List<String> list){
        String t = list.get(right);
        int i = left - 1;
        for(int j=left; j<right; j++){
            if(list.get(j).compareTo(t)<0){
                i++;
                String s = list.get(i);
                list.set(i,list.get(j));
                list.set(j,s);
            }
        }
        String s = list.get(i+1);
        list.set(i+1,t);
        list.set(right,s);
        return i+1;
    }
    /*
    i = p-1;
    for(j=p to r-1){
        if(A[j] <= x){
            i = i+1;
            exchange A[i] with A[j];
        }
    }
    exchange A[i+1] with A{r]
    return i+1;

    t = A[r];
    l = left;
    r = right;
    while(l<r){
        while(A[l] < t && l<r)
            l++;
        while(A[r] > t && l<r)
            r++;
        if(l<r)
            swap(A[l],A[r])
    }
    */

}
/*
class Item{
    String natureStr;   //字符串
    Integer freq;       //频次
    Long id;
    public Item(String natureStr, Integer freq) {
        this.natureStr = natureStr;
        this.freq = freq;
    }

    public Item(String natureStr) {
        this.natureStr = natureStr;
        this.freq = 1;
    }

    public String getNatureStr() {
        return natureStr;
    }

    public void setNatureStr(String natureStr) {
        this.natureStr = natureStr;
    }

    public Integer getFreq() {
        return freq;
    }

    public void setFreq(Integer freq) {
        this.freq = freq;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}*/
class data {

    private String[] name;//存储概念对
    private int sup;//支持度
    private double conf1;//置信度P(A|B)
    private double conf2;//置信度P（B|A）
    public String[] getName() {
        return name;
    }
    public void setName(String[] name) {
        this.name = name;
    }
    public int getSup() {
        return sup;
    }
    public void setSup(int sup) {
        this.sup = sup;
    }
    public double getConf1() {
        return conf1;
    }
    public void setConf1(double conf1) {
        this.conf1 = conf1;
    }
    public double getConf2() {
        return conf2;
    }
    public void setConf2(double conf2) {
        this.conf2 = conf2;
    }

}
