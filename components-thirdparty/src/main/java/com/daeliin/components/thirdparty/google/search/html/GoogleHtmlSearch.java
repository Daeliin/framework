package com.daeliin.components.thirdparty.google.search.html;

import com.daeliin.components.thirdparty.google.search.GoogleSearchQuery;
import com.daeliin.components.thirdparty.google.search.GoogleSearchResult;
import org.jsoup.Jsoup;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.URI;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class GoogleHtmlSearch {

    private final RestTemplate restTemplate;

    @Inject
    public GoogleHtmlSearch(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Set<GoogleSearchResult> search(GoogleSearchQuery query) {
        String url = buildGoogleSearchQueryUrl(query);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("User-agent", "Daeliin");

        RequestEntity<String> httpEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, URI.create(url));

        String html = restTemplate.exchange(httpEntity, String.class).getBody();

        return Jsoup.parse(html)
            .select("h3.r a")
            .stream()
            .map(a -> a.attr("href"))
            .map(this::extractUrl)
            .filter(extractedUrl -> extractedUrl.startsWith("http"))
            .map(GoogleSearchResult::new)
            .collect(toSet());
    }

    private String buildGoogleSearchQueryUrl(GoogleSearchQuery query) {
        return new GoogleUrlBuilder()
            .withPath("search")
            .withParam("q", query.text)
            .withParam("num", String.valueOf(query.maxResults))
            .build();
    }

    private String extractUrl(String href){
        return href.split("&")[0].split("q=")[1];
    }
}
