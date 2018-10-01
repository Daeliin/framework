package com.daeliin.components.thirdparty.disqus;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class DisqusUrlBuilderTest {

    @Test
    public void shouldBuildAnUrl_withAuthPAthAndParams() {
        String url = new DisqusUrlBuilder()
            .withAuth(new DisqusAuthentication("key", "secret", "token"))
            .withPath("threads/list.json")
            .withParam("forum", "tomSearle")
            .withParam("limit", "100")
            .build();

        assertThat(url).isEqualTo("https://disqus.com/api/3.0/threads/list.json?api_key=key&api_secret=secret&access_token=token&forum=tomSearle&limit=100");
    }
}