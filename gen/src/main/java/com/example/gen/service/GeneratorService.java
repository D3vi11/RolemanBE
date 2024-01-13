package com.example.gen.service;

import com.example.gen.dto.GeneratorDto;
import com.example.gen.exception.EnemiesNotFoundException;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GeneratorService {
    private final RestTemplate restTemplate;

    public ResponseEntity<String> generate(GeneratorDto generatorDto){
        Integer enemyCr = generatorDto.getTeamLevel();
        enemyCr*=(generatorDto.getNumberOfPlayers()/4);
        enemyCr+=generatorDto.getDifficulty().getValue();
        Integer singleEnemyCr = enemyCr/generatorDto.getNumberOfEnemies();

        ResponseEntity<String> entity = restTemplate.getForEntity("http://data/enemies/enemies?rarity=" + generatorDto.getRarity() + "&cr=" + singleEnemyCr+"&limit="+generatorDto.getNumberOfEnemies(), String.class);
        String body = entity.getBody();
        JsonArray jsonArray = JsonParser.parseString(body).getAsJsonArray();
        if(jsonArray.isEmpty()){
            throw new EnemiesNotFoundException("Brak przeciwników dopasowanych do podanych parametrów");
        }
        for(JsonElement jsonElement: jsonArray){
            if(jsonElement.isJsonObject()){
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                jsonObject.addProperty("number",1);
            }else{
                throw new JsonParseException("Błąd parsowania");
            }
        }
        if(jsonArray.size()!=generatorDto.getNumberOfEnemies()){
            int difference = Math.abs(generatorDto.getNumberOfEnemies()-jsonArray.size());
            if(jsonArray.get(0).isJsonObject()){
                jsonArray.get(0).getAsJsonObject().addProperty("number",difference+1);
            }else{
                throw new JsonParseException("Błąd parsowania");
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jsonArray.toString());
    }
}
