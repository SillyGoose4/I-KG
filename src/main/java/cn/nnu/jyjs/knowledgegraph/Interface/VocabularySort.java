package cn.nnu.jyjs.knowledgegraph.Interface;

import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;

import java.util.Comparator;

/**
 * List Sort By Vocabulary Frequency
 * create by wangj
 * create in 3/29
 */
public class VocabularySort implements Comparator<Vocabulary> {

    @Override
    public int compare(Vocabulary o1, Vocabulary o2) {
        return o1.getFrequence() - o2.getFrequence();
    }
}
