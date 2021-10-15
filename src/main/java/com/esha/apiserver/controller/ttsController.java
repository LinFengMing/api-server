package com.esha.apiserver.controller;

import com.esha.apiserver.dto.AlarmDto;
import com.esha.apiserver.service.DeltapathService;
import com.esha.apiserver.service.TtsService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/tts")
public class ttsController {

    @Resource
    TtsService ttsService;

    @Resource
    DeltapathService deltapathService;

    @PostMapping(path = "/alarm", produces = "application/json")
    public ResponseEntity alarmCall(@RequestBody @Valid AlarmDto alarmDto) {
        JSONObject obj = new JSONObject();
        obj.put("status", "OK");
        obj.put("code", 200);
        obj.put("message", "success");
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @GetMapping(path = "/getCdr", produces = "application/json")
    public ResponseEntity getCdr() {
        ResponseEntity<String> response = deltapathService.cdrReportQuery("");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;
        try {
            json = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(json.get("list"));
    }

    @GetMapping(path = "/call", produces = "application/json")
    public ResponseEntity call() {
        String response = deltapathService.callbackTrigger();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/wav", produces = "application/json")
    public ResponseEntity wav() throws IOException {
        ttsService.createWav("告警測試請撥打電話");
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @GetMapping(path = "/uploadFtp", produces = "application/json")
    public ResponseEntity uploadFtp() {
        ttsService.uploadWav();
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

}
