package com.example.data.controller;

import com.example.data.ResponseObject;
import com.example.data.dto.RuleDto;
import com.example.data.dto.SpellDto;
import com.example.data.service.RuleService;
import com.example.data.service.SpellService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("rules")
public class RuleController {
    private final RuleService ruleService;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> postRules(@RequestBody List<RuleDto> list){
        ruleService.saveAll(list);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }

    @GetMapping("/rule")
    public ResponseEntity<RuleDto> findRule(@RequestParam String name){
        RuleDto ruleDto = ruleService.findByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ruleDto);
    }

    @PostMapping("/rule")
    public ResponseEntity<ResponseObject> postRule(@RequestBody RuleDto ruleDto){
        ruleService.save(ruleDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }
    @PutMapping("/rule")
    public ResponseEntity<ResponseObject> putRule(@RequestBody RuleDto ruleDto, @RequestBody RuleDto newRuleDto){
        ruleService.change(ruleDto,newRuleDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @DeleteMapping("/rule")
    public ResponseEntity<ResponseObject> deleteRule(@RequestBody String name){
        ruleService.delete(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
