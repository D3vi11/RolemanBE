package com.example.gen.service;

import com.example.gen.dto.GeneratorDto;
import com.example.gen.exception.EnemiesNotFoundException;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GeneratorService {
    private final RestTemplate restTemplate;

    private static final String url = "http://data/enemies/";

    public String generate(GeneratorDto generatorDto) {
        ResponseEntity<String> entity = restTemplate.getForEntity(getUrl(generatorDto), String.class);
        String body = entity.getBody();
        if (body == null || body.equals(new JsonArray().toString())) {
            throw new EnemiesNotFoundException("Brak przeciwników dopasowanych do podanych parametrów");
        }
        JsonArray jsonArray = JsonParser.parseString(body).getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                jsonObject.addProperty("number", 1);
            } else {
                throw new JsonParseException("Błąd parsowania");
            }
        }
        if (jsonArray.size() != generatorDto.getNumberOfEnemies()) {
            int difference = Math.abs(generatorDto.getNumberOfEnemies() - jsonArray.size());
            jsonArray.get(0).getAsJsonObject().addProperty("number", difference + 1);
        }
        return jsonArray.toString();
    }

    private String getUrl(GeneratorDto generatorDto) {
        int enemyCr = generatorDto.getTeamLevel();
        enemyCr *= Math.floorDiv(generatorDto.getNumberOfPlayers(), 4);
        enemyCr += generatorDto.getDifficulty().getValue();
        int singleEnemyCr = enemyCr / generatorDto.getNumberOfEnemies();
        return url + "enemies?rarity=" + generatorDto.getRarity() + "&cr=" + singleEnemyCr + "&limit=" + generatorDto.getNumberOfEnemies();
    }
}
