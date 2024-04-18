package online.hackpi.spring_boot.api.v1.crawler;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/web-crawler")
@RequiredArgsConstructor
public class CrawlerRestController {
    @GetMapping("/scrap")
    public ResponseEntity<?> scrap(@RequestParam String url) throws IOException {
        String domain = "https://" + url;
//
        Document doc = Jsoup.parse(new URL(domain),9000);
        Elements allLinks = doc.select("a");
        // Extract href attributes and process them
        List<String> allHrefs = new ArrayList<>();
        for (Element link : allLinks) {
            String href = link.attr("href");
            if (!href.isEmpty()) { // Optional: Filter out empty or null hrefs
                allHrefs.add(href);
            }
        }
//
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allHrefs);
    }
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "s") String content){
        System.out.println(content);
        return null;
    }
}
