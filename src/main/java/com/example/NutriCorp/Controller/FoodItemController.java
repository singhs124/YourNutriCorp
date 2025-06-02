package com.example.NutriCorp.Controller;

import com.example.NutriCorp.Model.FoodItem;
import com.example.NutriCorp.Service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class FoodItemController {

    @Autowired
    FoodItemService fis;

    @GetMapping("/getFoods")
    public ResponseEntity<List<FoodItem>> getAllFood(){
        List<FoodItem> res = fis.getAllFoodData();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(200));
    }
}
