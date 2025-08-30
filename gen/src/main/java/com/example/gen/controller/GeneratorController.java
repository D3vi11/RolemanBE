package com.example.gen.controller;

import com.example.gen.dto.GeneratorDto;
import com.example.gen.service.GeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class GeneratorController {
    private final GeneratorService generatorService;

    @PostMapping("generate")
    public ResponseEntity<String> getGen(@Valid @RequestBody GeneratorDto generatorDto){
        return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(generatorService.generate(generatorDto));
    }
}
