package com.person98.commonsessence.conf;

import com.person98.craftessence.util.annotations.Configurable;

import java.util.List;
import java.util.Map;

@Configurable(fileName = "lang")
public class LangConf {
    private String test = "Test";
    private List<String> testList = List.of("Test1", "Test2", "Test3");
    private Map<String, String> testMap = Map.of("key1", "value1", "key2", "value2");
    private int testInt = 1;
    private double testDouble = 1.0;
    private boolean testBoolean = true;


    public String getTest() {
        return this.test;
    }

}
