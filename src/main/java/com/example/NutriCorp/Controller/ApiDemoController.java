package com.example.NutriCorp.Controller;

import com.example.NutriCorp.Model.ApiDemo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/posts")
public class ApiDemoController {
    private final String URL = "https://jsonplaceholder.typicode.com/posts";

    @GetMapping("/")
    public List<ApiDemo> getPost(){
        RestTemplate rest = new RestTemplate();
        ApiDemo[] posts = rest.getForObject(URL,ApiDemo[].class);
        return Arrays.asList(posts);
    }
}
