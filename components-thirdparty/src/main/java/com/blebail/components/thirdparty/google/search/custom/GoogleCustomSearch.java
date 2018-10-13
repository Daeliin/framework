package com.blebail.components.thirdparty.google.search.custom;

import com.blebail.components.thirdparty.google.search.GoogleSearchQuery;
import com.blebail.components.thirdparty.google.search.GoogleSearchResult;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GoogleCustomSearch {

    private final RestTemplate restTemplate;

    @Inject
    public GoogleCustomSearch(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Set<GoogleSearchResult> search(GoogleSearchQuery query, String apiKey, String contextId) {
        String url = buildGoogleSearchQueryUrl(query, apiKey, contextId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("User-agent", "Blebail");

        RequestEntity<String> httpEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, URI.create(url));

        String json = restTemplate.exchange(httpEntity, String.class).getBody();
        List<String> links = JsonPath.read(json, "$.items[*].link");

        return links.stream()
            .map(GoogleSearchResult::new)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String buildGoogleSearchQueryUrl(GoogleSearchQuery query, String apiKey, String contextId) {
        Objects.requireNonNull(apiKey);
        Objects.requireNonNull(contextId);

        return new GoogleApiUrlBuilder()
            .withPath("customsearch/v1")
            .withParam("q", query.text)
            .withParam("num", String.valueOf(query.maxResults))
            .withParam("key", apiKey)
            .withParam("cx", contextId)
            .build();
    }
}
