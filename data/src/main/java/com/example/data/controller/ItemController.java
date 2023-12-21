package com.example.data.controller;

import com.example.data.dto.ItemDto;
import com.example.data.service.ItemService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@AllArgsConstructor
public class ItemController {

    ItemService itemService;

    @PostMapping("/")
    public ResponseEntity<String> postItems(@RequestBody List<ItemDto> list){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        itemService.saveAll(list);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    @GetMapping("/item")
    public ResponseEntity<String> findItem(@RequestParam String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("item", itemService.findByName(name));
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @PostMapping("/item")
    public ResponseEntity<String> postItem(@RequestBody ItemDto itemDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        itemService.save(itemDto);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
    @PutMapping("/item")
    public ResponseEntity<String> putItem(@RequestBody ItemDto itemDto, @RequestBody ItemDto newItem){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        itemService.change(itemDto,newItem);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteItem(@RequestBody String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        itemService.delete(name);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
}
