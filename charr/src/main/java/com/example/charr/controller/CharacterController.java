package com.example.charr.controller;

import com.example.charr.ResponseObject;
import com.example.charr.dto.CharacterDto;
import com.example.charr.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<CharacterDto> getCharacter(@RequestParam String characterName, @RequestParam String campaignId, @RequestParam String username){
        CharacterDto characterDto = characterService.readCharacter(characterName,campaignId,username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(characterDto);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> postCharacter(@RequestBody CharacterDto characterDto){
        characterService.createCharacter(characterDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }
    @PutMapping
    public ResponseEntity<ResponseObject> putCharacter(@RequestBody CharacterDto characterDto){
        characterService.modifyCharacter(characterDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
    @DeleteMapping ResponseEntity<ResponseObject> deleteCharacter(@RequestParam String characterName, @RequestParam String campaignId, @RequestParam String username){
        characterService.deleteCharacter(characterName,campaignId,username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
