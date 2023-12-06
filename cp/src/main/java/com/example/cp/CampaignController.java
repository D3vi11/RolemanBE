package com.example.cp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    @GetMapping
    public String getCampaign(){
        return "campaign";
    }
    @PostMapping
    public void addCampaign(){

    }
    @PatchMapping
    public void updateCampaign(){

    }
    @DeleteMapping
    public void removeCampaign(){

    }
}
