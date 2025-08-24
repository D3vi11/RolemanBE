package com.example.cal;

import com.example.cal.dto.CalendarDto;
import com.example.cal.entity.Calendar;
import com.example.cal.exception.IncorrectIdException;
import com.example.cal.exception.NotFoundException;
import com.example.cal.repository.CalendarRepository;
import com.example.cal.service.CalendarService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Not;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CalendarServiceTests {

    @Mock
    CalendarRepository calendarRepository;
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    CalendarService calendarService;

    String campaignId = "abc";

    @Nested
    public class nextDayTests {
        Calendar calendar = new Calendar(campaignId, LocalDate.of(2000, 1,1), new ArrayList<>());

        @Test
        public void nextDayTest() {
            when(calendarRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(calendar));
            calendarService.nextDay(campaignId);
            ArgumentCaptor<Calendar> calendarCaptor = ArgumentCaptor.forClass(Calendar.class);
            verify(calendarRepository).save(calendarCaptor.capture());
            assertEquals(2, calendarCaptor.getValue().getCurrentDay().getDayOfMonth());
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
            verify(restTemplate).put(stringCaptor.capture(), eq(null));
            assertEquals("http://equipment/nextDay?campaignId="+campaignId, stringCaptor.getValue());
        }

        @Test
        public void calendarNotFoundTest(){
            when(calendarRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> calendarService.nextDay(campaignId));
            ArgumentCaptor<Calendar> calendarCaptor = ArgumentCaptor.forClass(Calendar.class);
            verify(calendarRepository, never()).save(calendarCaptor.capture());
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
            verify(restTemplate, never()).put(stringCaptor.capture(), eq(null));
        }
    }

    @Nested
    public class readCalendarTests {
        Calendar calendar = new Calendar(campaignId, LocalDate.of(2000, 1,1), new ArrayList<>());

        @Test
        public void readCalendarTest() {
            when(calendarRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(calendar));
            calendarService.readCalendar(campaignId);
        }

        @Test
        public void calendarNotFoundTest(){
            when(calendarRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> calendarService.readCalendar(campaignId));
        }
    }

    @Nested
    public class createCalendarTests {
        Calendar calendar = new Calendar(campaignId, LocalDate.of(2000, 1,1), new ArrayList<>());
        CalendarDto calendarDto = new CalendarDto(campaignId, LocalDate.of(2000, 1,1), new ArrayList<>());

        @Test
        public void createCalendarTest(){
            when(calendarRepository.findByCampaignId(calendarDto.getCampaignId())).thenReturn(Optional.empty());
            calendarService.createCalendar(calendarDto);
            ArgumentCaptor<Calendar> calendarCaptor = ArgumentCaptor.forClass(Calendar.class);
            verify(calendarRepository).save(calendarCaptor.capture());
            assertEquals(campaignId, calendarCaptor.getValue().getCampaignId());
        }

        @Test
        public void calendarExistsTest(){
            when(calendarRepository.findByCampaignId(calendarDto.getCampaignId())).thenReturn(Optional.of(calendar));
            assertThrows(IncorrectIdException.class, () -> calendarService.createCalendar(calendarDto));
            ArgumentCaptor<Calendar> calendarCaptor = ArgumentCaptor.forClass(Calendar.class);
            verify(calendarRepository, never()).save(calendarCaptor.capture());
        }
    }

    @Nested
    public class modifyCalendarTests {
        Calendar calendar = new Calendar(campaignId, LocalDate.of(2000, 1,1), new ArrayList<>());
        CalendarDto calendarDto = new CalendarDto(campaignId, LocalDate.of(2000, 1,1), new ArrayList<>());

        @Test
        public void modifyCalendarTest(){
            when(calendarRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(calendar));
            calendarService.modifyCalendar(campaignId, calendarDto);
            ArgumentCaptor<Calendar> calendarCaptor = ArgumentCaptor.forClass(Calendar.class);
            verify(calendarRepository).save(calendarCaptor.capture());
            assertEquals(campaignId, calendarCaptor.getValue().getCampaignId());
        }

        @Test
        public void calendarNotFoundTest(){
            when(calendarRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> calendarService.modifyCalendar(campaignId, calendarDto));
            ArgumentCaptor<Calendar> calendarCaptor = ArgumentCaptor.forClass(Calendar.class);
            verify(calendarRepository, never()).save(calendarCaptor.capture());
        }

    }

    @Nested
    public class deleteCalendarTests {
        Calendar calendar = new Calendar(campaignId, LocalDate.of(2000, 1,1), new ArrayList<>());

        @Test
        public void deleteCalendarTest(){
            when(calendarRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(calendar));
            calendarService.deleteCalendar(campaignId);
            verify(calendarRepository).deleteByCampaignId(campaignId);
        }

        @Test
        public void calendarNotFoundTest(){
            when(calendarRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> calendarService.deleteCalendar(campaignId));
            verify(calendarRepository, never()).deleteByCampaignId(campaignId);
        }
    }
}
