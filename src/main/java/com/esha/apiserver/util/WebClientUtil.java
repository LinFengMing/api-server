package com.esha.apiserver.util;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

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

    public static Boolean download(String ext) throws IOException {
        String fileUrl = "https://10.10.10.170:8443/tts?text=" + ext + "&rate=8000";
        Path path = Paths.get("C:\\Users\\LIN\\Desktop\\api-server\\src\\main\\resources\\static\\wav\\2345.wav");

        HttpClient secure = HttpClient.create()
                .secure(t -> t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));

        WebClient client = (WebClient) WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(secure))
                .build();

        Mono<byte[]> monoContents = client
                .get()
                .uri(fileUrl)
                .retrieve()
                .bodyToMono(byte[].class);

        Files.write(path, Objects.requireNonNull(monoContents.share().block()), StandardOpenOption.CREATE);
        return true;
    }

}
