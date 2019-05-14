package cn.nnu.jyjs.knowledgegraph;

import cn.nnu.jyjs.knowledgegraph.domain.SContent;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableNeo4jRepositories
public class KnowledgegraphApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgegraphApplication.class, args);
        SContent sContent = SContent.getInstance();
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri("http://39.108.107.28:7474")
                .credentials("neo4j", "SillyGoose4d.")
                .build();
        return configuration;
    }

    @Bean
    public SessionFactory sessionFactory() {
        // with domain entity base package(s)
        return new SessionFactory(configuration(), "cn.nnu.jyjs.knowledgegraph.domain");
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("30MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("300MB");
        return factory.createMultipartConfig();
    }

}
