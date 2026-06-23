package com.library.app.book.config;

import com.library.app.book.client.GenreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Value("${services.genre-service.url}")
    private String genreServiceUrl;

    @Bean
    public GenreClient genreClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor oauth2Interceptor =
                new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);

        RestClient restClient = RestClient.builder()
                .baseUrl(genreServiceUrl)
                .requestInterceptor(oauth2Interceptor)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();

        return factory.createClient(GenreClient.class);
    }
}
