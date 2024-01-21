package com.example.data.service;

import com.example.data.dto.ItemDto;
import com.example.data.entity.Item;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.ItemRepository;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public void saveAll(List<ItemDto> list) {
        try {
            itemRepository.saveAll(mapAll(list));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public ItemDto findByName(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono przedmiotu"));
        return mapToDto(item);
    }

    public void save(ItemDto itemDto) {
        try {
            itemRepository.save(mapToItem(itemDto));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public void change(ItemDto itemDto,ItemDto newItemDto){
        Item item = itemRepository.findByName(itemDto.getName())
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono przedmiotu do zmiany"));
        item.setName(newItemDto.getName());
        item.setRequirements(newItemDto.getRequirements());
        item.setDescription(newItemDto.getDescription());
        try{
            itemRepository.save(item);
        }catch (MongoException e){
            throw new FailedToSaveException("Błąd zapisu");
        }
    }

    public void delete(String name){
        Item item = itemRepository.findByName(name)
                .orElseThrow(()->new NothingFoundException("Brak przedmiotu do usunięcia"));
        try {
            itemRepository.delete(item);
        }catch (MongoException e){
            throw new FailedToDeleteException("Błąd podczas usuwania przedmiotu");
        }
    }

    private Item mapToItem(ItemDto itemDto) {
        return new Item(itemDto.getName(), itemDto.getRequirements(), itemDto.getDescription());
    }
    private ItemDto mapToDto(Item item){
        return new ItemDto(item.getName(),item.getRequirements(),item.getDescription());
    }

    private List<Item> mapAll(List<ItemDto> list) {
        return list.stream()
                .map(this::mapToItem)
                .toList();
    }
}
