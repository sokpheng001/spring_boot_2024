package online.hackpi.spring_boot.api.v1.crawler.service;

import online.hackpi.spring_boot.api.v1.crawler.model.AllLinks;
import online.hackpi.spring_boot.api.v1.crawler.model.SearchedResult;

import java.io.IOException;
import java.util.Set;

public interface CrawlerService {
    Boolean checkIsKhmerWebsite(String url);
    <T> T scrapContentOfAPage(String  url) throws IOException;
    <P extends AllLinks> P scrapAllPagesInAWebApp(String url) throws IOException;
    SearchedResult searchedResults(String url) throws IOException;
}
