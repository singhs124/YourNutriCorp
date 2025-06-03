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
        String URL = UriComponentsBuilder.fromHttpUrl(foodApiConfig.BaseURL.trim())
                .path("/fdc/v1/food/{id}")
                .queryParam("api_key" , foodApiConfig.foodApiKey)
                .buildAndExpand(Id)
                .toUriString();
        return restTemplate.getForObject(URL, String.class);
    }
}
