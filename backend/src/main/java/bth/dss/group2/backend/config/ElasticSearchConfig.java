package bth.dss.group2.backend.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@ComponentScan(basePackages = "bth.dss.group2.backend.service")
public class ElasticSearchConfig{
    @Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

    @Value("${elasticsearch.clustername}")
    private String EsClusterName;

    public String getEsHost() {
        return EsHost;
    }
    public int getEsPort() {
        return EsPort;
    }

    @Bean
    public Client client(){
        TransportClient client = null;
        try{

            System.out.println("host:"+ EsHost +" port:"+ EsPort +" clustername:" +EsClusterName);
            client = new PreBuiltTransportClient(Settings.builder().put("cluster.name","elasticsearch").build())
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(EsHost), EsPort));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean(name = {"elasticsearchTemplate"})
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }

//Embedded Elasticsearch Server
/** @Bean
public ElasticsearchOperations elasticsearchTemplate() {
return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
}** */
}
