package com.example.data.controller;

import com.example.data.ResponseObject;
import com.example.data.dto.EnemyDto;
import com.example.data.entity.Enemy;
import com.example.data.enums.Rarity;
import com.example.data.service.EnemyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("enemies")
public class EnemyController {
    private final EnemyService enemyService;
    @PostMapping("/")
    public ResponseEntity<ResponseObject> postEnemies(@RequestBody List<EnemyDto> list){
        enemyService.saveAll(list);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }

    @GetMapping("/enemy")
    public ResponseEntity<EnemyDto> findEnemy(@RequestParam String name){
        EnemyDto enemyDto = enemyService.findByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(enemyDto);
    }

    @GetMapping("/enemies")
    public ResponseEntity<List<EnemyDto>> findEnemiesByRarityAndCr(@RequestParam Rarity rarity, Integer cr, Integer limit){
        List<EnemyDto> allByRarityAndCr = enemyService.findAllByRarityAndCr(rarity, cr, limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allByRarityAndCr);
    }

    @PostMapping("/enemy")
    public ResponseEntity<ResponseObject> postEnemy(@RequestBody EnemyDto enemyDto){
        enemyService.save(enemyDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }
    @PutMapping("/enemy")
    public ResponseEntity<ResponseObject> putEnemy(@RequestParam String name, @RequestBody EnemyDto newEnemyDto){
        enemyService.change(name,newEnemyDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @DeleteMapping("/enemy")
    public ResponseEntity<ResponseObject> deleteEnemy(@RequestBody String name){
        enemyService.delete(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
