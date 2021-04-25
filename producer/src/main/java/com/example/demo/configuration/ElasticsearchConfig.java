package com.example.demo.configuration;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient restHighLevelClient=new RestHighLevelClient(
                RestClient.builder(new HttpHost("121.5.27.43",9200,"http"))
        );
            return restHighLevelClient;
    }

}
