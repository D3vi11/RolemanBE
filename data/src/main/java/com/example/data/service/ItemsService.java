package com.example.data.service;

import com.example.data.dto.ItemDto;
import com.example.data.entity.Item;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.ItemsRepository;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemsService {
    ItemsRepository itemRepository;

    public List<Item> findItemsByName(String name) {
        List<Item> items = itemRepository.findItemsByName(name);
        if (items.isEmpty()) {
            throw new NothingFoundException("Nie znaleziono żadnego przedmiotu");
        }

        return items;
    }

    public void saveAllItems(List<ItemDto> list) {
        try {
            itemRepository.saveAll(mapAll(list));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public Item findItemByName(String name) {
        return itemRepository.findItemByName(name)
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono przedmiotu"));
    }

    public void saveItem(ItemDto itemDto) {
        try {
            itemRepository.save(mapToItem(itemDto));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public void changeItem(ItemDto itemDto,ItemDto newItemDto){
        Item item = itemRepository.findItemByName(itemDto.getName())
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

    public void deleteItem(String name){
        Item item = itemRepository.findItemByName(name)
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

    private List<Item> mapAll(List<ItemDto> itemDtoList) {
        List<Item> list = new ArrayList<>();
        for (ItemDto itemDto : itemDtoList) {
            list.add(mapToItem(itemDto));
        }
        return list;
    }
}
