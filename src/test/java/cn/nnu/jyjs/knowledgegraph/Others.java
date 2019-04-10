package cn.nnu.jyjs.knowledgegraph;

import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;

import java.util.LinkedList;
import java.util.List;

public class Others {
    public static void main(String[] args){
        List<Vocabulary> lists = new LinkedList<>();
        String[] buf = new String[3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                buf [j] = "this is in "+i+" and "+j;
            }
            Vocabulary vocabulary = new Vocabulary("hi "+i);
            vocabulary.setParticle(buf);
            lists.add(vocabulary);
        }
        for(Vocabulary v : lists) {
            for (int i = 0; i < 3; i++) {
                System.out.println(v.getParticle()[i]);
            }
        }
    }
}
