package com.example.cal.service;

import com.example.cal.dto.CalendarDto;
import com.example.cal.entity.Calendar;
import com.example.cal.exception.IncorrectIdException;
import com.example.cal.exception.NotFoundException;
import com.example.cal.exception.UnableToDeleteException;
import com.example.cal.exception.UnableToSaveException;
import com.example.cal.repository.CalendarRepository;
import com.example.cal.exception.ConnectException;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final RestTemplate restTemplate;

    public void nextDay(String campaignId){
        Calendar calendar = calendarRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new NotFoundException("Nie znaleziono kalendarza dla podanej kampanii"));
        calendar.setCurrentDay(calendar.getCurrentDay().plusDays(1));
        try {
            calendarRepository.save(calendar);
        }catch (MongoException e){
            throw new UnableToSaveException("Błąd podczas zapisu");
        }
        updateEquipment(campaignId);
    }

    public CalendarDto readCalendar(String campaignId){
        Calendar calendar = calendarRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new NotFoundException("Nie znaleziono kalendarza dla podanej kampanii"));
        return mapToDto(calendar);
    }
    public void createCalendar(CalendarDto calendarDto){
        if(calendarRepository.findByCampaignId(calendarDto.getCampaignId()).isPresent()){
            throw new IncorrectIdException("Dane dla podanego Id już istnieją");
        }
        try {
            calendarRepository.save(mapToCalendar(calendarDto));
        }catch (MongoException e){
            throw new UnableToSaveException("Błąd podczas zapisu");
        }
    }
    public void modifyCalendar(String campaignId, CalendarDto calendarDto){
        if(!campaignId.equals(calendarDto.getCampaignId())){
            throw new IncorrectIdException("Podane Id nie są takie same");
        }
        Calendar calendar = calendarRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new NotFoundException("Nie znaleziono kalendarza dla podanej kampanii"));
        calendar.setCurrentDay(calendarDto.getCurrentDay());
        calendar.setEvents(calendarDto.getEvents());
        try {
            calendarRepository.save(calendar);
        }catch (MongoException e){
            throw new UnableToSaveException("Błąd podczas zapisu");
        }
    }
    public void deleteCalendar(String campaignId){
        calendarRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new NotFoundException("Zasób nie istnieje lub został już usunięty"));
        try {
            calendarRepository.deleteByCampaignId(campaignId);
        }catch (MongoException e){
            throw new UnableToDeleteException("Błąd podczas usuwania");
        }
    }
    private CalendarDto mapToDto(Calendar calendar){
        return new CalendarDto(calendar.getCampaignId(),calendar.getCurrentDay(),calendar.getEvents());
    }
    private Calendar mapToCalendar(CalendarDto calendarDto){
        return new Calendar(calendarDto.getCampaignId(),calendarDto.getCurrentDay(),calendarDto.getEvents());
    }
    private void updateEquipment(String campaignId){
        try{
            restTemplate.put("http://equipment/nextDay?campaignId="+campaignId,null);
        }catch (RuntimeException e){
            throw new ConnectException("Problem z połączeniem do serwisu 'equipment'");
        }

    }
}
