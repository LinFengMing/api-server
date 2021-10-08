package com.esha.apiserver.util;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientUtil {

    public static ResponseEntity get() {
        WebClient client = WebClient.create();
        ResponseEntity<String> response = client.get()
                .uri("http://218.32.244.236/RESTful/index.php/v1/get/reporting/cdr/view/list?start=0&limit=100")
                .header("X-frSIP-API-Token", "8050bedf7c0cfcb1894b183f70ad249bbccc3dc0d74ef74b55fc8cbc57d72101")
                .retrieve()
                .toEntity(String.class)
                .block();
        return response;
    }

    public static String post() {
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        bodyValues.add("callbknum", "3000");
        bodyValues.add("destnum", "1191101");
        bodyValues.add("destname", "test");
        bodyValues.add("secret", "216e8d767eb0ada74ed3eac23a1b2f18");

        WebClient client = WebClient.create();
        String response = client.post()
                .uri("http://218.32.244.236/RESTful/index.php/v1/post/frsipswitchboard/callback/trigger")
                .header("X-frSIP-API-Token", "8050bedf7c0cfcb1894b183f70ad249bbccc3dc0d74ef74b55fc8cbc57d72101")
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }

}
