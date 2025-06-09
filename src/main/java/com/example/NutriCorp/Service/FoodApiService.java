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
        double protein = 0.0, cal = 0.0 , fats = 0.0 , carbo = 0.0 ;
        if(NutritionNode.isArray()){
            for(JsonNode nutrition: NutritionNode){
                Integer nutritionNumber = nutrition.path("number").asInt() ;
                switch (nutritionNumber){
                    case 203:
                        protein = nutrition.path("amount").asDouble();
                }
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
        String response = restTemplate.getForObject(URL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        String FoodId = "";
        if(root.path("totalHits").asInt() <= 0 ) System.out.println("No Data");
        JsonNode foodArray = root.path("foods");
        if(foodArray.isArray()){
            for(JsonNode food: foodArray){
                FoodId = food.path("fdcId").asText();
            }
        }
        return FoodId;
    }
}
