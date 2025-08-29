package com.example.eq;

import com.example.eq.dto.EquipmentDto;
import com.example.eq.entity.Equipment;
import com.example.eq.entity.Item;
import com.example.eq.exception.NotFoundException;
import com.example.eq.exception.UnableToDeleteException;
import com.example.eq.exception.UnableToSaveException;
import com.example.eq.repository.EquipmentRepository;
import com.example.eq.service.EquipmentService;
import com.mongodb.MongoException;
import org.junit.jupiter.api.BeforeEach;
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
class EquipmentServiceTests {
    @Mock
    EquipmentRepository equipmentRepository;

    @InjectMocks
    EquipmentService equipmentService;

    private static final String username = "username";
    private static final String campaignId = "campaignId";
    private static final String characterName = "characterName";
    private static final String name = "name";
    private static final String damage = "damage";
    private static final Integer daysToExpire = 1;
    private static final String description = "description";
    private static final Integer quantity = 1;


    @Nested
    public class NextDayTests{
        private Item item = new Item(name, damage, daysToExpire, description, quantity);
        private Equipment equipment = new Equipment(username, campaignId, characterName, List.of(item));

        @BeforeEach
        public void prepareData(){
            item = new Item(name, damage, daysToExpire, description, quantity);
            equipment = new Equipment(username, campaignId, characterName, List.of(item));
        }

        @Test
        public void nextDayTest(){
            when(equipmentRepository.findAllByCampaignId(campaignId)).thenReturn(List.of(equipment));
            equipmentService.nextDay(campaignId);
            ArgumentCaptor<List<Equipment>> listCaptor = ArgumentCaptor.forClass(List.class);
            verify(equipmentRepository).saveAll(listCaptor.capture());
            assertEquals(item.getDaysToExpire(), listCaptor.getValue().get(0).getItemList().get(0).getDaysToExpire());
        }

        @Test
        public void databaseErrorTest(){
            when(equipmentRepository.findAllByCampaignId(campaignId)).thenReturn(List.of(equipment));
            when(equipmentRepository.saveAll(List.of(equipment))).thenThrow(new MongoException(""));
            assertThrows(UnableToSaveException.class, () -> equipmentService.nextDay(campaignId));
        }

    }

    @Nested
    public class ReadEquipmentTests{
        private Item item = new Item(name, damage, daysToExpire, description, quantity);
        private EquipmentDto equipmentDto = new EquipmentDto(username, campaignId, characterName, List.of(item));
        private Equipment equipment = new Equipment(username, campaignId, characterName, List.of(item));

        @BeforeEach
        public void prepareData(){
            item = new Item(name, damage, daysToExpire, description, quantity);
            equipmentDto = new EquipmentDto(username, campaignId, characterName, List.of(item));
            equipment = new Equipment(username, campaignId, characterName, List.of(item));
        }

        @Test
        public void readEquipmentTest(){
            when(equipmentRepository.findByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName)).thenReturn(Optional.of(equipment));
            EquipmentDto dto = equipmentService.readEquipment(username, campaignId, characterName);
            assertEquals(equipmentDto, dto);
        }

        @Test
        public void nothingFoundTest(){
            when(equipmentRepository.findByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> equipmentService.readEquipment(username, campaignId, characterName));
        }
    }

    @Nested
    public class CreateEquipmentTests{
        private Item item = new Item(name, damage, daysToExpire, description, quantity);
        private EquipmentDto equipmentDto = new EquipmentDto(username, campaignId, characterName, List.of(item));
        private Equipment equipment = new Equipment(username, campaignId, characterName, List.of(item));

        @BeforeEach
        public void prepareData(){
            item = new Item(name, damage, daysToExpire, description, quantity);
            equipmentDto = new EquipmentDto(username, campaignId, characterName, List.of(item));
            equipment = new Equipment(username, campaignId, characterName, List.of(item));
        }

        @Test
        public void createEquipmentTest(){
            equipmentService.createEquipment(equipmentDto);
            verify(equipmentRepository).save(equipment);
        }

        @Test
        public void databaseErrorTest(){
            when(equipmentRepository.save(equipment)).thenThrow(new MongoException(""));
            assertThrows(UnableToSaveException.class, () -> equipmentService.createEquipment(equipmentDto));
        }
    }

    @Nested
    public class ModifyEquipmentTests{
        private Item item = new Item(name, damage, daysToExpire, description, quantity);
        private EquipmentDto equipmentDto = new EquipmentDto(username, campaignId, characterName, List.of(item));
        private Equipment equipment = new Equipment(username, campaignId, characterName, List.of(item));

        @BeforeEach
        public void prepareData(){
            item = new Item(name, damage, daysToExpire, description, quantity);
            equipmentDto = new EquipmentDto(username, campaignId, characterName, List.of(item));
            equipment = new Equipment(username, campaignId, characterName, List.of(item));
        }

        @Test
        public void modifyEquipmentTest(){
            when(equipmentRepository.findByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName)).thenReturn(Optional.of(equipment));
            equipmentDto.setItemList(List.of(item, item));
            equipmentService.modifyEquipment(equipmentDto);
            ArgumentCaptor<Equipment> equipmentCaptor = ArgumentCaptor.forClass(Equipment.class);
            verify(equipmentRepository).save(equipmentCaptor.capture());
            assertEquals(List.of(item, item), equipmentCaptor.getValue().getItemList());
        }

        @Test
        public void nothingFoundTest(){
            when(equipmentRepository.findByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName)).thenReturn(Optional.empty());
            equipmentDto.setItemList(List.of(item, item));
            assertThrows(NotFoundException.class, () -> equipmentService.modifyEquipment(equipmentDto));
            ArgumentCaptor<Equipment> equipmentCaptor = ArgumentCaptor.forClass(Equipment.class);
            verify(equipmentRepository, never()).save(equipmentCaptor.capture());
        }

        @Test
        public void databaseErrorTest(){
            when(equipmentRepository.findByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName)).thenReturn(Optional.of(equipment));
            when(equipmentRepository.save(equipment)).thenThrow(new MongoException(""));
            equipmentDto.setItemList(List.of(item, item));
            assertThrows(UnableToSaveException.class, () -> equipmentService.modifyEquipment(equipmentDto));
        }
    }

    @Nested
    public class DeleteEquipmentTests{

        @Test
        public void deleteEquipmentTest(){
            equipmentService.deleteEquipment(username, campaignId, characterName);
            verify(equipmentRepository).deleteByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName);
        }

        @Test
        public void databaseErrorTest(){
            doThrow(new MongoException("")).when(equipmentRepository).deleteByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName);
            assertThrows(UnableToDeleteException.class, () -> equipmentService.deleteEquipment(username, campaignId, characterName));
        }
    }


}
