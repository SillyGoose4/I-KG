package cn.nnu.jyjs.knowledgegraph.tools;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 关系抽取算法
 * create by wangj
 * in 3/12/2019
 *
 */
public class Apriori {
    //frequent item sets
    private static List<List<String>> D = new LinkedList<>();

    private static Map<String, Integer> C = new HashMap<>();

    private static Map<String, Integer> L = new HashMap<>();

    private static double MIN_SUPPORT = 0.06;

    private static int SIZE = 0;

    public Apriori(){}

    public Apriori(List<List<String>> D){
        this.D = D;
    }

    public static void setMinSupport(double minSupport){
        MIN_SUPPORT = minSupport;
    }

    public static void setData(List<List<String>> Data){
        D = Data;
        SIZE = D.size();
    }

    /**
     * get candidate set
     * @return
     */
    public static Map<String, Integer> getC1(){
        for (List<String> it: D) {
            for(String s: it){
                if(C.containsKey(s)){
                    C.replace(s,C.get(s)+1);
                }else{
                    C.put(s,0);
                }
            }
        }

        return C;
    }

    /**
     * get frequent item sets L1 from candidate C1
     * set default min_support rate : 0.006
     * if you want to change min_support,PLEASE USE [setMinSupport(double min_support)] this function.
     * @return
     */
    public static Map<String, Integer> getL1(){
        int Counts = (int) (MIN_SUPPORT * SIZE);
        System.out.println(Counts);
        for (Map.Entry e :
                C.entrySet()) {
            if((int)e.getValue()>Counts){
                L.put((String)e.getKey(),(int)e.getValue());
                System.out.println(e.getKey()+"\t"+e.getValue());
            }
        }
        return L;
    }

    public static Map<String, Integer> calc(List<List<String>> Data){
        setData(Data);
        Map<String,Integer> C = getC1();
        Map<String,Integer> L = getL1();
        return L;
    }


    /**
     * 以下无用
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
