package com.example.NutriCorp.Service;

import com.example.NutriCorp.config.FoodApiConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FoodApiService {
    @Autowired
    FoodApiConfig foodApiConfig;

    RestTemplate restTemplate = new RestTemplate();

    public String getFoodById(String Id) throws JsonProcessingException {
        String URL = UriComponentsBuilder.fromHttpUrl(foodApiConfig.BaseURL.trim())
                .path("/fdc/v1/food/{id}")
                .queryParam("api_key" , foodApiConfig.foodApiKey)
                .buildAndExpand(Id)
                .toUriString();
        String response = restTemplate.getForObject(URL, String.class);
//        JSONObject jsonObject = new JSONObject(response);
//        String description = jsonObject.getString("description");
//        System.out.println(description);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);
        JsonNode NutritionNode = root.path("foodNutrients");
        JsonNode foodPotion = root.path("foodPortions");
        if(NutritionNode.isArray()){
            for(JsonNode nutrition: NutritionNode){
                double amount = nutrition.path("amount").asDouble();
                String name = nutrition.path("nutrient").path("name").asText();
                String unit = nutrition.path("nutrient").path("unitName").asText();
                System.out.println(name+" -> "+amount+" "+unit);
            }
        }
        if(foodPotion.isArray()){
            for(JsonNode potions: foodPotion){
                double wt = potions.path("gramWeight").asDouble();
                System.out.println("Potion Size:" + wt+" gram");
            }
        }
//        String preetyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(NutritionNode);
//        System.out.println(preetyJson);
        return response;
    }
}
