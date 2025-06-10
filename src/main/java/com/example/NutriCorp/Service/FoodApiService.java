package com.example.NutriCorp.Service;

import com.example.NutriCorp.Model.FoodItem;
import com.example.NutriCorp.config.FoodApiConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class FoodApiService {
    @Autowired
    FoodApiConfig foodApiConfig;
    @Autowired
    private USDAParser usdaParser;

    RestTemplate restTemplate = new RestTemplate();

    public FoodItem getFoodById(String Id) throws JsonProcessingException {
        String URL = UriComponentsBuilder.fromHttpUrl(foodApiConfig.BaseURL.trim())
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
        Map<String, List<String>> datatypes = new HashMap<>();
        datatypes.put("Fresh Food" , Arrays.asList("Foundation"));
        datatypes.put("Packaged Food" , Arrays.asList("Branded"));
        datatypes.put("Cooked Food" , Arrays.asList("Survey (FNDDS)")) ;

        List<String> getDataType = datatypes.get(foodType);
        String URL = UriComponentsBuilder.fromHttpUrl(foodApiConfig.BaseURL.trim())
                .path("/fdc/v1/foods/search")
                .queryParam("query" , foodName)
                .queryParam("dataType" , getDataType)
                .queryParam("pageSize" , 1)
                .queryParam("pageNumber" , 1)
                .queryParam("api_key" , foodApiConfig.foodApiKey)
                .toUriString();
        String response = restTemplate.getForObject(URL, String.class);
        JsonNode root = usdaParser.RootParse(response);
        String FoodId = "";
        if(root.path("totalHits").asInt() <= 0 ) System.out.println("No Data");
        JsonNode foodArray = usdaParser.NodeFromRoot(root , "foods");
        return usdaParser.getFoodID(foodArray);
    }
}
