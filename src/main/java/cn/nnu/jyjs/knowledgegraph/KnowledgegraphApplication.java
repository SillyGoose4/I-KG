package cn.nnu.jyjs.knowledgegraph;

import cn.nnu.jyjs.knowledgegraph.domain.SContent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KnowledgegraphApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgegraphApplication.class, args);
        SContent sContent = SContent.getInstance();
    }

}
