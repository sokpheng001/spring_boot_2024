package online.hackpi.spring_boot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.typesense.api.Client;
import org.typesense.resources.Node;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TypesenseClientConfig {
    @Value("${Typesense.api-key}")
    private String typesense_api_key;
    public Client clientConfig(){
        List<Node> nodes = new ArrayList<>();
        nodes.add(
                new Node(
                        "http", // For Typesense Cloud use https
                        "136.228.158.126",  // For Typesense Cloud use xxx.a1.typesense.net
                        "3200"        // For Typesense Cloud use 443
                )
        );
        org.typesense.api.Configuration configuration = new org.typesense.api.Configuration(nodes, Duration.ofSeconds(2),typesense_api_key);
        return new Client(configuration);
    }
}
