package com.example.NutriCorp.Service;

import com.example.NutriCorp.Model.FoodItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class USDAParser {

    public JsonNode RootParse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode NodeFromRoot(JsonNode root, String fieldName){
        return root.path(fieldName);
    }

    public FoodItem getNutrionalValue(JsonNode NutritionNode){
        String protein = "" , fats = "", cal = "" , carbs = "" ;
        if(NutritionNode.isArray()){
            for(JsonNode nutrition: NutritionNode){
                Integer nutritionNumber = nutrition.path("nutrient").path("number").asInt(); ;
                switch (nutritionNumber){
                    case 203:
                        protein = nutrition.path("amount").asText() + " " + nutrition.path("nutrient").path("unitName").asText();
                        break;
                    case 204:
                        fats = nutrition.path("amount").asText() + " " + nutrition.path("nutrient").path("unitName").asText();
                        break;
                    case 205:
                        carbs = nutrition.path("amount").asText() + " " + nutrition.path("nutrient").path("unitName").asText();
                        break;
                    case 208:
                        cal = nutrition.path("amount").asText() + " " + nutrition.path("nutrient").path("unitName").asText();
                        break;
                }
            }
        }
        return new FoodItem(cal,fats,protein,carbs);
    }

    public String getFoodID(JsonNode foodArray){
        String FoodId = "";
        if(foodArray.isArray()){
            for(JsonNode food: foodArray){
                FoodId = food.path("fdcId").asText();
            }
        }
        return FoodId ;
    }
}
