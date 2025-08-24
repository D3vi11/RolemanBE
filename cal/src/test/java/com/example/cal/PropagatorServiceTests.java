package com.example.cal;

import com.example.cal.dto.CalendarDto;
import com.example.cal.service.CalendarService;
import com.example.cal.service.PropagatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PropagatorServiceTests {

    @Mock
    CalendarService calendarService;

    @InjectMocks
    PropagatorService propagatorService;

    String campainId = "abc";

    @Test
    public void createTest(){
        propagatorService.create(campainId);
        verify(calendarService).createCalendar(new CalendarDto(campainId, LocalDate.of(2000,1,1), new ArrayList<>()));
    }

    @Test
    public void deleteTest(){
        propagatorService.delete(campainId);
        verify(calendarService).deleteCalendar(campainId);
    }
}
