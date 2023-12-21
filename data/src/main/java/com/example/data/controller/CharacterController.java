package com.example.data.controller;

import com.example.data.dto.CharacterDto;
import com.example.data.service.CharacterService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("characters")
public class CharacterController {
    CharacterService characterService;
    @PostMapping("/")
    public ResponseEntity<String> postCharacters(@RequestBody List<CharacterDto> list){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.OK);
        characterService.saveAll(list);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    @GetMapping("/character")
    public ResponseEntity<String> findCharacter(@RequestParam String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("item", characterService.findByName(name));
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @PostMapping("/character")
    public ResponseEntity<String> postCharacter(@RequestBody CharacterDto characterDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        characterService.save(characterDto);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
    @PutMapping("/character")
    public ResponseEntity<String> putCharacter(@RequestBody CharacterDto characterDto, @RequestBody CharacterDto newCharacterDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        characterService.change(characterDto,newCharacterDto);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @DeleteMapping("/character")
    public ResponseEntity<String> deleteCharacter(@RequestBody String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        characterService.delete(name);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
}
