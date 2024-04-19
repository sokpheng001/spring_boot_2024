package online.hackpi.spring_boot.api.v1.crawler;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.crawler.service.CrawlerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/web-crawler")
@RequiredArgsConstructor
@EnableScheduling
public class CrawlerRestController {
    private final CrawlerService crawlerService;
    @GetMapping("/scrap")
    public ResponseEntity<?> scrap(@RequestParam String url) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(crawlerService.scrapContentOfAPage(url));
    }
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "s") String content){
        System.out.println(content);
        return null;
    }
}
