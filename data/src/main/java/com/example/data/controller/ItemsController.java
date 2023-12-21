package com.example.data.controller;

import com.example.data.dto.ItemDto;
import com.example.data.service.ItemsService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@AllArgsConstructor
public class ItemsController {

    ItemsService itemsService;

    @GetMapping("/")
    public ResponseEntity<String> findItems(@RequestParam String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("items",itemsService.findItemsByName(name));
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> postItems(@RequestBody List<ItemDto> list){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        itemsService.saveAllItems(list);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    @GetMapping("/item")
    public ResponseEntity<String> findItem(@RequestParam String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("item",itemsService.findItemByName(name));
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @PostMapping("/item")
    public ResponseEntity<String> postItem(@RequestBody ItemDto itemDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        itemsService.saveItem(itemDto);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
    @PutMapping("/item")
    public ResponseEntity<String> putItem(@RequestBody ItemDto itemDto, @RequestBody ItemDto newItem){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        itemsService.changeItem(itemDto,newItem);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteItem(@RequestBody String name){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        itemsService.deleteItem(name);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
}
