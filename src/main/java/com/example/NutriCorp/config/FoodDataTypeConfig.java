package com.example.NutriCorp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FoodDataTypeConfig {
    public static final Logger logger = LoggerFactory.getLogger(FoodDataTypeConfig.class);
    public static final Map<String , List<String>> DATATYPES ;

    static {
        DATATYPES = Map.of(
                "Fresh Food", List.of("Foundation"),
                "Packaged Food", List.of("Branded"),
                "Cooked Food", List.of("Survey (FNDDS)"));
        logger.info("Datatypes are intialized");
    }
}
