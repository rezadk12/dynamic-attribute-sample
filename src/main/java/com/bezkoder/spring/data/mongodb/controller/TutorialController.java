package com.bezkoder.spring.data.mongodb.controller;

import java.util.*;

import com.bezkoder.spring.data.mongodb.dto.TutorialDto;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {


  @Autowired
  private MongoTemplate mongo;




    @PostMapping("/user")
    public ResponseEntity<Boolean> addUser(@RequestBody TutorialDto tutorialDto  ) {
        try {

            Set<Document> docs = tutorialDto.getInput()
                    .stream()
                    .collect(HashSet<Document>::new, (set, entry) -> {
                        Document document = new Document();
                        Set<String> strings=entry.keySet();
                        for(String a:strings){
                            document.put(a,entry.get(a));
                        }
                        set.add(document);
                    }, Set::addAll);

            Collection<Document> saved = mongo.insert(docs, tutorialDto.getCollectionName());



            Set<String> keys=tutorialDto.getInput().get(0).keySet();
            TextIndexDefinition.TextIndexDefinitionBuilder textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder();
            for(String w:keys){
                textIndex.onField(w);
            }
            TextIndexDefinition textIndex1 = textIndex
                    .build();

            mongo.indexOps(tutorialDto.getCollectionName()).ensureIndex(textIndex1);


            Query query = new Query(Criteria.where("age").gt(20));
            List<Document> documents=mongo.find(query,Document.class,tutorialDto.getCollectionName());

            query = new Query(Criteria.where("age").gt(26));
            documents=mongo.find(query,Document.class,tutorialDto.getCollectionName());
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
