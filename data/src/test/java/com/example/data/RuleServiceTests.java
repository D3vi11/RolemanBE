package com.example.data;

import com.example.data.dto.RuleDto;
import com.example.data.entity.Rule;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.RuleRepository;
import com.example.data.service.RuleService;
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
public class RuleServiceTests {
    @Mock
    RuleRepository ruleRepository;

    @InjectMocks
    RuleService ruleService;

    private static final String name = "name";
    private static final String description = "description";

    @Nested
    public class SaveAllTests{
        RuleDto ruleDto = new RuleDto(name, description);
        Rule rule = new Rule(name, description);

        @Test
        public void saveAllTest(){
            ruleService.saveAll(List.of(ruleDto));
            verify(ruleRepository).saveAll(List.of(rule));
        }

        @Test
        public void failedToSaveTest(){
            doThrow(new MongoException("")).when(ruleRepository).saveAll(List.of(rule));
            assertThrows(FailedToSaveException.class, () -> ruleService.saveAll(List.of(ruleDto)));
            verify(ruleRepository).saveAll(List.of(rule));
        }
    }

    @Nested
    public class FindByNameTests{
        RuleDto ruleDto = new RuleDto(name, description);
        Rule rule = new Rule(name, description);

        @Test
        public void findByNameTest(){
            when(ruleRepository.findByName(name)).thenReturn(Optional.of(rule));
            RuleDto dto = ruleService.findByName(name);
            verify(ruleRepository).findByName(name);
            assertEquals(ruleDto, dto);
        }

        @Test
        public void ruleNotFoundTest(){
            when(ruleRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> ruleService.findByName(name));
        }

    }

    @Nested
    public class SaveTests{
        RuleDto ruleDto = new RuleDto(name, description);
        Rule rule = new Rule(name, description);

        @Test
        public void saveTest(){
            ruleService.save(ruleDto);
            verify(ruleRepository).save(rule);
        }

        @Test
        public void failedToSaveTest(){
            doThrow(new MongoException("")).when(ruleRepository).save(rule);
            assertThrows(FailedToSaveException.class, () -> ruleService.save(ruleDto));
            verify(ruleRepository).save(rule);
        }
    }

    @Nested
    public class ChangeTests{
        RuleDto ruleDto = new RuleDto(name, description);
        RuleDto ruleDto2 = new RuleDto("new"+name, "new"+description);
        Rule rule = new Rule(name, description);

        @Test
        public void changeTest(){
            when(ruleRepository.findByName(name)).thenReturn(Optional.of(rule));
            ruleService.change(ruleDto, ruleDto2);
            ArgumentCaptor<Rule> ruleCaptor = ArgumentCaptor.forClass(Rule.class);
            verify(ruleRepository).save(ruleCaptor.capture());
            assertEquals(ruleDto2.getName(), ruleCaptor.getValue().getName());
            assertEquals(ruleDto2.getDescription(), ruleCaptor.getValue().getDescription());
        }

        @Test
        public void ruleNotFoundTest(){
            when(ruleRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> ruleService.change(ruleDto, ruleDto2));
            ArgumentCaptor<Rule> ruleCaptor = ArgumentCaptor.forClass(Rule.class);
            verify(ruleRepository, never()).save(ruleCaptor.capture());
        }

        @Test
        public void databaseErrorTest(){
            when(ruleRepository.findByName(name)).thenReturn(Optional.of(rule));
            when(ruleRepository.save(rule)).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class, () -> ruleService.change(ruleDto, ruleDto2));
        }
    }

    @Nested
    public class DeleteTests{
        Rule rule = new Rule(name, description);

        @Test
        public void changeTest(){
            when(ruleRepository.findByName(name)).thenReturn(Optional.of(rule));
            ruleService.delete(name);
            ArgumentCaptor<Rule> ruleCaptor = ArgumentCaptor.forClass(Rule.class);
            verify(ruleRepository).delete(ruleCaptor.capture());
        }

        @Test
        public void ruleNotFoundTest(){
            when(ruleRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> ruleService.delete(name));
            ArgumentCaptor<Rule> ruleCaptor = ArgumentCaptor.forClass(Rule.class);
            verify(ruleRepository, never()).delete(ruleCaptor.capture());
        }

        @Test
        public void databaseErrorTest(){
            when(ruleRepository.findByName(name)).thenReturn(Optional.of(rule));
            doThrow(new MongoException("")).when(ruleRepository).delete(rule);
            assertThrows(FailedToDeleteException.class, () -> ruleService.delete(name));
        }
    }
}
