package com.bezkoder.spring.data.mongodb.dto;

import java.util.List;
import java.util.Map;

public class TutorialDto {

    private List<Map<String,Object>> input;
    private String collectionName;

    public List<Map<String, Object>> getInput() {
        return input;
    }

    public TutorialDto setInput(List<Map<String, Object>> input) {
        this.input = input;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public TutorialDto setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }
}
