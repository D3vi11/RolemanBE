package com.example.data.controller;

import com.example.data.ResponseObject;
import com.example.data.dto.ItemDto;
import com.example.data.entity.Item;
import com.example.data.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> postItems(@RequestBody List<ItemDto> list){
        itemService.saveAll(list);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }

    @GetMapping("/item")
    public ResponseEntity<ItemDto> findItem(@RequestParam String name){
        ItemDto itemDto = itemService.findByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(itemDto);
    }

    @PostMapping("/item")
    public ResponseEntity<ResponseObject> postItem(@RequestBody ItemDto itemDto){
        itemService.save(itemDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }
    @PutMapping("/item")
    public ResponseEntity<ResponseObject> putItem(@RequestBody ItemDto itemDto, @RequestBody ItemDto newItem){
        itemService.change(itemDto,newItem);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteItem(@RequestBody String name){
        itemService.delete(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
