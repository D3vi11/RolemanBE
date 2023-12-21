package com.example.data.controller;

import com.example.data.dto.ItemDto;
import com.example.data.dto.SpellDto;
import com.example.data.service.SpellService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("spells")
public class SpellController {
    SpellService spellService;

    @PostMapping("/")
    public ResponseEntity<String> postSpells(@RequestBody List<SpellDto> list){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        spellService.saveAll(list);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    @GetMapping("/spell")
    public ResponseEntity<String> findSpell(@RequestParam String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("item", spellService.findByName(name));
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @PostMapping("/spell")
    public ResponseEntity<String> postSpell(@RequestBody SpellDto spellDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        spellService.save(spellDto);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
    @PutMapping("/spell")
    public ResponseEntity<String> putSpell(@RequestBody SpellDto spellDto, @RequestBody SpellDto newSpell){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        spellService.change(spellDto,newSpell);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @DeleteMapping("/spell")
    public ResponseEntity<String> deleteSpell(@RequestBody String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        spellService.delete(name);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
}
