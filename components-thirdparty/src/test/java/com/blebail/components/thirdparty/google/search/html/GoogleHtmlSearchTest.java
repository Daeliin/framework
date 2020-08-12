package com.blebail.components.thirdparty.google.search.html;

import com.blebail.components.thirdparty.google.search.GoogleSearchQuery;
import com.blebail.components.thirdparty.google.search.GoogleSearchResult;
import com.blebail.components.thirdparty.library.GoogleLibrary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public final class GoogleHtmlSearchTest {

    private GoogleHtmlSearch tested;

    @BeforeEach
    public void setUp() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        tested = new GoogleHtmlSearch(restTemplate);

        String googleSearchResultHtml = new String(Files.readAllBytes(Paths.get(GoogleLibrary.SEARCH_RESULT_HTML_PATH)));

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        String googleSearchUrl = new GoogleUrlBuilder()
            .withPath("search")
            .withParam("q", "test")
            .withParam("num", "5")
            .build();

        mockServer.expect(requestTo(googleSearchUrl))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(googleSearchResultHtml, MediaType.TEXT_HTML));
    }

    @Test
    public void shouldParseResults() {
        Set<GoogleSearchResult> results = tested.search(new GoogleSearchQuery("test", 5));

        assertThat(results).containsExactly(
            new GoogleSearchResult("https://fr.wikipedia.org/wiki/Test"),
            new GoogleSearchResult("https://fr.wiktionary.org/wiki/test"),
            new GoogleSearchResult("http://test.psychologies.com/"),
            new GoogleSearchResult("https://www.123test.fr/test-de-qi/")
        );
    }
}