package com.example.NutriCorp.Controller;

import com.example.NutriCorp.Model.FoodItem;
import com.example.NutriCorp.Service.FoodApiService;
import com.example.NutriCorp.Service.OpenAIApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;


@RestController
@RequestMapping("/v1/foodApi")
public class FoodApiController {
    @Autowired
    FoodApiService foodApiService;
    @Autowired
    OpenAIApiService openAIApiService;

    @GetMapping("/food/{Id}")
    public ResponseEntity<?> getFoodById(@PathVariable("Id") String Id) {
        try{
            FoodItem fooditem = foodApiService.getFoodById(Id);
            if(fooditem == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This Food item is not Found");
            return ResponseEntity.ok(fooditem) ;
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parsing Error: "+ e);
        } catch (HttpClientErrorException e){
            if(e.getStatusCode().value() == 404){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This food item is not Found");
            }
            else {
                return ResponseEntity.status(e.getStatusCode()).body("Error: " + e) ;
            }
        }
    }

    @GetMapping("/foodName/{FoodName}/{dataType}")
    public ResponseEntity<?> getFoodByName(@PathVariable String FoodName, @PathVariable String dataType){
        try{
            String foodId =  foodApiService.getFoodByName(FoodName,dataType);
            if(Objects.equals(foodId, "")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This food is not Found");
            FoodItem foodItem =  foodApiService.getFoodById(foodId);
            if(foodItem == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This food is not Found");
            return ResponseEntity.ok(foodItem);
        }
        catch (JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{Input}")
    public String foodCheck(@PathVariable("Input") String Input){
        openAIApiService.filterInput(Input);
        return "Hello, Done!";
    }
}
