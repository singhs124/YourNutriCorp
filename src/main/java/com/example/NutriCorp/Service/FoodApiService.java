package com.example.NutriCorp.Service;

import com.example.NutriCorp.Model.FoodItem;
import com.example.NutriCorp.config.FoodApiConfig;
import com.example.NutriCorp.config.FoodDataTypeConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class FoodApiService {
    @Autowired
    FoodApiConfig foodApiConfig;
    @Autowired
    private USDAParser usdaParser;


    RestTemplate restTemplate = new RestTemplate();

    public FoodItem getFoodById(String Id) throws JsonProcessingException {
        String URL = UriComponentsBuilder.fromUriString(foodApiConfig.BaseURL.trim())
                .path("/fdc/v1/food/{id}")
                .queryParam("api_key" , foodApiConfig.foodApiKey)
                .buildAndExpand(Id)
                .toUriString();
        try {
            String response = restTemplate.getForObject(URL, String.class);
            JsonNode root = usdaParser.RootParse(response);
            JsonNode NutritionNode = usdaParser.NodeFromRoot(root , "foodNutrients");
            return usdaParser.getNutrionalValue(NutritionNode);
        }
        catch (HttpClientErrorException e){
            throw e;
        }
    }

    public String getFoodByName(String foodName , String foodType) throws JsonProcessingException {
        List<String> getDataType = FoodDataTypeConfig.DATATYPES.get(foodType);
        String URL = UriComponentsBuilder.fromUriString(foodApiConfig.BaseURL.trim())
                .path("/fdc/v1/foods/search")
                .queryParam("query" , foodName)
                .queryParam("dataType" , getDataType)
                .queryParam("pageSize" , 1)
                .queryParam("pageNumber" , 1)
                .queryParam("api_key" , foodApiConfig.foodApiKey)
                .toUriString();
        String response = restTemplate.getForObject(URL, String.class);
        JsonNode root = usdaParser.RootParse(response);
        if(root.path("totalHits").asInt() <= 0 ) System.out.println("No Data");
        JsonNode foodArray = usdaParser.NodeFromRoot(root , "foods");
        return usdaParser.getFoodID(foodArray);
    }
}
