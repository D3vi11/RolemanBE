package com.example.data.controller;

import com.example.data.ResponseObject;
import com.example.data.dto.ItemDto;
import com.example.data.dto.SpellDto;
import com.example.data.service.SpellService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("spells")
public class SpellController {
    private final SpellService spellService;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> postSpells(@RequestBody List<SpellDto> list){
        spellService.saveAll(list);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }

    @GetMapping("/spell")
    public ResponseEntity<SpellDto> findSpell(@RequestParam String name){
        SpellDto spellDto = spellService.findByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(spellDto);
    }

    @PostMapping("/spell")
    public ResponseEntity<ResponseObject> postSpell(@RequestBody SpellDto spellDto){
        spellService.save(spellDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }
    @PutMapping("/spell")
    public ResponseEntity<ResponseObject> putSpell(@RequestBody SpellDto spellDto, @RequestBody SpellDto newSpell){
        spellService.change(spellDto,newSpell);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @DeleteMapping("/spell")
    public ResponseEntity<ResponseObject> deleteSpell(@RequestBody String name){
        spellService.delete(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
