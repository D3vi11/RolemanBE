package com.example.data.controller;

import com.example.data.dto.RuleDto;
import com.example.data.dto.SpellDto;
import com.example.data.service.RuleService;
import com.example.data.service.SpellService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("rules")
public class RuleController {
    RuleService ruleService;

    @PostMapping("/")
    public ResponseEntity<String> postRules(@RequestBody List<RuleDto> list){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        ruleService.saveAll(list);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    @GetMapping("/rule")
    public ResponseEntity<String> findRule(@RequestParam String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("item", ruleService.findByName(name));
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @PostMapping("/rule")
    public ResponseEntity<String> postRule(@RequestBody RuleDto ruleDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        ruleService.save(ruleDto);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
    @PutMapping("/rule")
    public ResponseEntity<String> putRule(@RequestBody RuleDto ruleDto, @RequestBody RuleDto newRuleDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        ruleService.change(ruleDto,newRuleDto);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @DeleteMapping("/rule")
    public ResponseEntity<String> deleteRule(@RequestBody String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        ruleService.delete(name);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
}
