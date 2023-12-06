package com.example.eq;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @GetMapping
    public String getEquipment(){
        return "Equipment";
    }
    @PostMapping
    public void addEquipment(){

    }
    @PatchMapping
    public void updateEquipment(){

    }
    @DeleteMapping
    public void removeEquipment(){

    }
}
