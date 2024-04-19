package online.hackpi.spring_boot.api.v1.crawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Service
public class CrawlerServiceImp implements CrawlerService{
    @Override
    public Boolean checkIsKhmerWebsite(String url) {
        Document document = Jsoup.parse(url);
//        text analysis
        return false;
    }

    @Override
    public <T> T scrapContentOfAPage(String url) throws IOException {
        String domain = checkIsWithHttpProtocol(url);
        Document doc = Jsoup.parse(new URL(domain),1_000_000_000);
        Elements allLinks = doc.select("img");
        System.out.println(allLinks.attr("src"));
        return null;
    }
//    20%
    @Override
    public Set<String> scrapAllPagesInAWebApp(String url) throws IOException {
        String domain = checkIsWithHttpProtocol(url);
        Document doc = Jsoup.parse(new URL(domain),1_000_000_000);
        Elements allLinks = doc.select("a");
        // Extract href attributes and process them
        Set<String> allHrefs = new HashSet<>();
        for (Element link : allLinks) {
            String href = link.attr("href");
            if (!href.isEmpty()) { // Optional: Filter out empty or null hrefs
                if(href.contains("http")){
                    allHrefs.add(href);
                }else {
                    allHrefs.add(domain+href);
                }
            }
        }
        allHrefs.forEach(System.out::println);
        return allHrefs;
    }
//    check if your url has been included with HTTP protocol
    private String checkIsWithHttpProtocol(String url){
        String domain;
        if(!url.contains("http")){
            domain = "https://" + url;
        }else {
            domain = url;
        }
        return domain;
    }
}
