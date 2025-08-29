package com.example.data;

import com.example.data.dto.SpellDto;
import com.example.data.entity.Spell;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.SpellRepository;
import com.example.data.service.SpellService;
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
public class SpellServiceTests {
    @Mock
    SpellRepository spellRepository;

    @InjectMocks
    SpellService spellService;

    String name = "name";
    String description = "description";

    @Nested
    public class SaveAllTests{
        Spell spell = new Spell(name, description);
        SpellDto spellDto = new SpellDto(name, description);

        @Test
        public void saveAllTest(){
                spellService.saveAll(List.of(spellDto));
                verify(spellRepository).saveAll(List.of(spell));
        }

        @Test
        public void databaseErrorTest(){
            when(spellRepository.saveAll(List.of(spell))).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class, () -> spellService.saveAll(List.of(spellDto)));
        }
    }

    @Nested
    public class FindByNameTests{
        Spell spell = new Spell(name, description);
        SpellDto spellDto = new SpellDto(name, description);

        @Test
        public void findByNameTest(){
            when(spellRepository.findByName(name)).thenReturn(Optional.of(spell));
            SpellDto dto = spellService.findByName(name);
            assertEquals(spellDto, dto);
        }

        @Test
        public void nothingFoundTest(){
            when(spellRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> spellService.findByName(name));
        }
    }

    @Nested
    public class SaveTests{
        Spell spell = new Spell(name, description);
        SpellDto spellDto = new SpellDto(name, description);

        @Test
        public void saveTest(){
            spellService.save(spellDto);
            verify(spellRepository).save(spell);
        }

        @Test
        public void databaseErrorTest(){
            when(spellRepository.save(spell)).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class, () -> spellService.save(spellDto));
        }
    }

    @Nested
    public class ChangeTests{
        Spell spell = new Spell(name, description);
        SpellDto spellDto = new SpellDto(name, description);
        SpellDto spellDto2 = new SpellDto("new"+name, "new"+description);

        @Test
        public void changeTest(){
            when(spellRepository.findByName(name)).thenReturn(Optional.of(spell));
            spellService.change(spellDto, spellDto2);
            ArgumentCaptor<Spell> spellCaptor = ArgumentCaptor.forClass(Spell.class);
            verify(spellRepository).save(spellCaptor.capture());
            assertEquals(spell, spellCaptor.getValue());
        }

        @Test
        public void nothingFoundTest(){
            when(spellRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> spellService.change(spellDto,spellDto2));
            verify(spellRepository, never()).save(spell);
        }

        @Test
        public void databaseErrorTest(){
            when(spellRepository.findByName(name)).thenReturn(Optional.of(spell));
            when(spellRepository.save(spell)).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class, () -> spellService.change(spellDto,spellDto2));
        }
    }

    @Nested
    public class DeleteTests{
        Spell spell = new Spell(name, description);

        @Test
        public void deleteTest(){
            when(spellRepository.findByName(name)).thenReturn(Optional.of(spell));
            spellService.delete(name);
            ArgumentCaptor<Spell> spellCaptor = ArgumentCaptor.forClass(Spell.class);
            verify(spellRepository).delete(spellCaptor.capture());
            assertEquals(spell, spellCaptor.getValue());
        }

        @Test
        public void nothingFoundTest(){
            when(spellRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> spellService.delete(name));
            verify(spellRepository, never()).save(spell);
        }

        @Test
        public void databaseErrorTest(){
            when(spellRepository.findByName(name)).thenReturn(Optional.of(spell));
            doThrow(new MongoException("")).when(spellRepository).delete(spell);
            assertThrows(FailedToDeleteException.class, () -> spellService.delete(name));
        }
    }
}
