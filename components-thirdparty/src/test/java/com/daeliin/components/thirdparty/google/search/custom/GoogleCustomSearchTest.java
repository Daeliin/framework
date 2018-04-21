package com.daeliin.components.thirdparty.google.search.custom;

import com.daeliin.components.thirdparty.google.search.GoogleSearchQuery;
import com.daeliin.components.thirdparty.google.search.GoogleSearchResult;
import com.daeliin.components.thirdparty.library.GoogleLibrary;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public final class GoogleCustomSearchTest {

    private GoogleCustomSearch googleCustomSearch;

    @Before
    public void setUp() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        googleCustomSearch = new GoogleCustomSearch(restTemplate);

        String googleSearchResultJson = new String(Files.readAllBytes(Paths.get(GoogleLibrary.SEARCH_RESULT_CUSTOM_PATH)));

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        String googleCustomSearchUrl = new GoogleApiUrlBuilder()
            .withPath("customsearch/v1")
            .withParam("q", "architects spotify")
            .withParam("num", "4")
            .withParam("key", "key")
            .withParam("cx", "5")
            .build();

        mockServer.expect(requestTo(googleCustomSearchUrl))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(googleSearchResultJson, MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldParseResults() {
        Set<GoogleSearchResult> results = googleCustomSearch.search(new GoogleSearchQuery("architects spotify", 4), "key", "5");

        assertThat(results).containsExactly(
            new GoogleSearchResult("https://open.spotify.com/artist/3ZztVuWxHzNpl0THurTFCv"),
            new GoogleSearchResult("https://www.fox-architects.com/projects/spotify/"),
            new GoogleSearchResult("https://www.slideshare.net/kevingoldsmith/how-spotify-builds-products-organization-architecture-autonomy-accountability"),
            new GoogleSearchResult("https://open.spotify.com/album/3OYBI2pM9l36LYVhsqeL8j")
        );
    }
}