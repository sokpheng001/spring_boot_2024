package online.hackpi.spring_boot.api.v1.crawler;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.crawler.service.CrawlerService;
import online.hackpi.spring_boot.utils.TypesenseClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import org.typesense.api.Client;
import org.typesense.api.Configuration;
import org.typesense.model.ApiKey;
import org.typesense.model.ApiKeySchema;
import org.typesense.model.CollectionSchema;
import org.typesense.model.Field;
import org.typesense.resources.Node;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/v1/web-crawler")
@RequiredArgsConstructor
@EnableScheduling
public class CrawlerRestController {
    private final CrawlerService crawlerService;
    private final TypesenseClientConfig typesenseClientConfig;
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

    @PostMapping("/collections")
    public ResponseEntity<?> createCollection(@RequestBody CollectionSchema collectionSchema) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(typesenseClientConfig.clientConfig().collections().create(collectionSchema));
    }
    @PostMapping("/collections/{collection_name}/documents")
    public ResponseEntity<?> createDocumentOfCollection(
            @RequestBody Object object, @PathVariable(name = "collection_name") String collection_name
    ) throws Exception {
        HashMap<String, Object> document = new HashMap<>();
        document.put("id","124");
        document.put("data","Cambodian");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        typesenseClientConfig.clientConfig().collections(collection_name).documents().create(document)
                );
    }
    @GetMapping("/test")
    public ResponseEntity<?> test() throws Exception {
        CollectionSchema collectionSchema = new CollectionSchema();
        collectionSchema.name("")
                .addFieldsItem(new Field())
                .addFieldsItem(new Field())
                .addFieldsItem(new Field());

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
