package online.hackpi.spring_boot.api.v1.crawler.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

public interface CrawlerService {
    Boolean checkIsKhmerWebsite(String url);
    <T> T scrapContentOfAPage(String  url) throws IOException;
    Set<String> scrapAllPagesInAWebApp(String url) throws IOException;
}
