package com.example.eq.service;

import com.example.eq.dto.EquipmentDto;
import com.example.eq.entity.Equipment;
import com.example.eq.entity.Item;
import com.example.eq.exception.NotFoundException;
import com.example.eq.exception.UnableToDeleteException;
import com.example.eq.exception.UnableToSaveException;
import com.example.eq.repository.EquipmentRepository;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public void nextDay(String campaignId) {
        List<Equipment> equipmentList = equipmentRepository.findAllByCampaignId(campaignId);
        for (Equipment equipment : equipmentList) {
            for (Item item : equipment.getItemList()) {
                if (item.getDaysToExpire() != null) {
                    if (item.getDaysToExpire() > 0) {
                        item.setDaysToExpire(item.getDaysToExpire() - 1);
                    }
                }
            }
        }
        try {
            equipmentRepository.saveAll(equipmentList);
        } catch (MongoException e) {
            throw new UnableToSaveException("Błąd podczas aktualizacji daty przydatności");
        }
    }

    public EquipmentDto readEquipment(String username, String campaignId, String characterName) {
        Equipment equipment = equipmentRepository.findByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName)
                .orElseThrow(() -> new NotFoundException("Nie znaleziono ekwipunku dla tej postaci"));
        return mapToDto(equipment);
    }

    public void createEquipment(EquipmentDto equipmentDto) {
        try {
            equipmentRepository.save(mapToEquipment(equipmentDto));
        } catch (MongoException e) {
            throw new UnableToSaveException("Błąd podczas zapisu");
        }
    }

    public void modifyEquipment(EquipmentDto equipmentDto) {
        Equipment equipment = equipmentRepository.findByUsernameAndCampaignIdAndCharacterName(equipmentDto.getUsername(), equipmentDto.getCampaignId(), equipmentDto.getCharacterName())
                .orElseThrow(() -> new NotFoundException("Nie znaleziono ekwipunku dla tej postaci"));
        equipment.setItemList(equipmentDto.getItemList());
        try {
            equipmentRepository.save(equipment);
        } catch (MongoException e) {
            throw new UnableToSaveException("Błąd podczas zapisu");
        }
    }

    public void deleteEquipment(String username, String campaignId, String characterName) {
        try {
            equipmentRepository.deleteByUsernameAndCampaignIdAndCharacterName(username, campaignId, characterName);
        } catch (MongoException e) {
            throw new UnableToDeleteException("Błąd podczas usuwania ekwipunku");
        }
    }

    private EquipmentDto mapToDto(Equipment equipment) {
        return new EquipmentDto(equipment.getUsername(), equipment.getCampaignId(), equipment.getCharacterName(), equipment.getItemList());
    }

    private Equipment mapToEquipment(EquipmentDto equipmentDto) {
        return new Equipment(equipmentDto.getUsername(), equipmentDto.getCampaignId(), equipmentDto.getCharacterName(), equipmentDto.getItemList());
    }
}
