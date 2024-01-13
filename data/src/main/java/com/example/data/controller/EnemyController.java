package com.example.data.controller;

import com.example.data.dto.EnemyDto;
import com.example.data.entity.Enemy;
import com.example.data.enums.Rarity;
import com.example.data.service.EnemyService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("enemies")
public class EnemyController {
    EnemyService enemyService;
    @PostMapping("/")
    public ResponseEntity<String> postEnemies(@RequestBody List<EnemyDto> list){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.OK);
        enemyService.saveAll(list);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    @GetMapping("/enemy")
    public ResponseEntity<String> findEnemy(@RequestParam String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("item", enemyService.findByName(name));
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @GetMapping("/enemies")
    public ResponseEntity<List<EnemyDto>> findEnemiesByRarityAndCr(@RequestParam Rarity rarity, Integer cr, Integer limit){
        List<EnemyDto> allByRarityAndCr = enemyService.findAllByRarityAndCr(rarity, cr, limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allByRarityAndCr);
    }

    @PostMapping("/enemy")
    public ResponseEntity<String> postEnemy(@RequestBody EnemyDto enemyDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        enemyService.save(enemyDto);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
    @PutMapping("/enemy")
    public ResponseEntity<String> putEnemy(@RequestParam String name, @RequestBody EnemyDto newEnemyDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        enemyService.change(name,newEnemyDto);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @DeleteMapping("/enemy")
    public ResponseEntity<String> deleteEnemy(@RequestBody String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        enemyService.delete(name);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
}
