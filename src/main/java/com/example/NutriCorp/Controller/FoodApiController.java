package com.example.NutriCorp.Controller;

import com.example.NutriCorp.Service.FoodApiService;
import com.example.NutriCorp.Service.OpenAIApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/v1/foodApi")
public class FoodApiController {
    @Autowired
    FoodApiService foodApiService;
    @Autowired
    OpenAIApiService openAIApiService;

    @GetMapping("/food/{Id}")
    public String getFoodById(@PathVariable("Id") String Id){
        System.out.println("here your food goes");
        return foodApiService.getFoodById(Id);
    }

    @GetMapping("/{Input}")
    public String foodCheck(@PathVariable("Input") String Input){
        openAIApiService.filterInput(Input);
        return "Hello, Done!";
    }
}
