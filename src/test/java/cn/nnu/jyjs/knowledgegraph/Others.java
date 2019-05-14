package cn.nnu.jyjs.knowledgegraph;

import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;
import cn.nnu.jyjs.knowledgegraph.domain.roles.RELATION;

import java.util.LinkedList;
import java.util.List;

public class Others {

    enum TEST{
        t1("t1N"),
        t2("t2N"),
        ;


        TEST(String t) {

        }
    }

    public static void main(String[] args){
        String s = "被依赖";
        RELATION relation = RELATION.fromName(s);
        switch (relation){
            case essential:
                System.out.println("test1");
                break;
            case b_belong:
                System.out.println("test2");
                break;
                case b_rely:
                System.out.println("test3");
                break;
            case b_attr:
                System.out.println("test4");
                break;
        }
    }
}
