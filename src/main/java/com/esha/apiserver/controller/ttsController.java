package com.esha.apiserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.esha.apiserver.util.WebClientUtil;
import com.esha.apiserver.vo.AlarmVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tts")
public class ttsController {

    @PostMapping(path = "/alarm", produces = "application/json")
    public ResponseEntity alarmCall(@RequestBody AlarmVo alarmVo) {
        JSONObject obj = new JSONObject();
        obj.put("status", "OK");
        obj.put("code", 200);
        obj.put("message", "success");
        return ResponseEntity.status(HttpStatus.OK).body(obj.toString());
    }

    @GetMapping(path = "/getCdr", produces = "application/json")
    public ResponseEntity getCdr() {
        ResponseEntity response = WebClientUtil.get();
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    @GetMapping (path = "/call", produces = "application/json")
    public ResponseEntity call() {
        String response = WebClientUtil.post();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
