package com.esha.apiserver.service;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@Service
public class TtsService {

    @Resource
    FtpService ftpService;

    @Value("${tts.service}")
    private String service;

    @Value("${file.path}")
    private String filePath;

    @Value("${deltapath.ivr}")
    private int ivr;

    public void createWav(String ext) throws IOException {
        String fileUrl = service + "/tts?text=" + ext + "&rate=8000";
        Path path = Paths.get("src/main/resources/static/wav/" + ivr + ".wav");

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
    }

    public void uploadWav() {
        ftpService.upload(filePath + ivr + ".wav");
    }

}
