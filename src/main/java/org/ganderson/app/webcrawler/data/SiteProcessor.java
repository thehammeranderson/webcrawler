package org.ganderson.app.webcrawler.data;

import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.ganderson.app.webcrawler.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("prototype")
public class SiteProcessor {
    @Autowired
    HttpService httpService;

    private List<Page> mainList = new ArrayList<>();
    private Set<String> processedUrlMap = new HashSet<>();
    private String host;
    private String baseUrl;

    public List<Page> crawlSite(String url) throws InvalidUrlException, SiteNotFoundException, IOException, URISyntaxException {
        return crawlSite(url, null);
    }

    public List<Page> crawlSite(String url, Integer processUrlLimit) throws InvalidUrlException, SiteNotFoundException, IOException, URISyntaxException {
        validateUrl(url);
        baseUrl = url;
        try {
            host = new URL(url).getHost();
        } catch (MalformedURLException e) {
            throw new InvalidUrlException("invalid url");
        }

        processUrl(url, processUrlLimit);
        return mainList;
    }

    private void processUrl(String url, Integer processUrlLimit) throws IOException, SiteNotFoundException, URISyntaxException {
        // standardize urls so both versions will equate
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        List<String> urlsToProcess = new ArrayList<>();
        urlsToProcess.add(url);

        for (int count = 0; count < urlsToProcess.size(); count++) {
            if (processUrlLimit != null && count >= processUrlLimit) {
                break;
            }

            String thisUrl = urlsToProcess.get(count);

            processedUrlMap.add(thisUrl);
            Page page = httpService.parsePage(thisUrl);
            if (page == null) {
                return;
            }

            mainList.add(page);

            for (String linkUrl : page.getLinkUrls()) {
                try {
                    URI aUrl = new URI(linkUrl);

                    if (linkUrl.toLowerCase().startsWith("mailto:")) {
                        continue;
                    } else if (!linkUrl.startsWith("http://") && !linkUrl.startsWith("https://")) {
                        // handle relative urls
                        String concatChar = "";
                        if (!baseUrl.endsWith("/")) {
                            concatChar = "/";
                        }
                        if (!urlsToProcess.contains(baseUrl + concatChar + linkUrl)) {
                            urlsToProcess.add(baseUrl + concatChar + linkUrl);
                        }
                    } else if (host.contains(aUrl.getHost()) || aUrl.getHost().contains(host)) {
                        if (!urlsToProcess.contains(linkUrl)) {
                            urlsToProcess.add(linkUrl);
                        }
                    }
                } catch (URISyntaxException e) {
                    System.out.println("invalid URL: " + linkUrl);
                }
            }
        }
    }

    private void validateUrl(String url) throws InvalidUrlException {
        if (url == null) {
            throw new InvalidUrlException("url param required");
        }

        Pattern pattern = Pattern.compile("^(https?:\\/\\/)(www\\.){0,1}([\\w]+\\.)+[‌​\\w]{2,63}\\/?(\\w+\\/?)+$");
        Matcher matcher = pattern.matcher(url);
        if (!matcher.matches()) {
            throw new InvalidUrlException("invalid url");
        }
    }
}
