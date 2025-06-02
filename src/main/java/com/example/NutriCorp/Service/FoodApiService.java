package com.example.NutriCorp.Service;

import com.example.NutriCorp.config.FoodApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FoodApiService {
    @Autowired
    FoodApiConfig foodApiConfig;

    RestTemplate restTemplate = new RestTemplate();

    public String getFoodById(String Id){
        System.out.println("BaseURL:" + foodApiConfig.BaseURL);
        System.out.println("Api: " + foodApiConfig.foodApiKey);
        String URL = UriComponentsBuilder.fromHttpUrl(foodApiConfig.BaseURL)
                .path("/fdc/v1/food/{id}")
                .queryParam("api_key" , foodApiConfig.foodApiKey)
                .buildAndExpand(Id)
                .toUriString();
        System.out.println("URL is: " + URL);
        return restTemplate.getForObject(URL, String.class);
    }
}
