package online.hackpi.spring_boot.api.v1.crawler.service;

import online.hackpi.spring_boot.api.v1.crawler.model.AllLinks;
import online.hackpi.spring_boot.api.v1.crawler.model.SearchedResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketException;
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
        Document doc = Jsoup.connect(domain).get();
        Elements images = doc.select("img[src]"); // Use Jsoup.connect() method to load HTML from a URL.
        for (Element image : images) {
            String imageUrl = image.attr("abs:src");  // Get absolute URL
            System.out.println(imageUrl);
        }
        return null;
    }
//    20%
    @Override
    public <P extends AllLinks> P scrapAllPagesInAWebApp(String url) throws IOException {
        // Extract href attributes and process them
        Set<String> allHrefs = new HashSet<>();
        Set<String> allHrefsFromFacebook = new HashSet<>();
        Set<String> allHrefsFromYoutube = new HashSet<>();
        try{
            String domain = checkIsWithHttpProtocol(url);
            Document doc = Jsoup.connect(domain).get();
            Elements allLinks = doc.select("a");

            for (Element link : allLinks) {
                String href = link.attr("abs:href");
                if (!href.isEmpty()) { // Optional: Filter out empty or null hrefs
//                if(href.contains("http")){
//                    allHrefs.add(href);
//                }else {
//                    allHrefs.add(domain+href);
//                }
                    if (!href.contains(".facebook") && !href.contains(".twitter") && !href.contains(".google")  && !href.contains(".youtube")) {
                        // do your modification with the link
                        allHrefs.add(href);
                    }
                    if(href.contains(".facebook")){
                        allHrefsFromFacebook.add(href);
                    }
                    if(href.contains(".youtube")){
                        allHrefsFromYoutube.add(href);
                    }
                }
            }
            return (P) new AllLinks(allHrefs, allHrefsFromFacebook,allHrefsFromYoutube);
        }catch (SocketException socketException){
            System.out.println(socketException.getMessage());
            allHrefs.add(socketException.getMessage());
        }
        return (P) new AllLinks(allHrefs,allHrefsFromFacebook,allHrefsFromYoutube);
    }

    @Override
    public SearchedResult searchedResults(String url) throws IOException {
        String domain = checkIsWithHttpProtocol(url);
//
        Document doc = Jsoup.connect(domain).get();
//
        Set<String> imagesSet = new HashSet<>();
        Set<String> videosSet = new HashSet<>();

        // Extract video URLs
        Elements videos = doc.select("video");
        System.out.println(videos.attr("video"));
        for (Element video : videos) {
            String videoUrl = video.attr("abs:src");
            videosSet.add(videoUrl);

            // Download logic here (consider copyright and fair use)
            // downloadVideo(videoUrl); // Implement download functionality
        }

        // Extract image URLs
        Elements images = doc.select("img[src]");
        for (Element image : images) {
            String imageUrl = image.attr("abs:src");
            imagesSet.add(imageUrl);

            // Download logic here (consider copyright and fair use)
            // downloadImage(imageUrl); // Implement download functionality
        }
        return new SearchedResult(imagesSet, videosSet);
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
