package com.esha.apiserver.util;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientUtil {

    public static ResponseEntity get(String url, String headerName, String headerValue) {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(url)
                .header(headerName, headerValue)
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    public static String post(String url, String headerName, String headerValue, MultiValueMap<String, String> bodyValues) {
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(url)
                .header(headerName, headerValue)
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
