package com.example.NutriCorp.Service;

import com.example.NutriCorp.config.FoodApiConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.util.*;

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
        System.out.println(URL);
        String response = restTemplate.getForObject(URL, String.class);
        ObjectMapper objectMapper = new ObjectMapper() ;
        JsonNode root = objectMapper.readTree(response);
        JsonNode eleCount = root.path("totalHits");
        System.out.println(eleCount);
        if(eleCount.asInt() <= 0) {
            System.out.println("Data Not Found");
            return "Null";
        }
        JsonNode foodNode = root.path("foods");
        System.out.println(foodNode);
        JsonNode NutritionNode = null;
        if(foodNode.isArray()){
            for(JsonNode food: foodNode){
                NutritionNode = food.path("foodNutrients");
                break;
            }
        }
        System.out.println(NutritionNode);
        JsonNode foodPotion = root.path("foodPortions");
        if(NutritionNode != null && NutritionNode.isArray()){
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
        return response;
    }
}
