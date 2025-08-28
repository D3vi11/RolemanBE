package com.example.data;

import com.example.data.dto.ItemDto;
import com.example.data.entity.Item;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.ItemRepository;
import com.example.data.service.ItemService;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {
    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;

    String name = "name";
    String requirements = "requirements";
    String description = "description";

    @Nested
    public class SaveAllTests{
        ItemDto itemDto = new ItemDto(name, requirements, description);
        Item item = new Item(name, requirements, description);

        @Test
        public void saveAllTest(){
            itemService.saveAll(List.of(itemDto));
            verify(itemRepository).saveAll(List.of(item));
        }

        @Test
        public void failedToSaveTest(){
            when(itemRepository.saveAll(List.of(item))).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class, () -> itemService.saveAll(List.of(itemDto)));
            verify(itemRepository).saveAll(List.of(item));
        }
    }

    @Nested
    public class FindByNameTests{
        ItemDto itemDto = new ItemDto(name, requirements, description);
        Item item = new Item(name, requirements, description);

        @Test
        public void findByNameTest(){
            when(itemRepository.findByName(name)).thenReturn(Optional.of(item));
            ItemDto dto = itemService.findByName(name);
            assertEquals(itemDto, dto);
        }

        @Test
        public void nothingFoundTest(){
            when(itemRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> itemService.findByName(name));
        }
    }

    @Nested
    public class SaveTests{
        ItemDto itemDto = new ItemDto(name, requirements, description);
        Item item = new Item(name, requirements, description);

        @Test
        public void saveTest(){
            itemService.save(itemDto);
            verify(itemRepository).save(item);
        }

        @Test
        public void savingErrorTest(){
            when(itemRepository.save(item)).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class, () -> itemService.save(itemDto));
            verify(itemRepository).save(item);
        }
    }

    @Nested
    public class ChangeTests{
        ItemDto itemDto = new ItemDto(name, requirements, description);
        ItemDto itemDto2 = new ItemDto("new"+name, "new"+requirements, "new"+description);
        Item item = new Item(name, requirements, description);

        @Test
        public void changeTest(){
            when(itemRepository.findByName(name)).thenReturn(Optional.of(item));
            itemService.change(itemDto, itemDto2);
            ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
            verify(itemRepository).save(itemCaptor.capture());
            assertEquals(itemDto2.getName(), itemCaptor.getValue().getName());
            assertEquals(itemDto2.getRequirements(), itemCaptor.getValue().getRequirements());
            assertEquals(itemDto2.getDescription(), itemCaptor.getValue().getDescription());
        }

        @Test
        public void nothingFoundTest(){
            when(itemRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> itemService.change(itemDto, itemDto2));
            ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
            verify(itemRepository, never()).save(itemCaptor.capture());
        }

        @Test
        public void databaseErrorTest(){
            when(itemRepository.findByName(name)).thenReturn(Optional.of(item));
            when(itemRepository.save(item)).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class, () -> itemService.change(itemDto, itemDto2));
            ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
            verify(itemRepository).save(itemCaptor.capture());
        }
    }

    @Nested
    public class DeleteTests{
        ItemDto itemDto = new ItemDto(name, requirements, description);
        Item item = new Item(name, requirements, description);

        @Test
        public void deleteTest(){
            when(itemRepository.findByName(name)).thenReturn(Optional.of(item));
            itemService.delete(name);
            verify(itemRepository).delete(item);
        }

        @Test
        public void nothingFoundTest(){
            when(itemRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> itemService.delete(name));
            verify(itemRepository, never()).delete(item);
        }

        @Test
        public void databaseErrorTest(){
            when(itemRepository.findByName(name)).thenReturn(Optional.of(item));
            doThrow(new MongoException("")).when(itemRepository).delete(item);
            assertThrows(FailedToDeleteException.class, () -> itemService.delete(name));
        }
    }
}
