package com.example.data;

import com.example.data.dto.EnemyDto;
import com.example.data.entity.Enemy;
import com.example.data.enums.Rarity;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.EnemyRepository;
import com.example.data.service.EnemyService;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnemyServiceTests {

    String name = "name";
    String image = "image";
    Rarity rarity = Rarity.COMMON;
    int cr = 1;
    int xp = 1;
    String description = "description";
    CharacterSheet characterSheet = new CharacterSheet(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

    @Mock
    EnemyRepository enemyRepository;

    @InjectMocks
    EnemyService enemyService;

    @Nested
    public class SaveAllTests {
        EnemyDto enemyDto = new EnemyDto(name, image, rarity, cr, xp, description, characterSheet);
        Enemy enemy = new Enemy(name, image, rarity, cr, xp, description, characterSheet);

        @Test
        public void saveAllTest() {
            enemyService.saveAll(List.of(enemyDto));
            ArgumentCaptor<List<Enemy>> enemyCaptor = ArgumentCaptor.forClass(List.class);
            verify(enemyRepository).saveAll(enemyCaptor.capture());
            assertEquals(List.of(enemy), enemyCaptor.getValue());
        }

        @Test
        public void saveFailedTest() {
            when(enemyRepository.saveAll(List.of(enemy))).thenThrow(MongoException.class);
            assertThrows(FailedToSaveException.class, () -> enemyService.saveAll(List.of(enemyDto)));
            ArgumentCaptor<List<Enemy>> enemyCaptor = ArgumentCaptor.forClass(List.class);
            verify(enemyRepository).saveAll(enemyCaptor.capture());
        }
    }

    @Nested
    public class FindByNameTests{
        EnemyDto enemyDto = new EnemyDto(name, image, rarity, cr, xp, description, characterSheet);
        Enemy enemy = new Enemy(name, image, rarity, cr, xp, description, characterSheet);

        @Test
        public void findByNameTest(){
            when(enemyRepository.findByName(name)).thenReturn(Optional.of(enemy));
            EnemyDto dto = enemyService.findByName(name);
            assertEquals(enemyDto, dto);
        }

        @Test
        public void nothingFoundTest(){
            when(enemyRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class, () -> enemyService.findByName(name));
        }
    }

    @Nested
    public class FindAllByRarityAndCrTests{
        EnemyDto enemyDto = new EnemyDto(name, image, rarity, cr, xp, description, characterSheet);
        Enemy enemy = new Enemy(name, image, rarity, cr, xp, description, characterSheet);

        @Test
        public void findAllByRarityAndCrTest(){
            List<EnemyDto> testList = List.of(enemyDto, enemyDto, enemyDto);
            when(enemyRepository.findAllByRarityAndCr(rarity, cr)).thenReturn(List.of(enemy, enemy, enemy, enemy));
            List<EnemyDto> list = enemyService.findAllByRarityAndCr(rarity, cr, 3);
            assertEquals(list.size(), testList.size());
            assertEquals(list.get(0), testList.get(0));
            assertEquals(list.get(1), testList.get(1));
            assertEquals(list.get(2), testList.get(2));
        }
    }

    @Nested
    public class SaveTests{
        EnemyDto enemyDto = new EnemyDto(name, image, rarity, cr, xp, description, characterSheet);
        Enemy enemy = new Enemy(name, image, rarity, cr, xp, description, characterSheet);

        @Test
        public void saveTest(){
            enemyService.save(enemyDto);
            ArgumentCaptor<Enemy> enemyCaptor = ArgumentCaptor.forClass(Enemy.class);
            verify(enemyRepository).save(enemyCaptor.capture());
            assertEquals(enemy, enemyCaptor.getValue());
        }

        @Test
        public void failedToSaveTest(){
            when(enemyRepository.save(enemy)).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class,() -> enemyService.save(enemyDto));
        }
    }

    @Nested
    public class ChangeTests{
        EnemyDto enemyDto = new EnemyDto(name, image, rarity, cr, xp, description, characterSheet);
        Enemy enemy = new Enemy(name, image, rarity, cr, xp, description, characterSheet);

        @Test
        public void changeTest(){
            when(enemyRepository.findByName(name)).thenReturn(Optional.of(enemy));
            enemyService.change(name, enemyDto);
            ArgumentCaptor<Enemy> enemyCaptor = ArgumentCaptor.forClass(Enemy.class);
            verify(enemyRepository).save(enemyCaptor.capture());
            assertEquals(enemy, enemyCaptor.getValue());
        }

        @Test
        public void enemyNotFoundTest(){
            when(enemyRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class,() -> enemyService.change(name, enemyDto));
            ArgumentCaptor<Enemy> enemyCaptor = ArgumentCaptor.forClass(Enemy.class);
            verify(enemyRepository, never()).save(enemyCaptor.capture());
        }

        @Test
        public void failedToSaveTest(){
            when(enemyRepository.findByName(name)).thenReturn(Optional.of(enemy));
            when(enemyRepository.save(enemy)).thenThrow(new MongoException(""));
            assertThrows(FailedToSaveException.class,() -> enemyService.change(name, enemyDto));
        }
    }

    @Nested
    public class DeleteTests{
        EnemyDto enemyDto = new EnemyDto(name, image, rarity, cr, xp, description, characterSheet);
        Enemy enemy = new Enemy(name, image, rarity, cr, xp, description, characterSheet);

        @Test
        public void deleteTest(){
            when(enemyRepository.findByName(name)).thenReturn(Optional.of(enemy));
            enemyService.delete(name);
            verify(enemyRepository).delete(enemy);
        }

        @Test
        public void enemyNotFoundTest(){
            when(enemyRepository.findByName(name)).thenReturn(Optional.empty());
            assertThrows(NothingFoundException.class,() -> enemyService.delete(name));
            verify(enemyRepository, never()).delete(enemy);
        }
    }

}
