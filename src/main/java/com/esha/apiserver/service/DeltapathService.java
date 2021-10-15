package com.esha.apiserver.service;

import com.esha.apiserver.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

@Service
public class DeltapathService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Value("${deltapath.service}")
    private String service;

    @Value("${deltapath.secret}")
    private String secret;

    private String tokenName = "X-frSIP-API-Token";

    public ResponseEntity<String> cdrReportQuery(String query)
    {
        String url = service + "/RESTful/index.php/v1/get/reporting/cdr/view/list?start=0&limit=100&query=" + query;
        String tokenValue = stringRedisTemplate.opsForValue().get("deltapath_token");
        return WebClientUtil.get(url, tokenName, tokenValue);
    }

    public String callbackTrigger() {
        String url = service + "/RESTful/index.php/v1/post/frsipswitchboard/callback/trigger";
        String tokenValue = stringRedisTemplate.opsForValue().get("deltapath_token");
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("callbknum", "3000");
        bodyValues.add("destnum", "1191101");
        bodyValues.add("destname", "test");
        bodyValues.add("secret", "216e8d767eb0ada74ed3eac23a1b2f18");
        return WebClientUtil.post(url, tokenName, tokenValue, bodyValues);
    }

}
