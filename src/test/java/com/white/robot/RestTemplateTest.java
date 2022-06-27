package com.white.robot;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void RestGet(){
        String url="http://api.iyk0.com/tiangou";

        String result =restTemplate.getForObject(url,String.class);

        System.out.println(result);


    }


}
