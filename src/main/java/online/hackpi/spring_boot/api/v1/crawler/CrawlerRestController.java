package online.hackpi.spring_boot.api.v1.crawler;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import org.typesense.api.Client;
import org.typesense.api.Configuration;
import org.typesense.model.ApiKey;
import org.typesense.model.ApiKeySchema;
import org.typesense.resources.Node;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/web-crawler")
@RequiredArgsConstructor
@EnableScheduling
public class CrawlerRestController {
    private final CrawlerService crawlerService;
    @Value("${Typesense.api-key}")
    private String typesense_api_key;
    @GetMapping("/scrap")
    public ResponseEntity<?> scrap(@RequestParam String url) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(crawlerService.scrapAllPagesInAWebApp(url));
    }
    @GetMapping("/web-search")
    public ResponseEntity<?> search(@RequestParam(name = "s") String s) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(crawlerService.searchedResults(s));
    }
    @GetMapping("/test")
    public ResponseEntity<?> test() throws Exception {
//        Create new client
        List<Node> nodes = new ArrayList<>();
        nodes.add(
                new Node(
                        "http", // For Typesense Cloud use https
                        "136.228.158.126",  // For Typesense Cloud use xxx.a1.typesense.net
                        "3200"        // For Typesense Cloud use 443
                )
        );
        Configuration configuration = new Configuration(nodes, Duration.ofSeconds(2),typesense_api_key);
        Client client = new Client(configuration);
//
        ApiKeySchema apiKeySchema = new ApiKeySchema();
        List<String> actionValues = new ArrayList<>();
        List<String> collectionValues = new ArrayList<>();
//
        actionValues.add("*");
        collectionValues.add("*");
        apiKeySchema.description("Admin Key").actions(actionValues).collections(collectionValues);
//
//        actionValues.add("documents:search");
//        collectionValues.add("*");
//        apiKeySchema.description("Search only Key").actions(actionValues).collections(collectionValues);

        ApiKey apiKey = client.keys().create(apiKeySchema);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiKey);
    }
}
