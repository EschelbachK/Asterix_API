package org.example.asterix_api.service;

import org.example.asterix_api.dto.CharacterDTO;
import org.example.asterix_api.model.Character;
import org.example.asterix_api.repository.CharacterRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    private CharacterRepo mockRepo;
    private IdService mockIdService;
    private CharacterService service;

    @BeforeEach
    void setUp() {
        mockRepo = Mockito.mock(CharacterRepo.class);
        mockIdService = Mockito.mock(IdService.class);
        service = new CharacterService(mockRepo, mockIdService);
    }

    @Test
    void getAllCharacters() {
        // GIVEN
        List<Character> expected = List.of(
                new Character("1", "Asterix", 30, "Warrior"),
                new Character("2", "Obelix", 35, "Strongman")
        );
        when(mockRepo.findAll()).thenReturn(expected);

        // WHEN
        List<Character> actual = service.getAllCharacters();

        // THEN
        assertEquals(expected, actual);
        verify(mockRepo).findAll();
    }

    @Test
    void getCharacterById() {
        // GIVEN
        Character character = new Character("1", "Asterix", 30, "Warrior");
        when(mockRepo.findById("1")).thenReturn(Optional.of(character));

        // WHEN
        Character actual = service.getCharacterById("1");

        // THEN
        assertEquals(character, actual);
        verify(mockRepo).findById("1");
    }

    @Test
    void updateCharacter() {
        // GIVEN
        Character oldCharacter = new Character("1", "Asterix", 30, "Warrior");
        Character updateData = new Character(null, "Asterix Updated", 31, "Chief");
        Character updatedCharacter = new Character("1", "Asterix Updated", 31, "Chief");

        when(mockRepo.findById("1")).thenReturn(Optional.of(oldCharacter));
        when(mockRepo.save(any(Character.class))).thenReturn(updatedCharacter);

        // WHEN
        Character actual = service.updateCharacter("1", updateData);

        // THEN
        assertEquals(updatedCharacter, actual);
        verify(mockRepo).save(any(Character.class));
    }

    @Test
    void deleteCharacter() {
        // WHEN
        service.deleteCharacter("1");

        // THEN
        verify(mockRepo).deleteById("1");
    }

    @Test
    void addCharacter_shouldGenerateIdAndSave() {
        // GIVEN
        String generatedId = "random-id-123";
        when(mockIdService.generateId()).thenReturn(generatedId);

        CharacterDTO dto = new CharacterDTO("Asterix", 30, "Warrior");

        Character savedCharacter = new Character(generatedId, "Asterix", 30, "Warrior");
        when(mockRepo.save(any(Character.class))).thenReturn(savedCharacter);

        // WHEN
        Character actual = service.addCharacter(dto);

        // THEN
        assertEquals(generatedId, actual.getId());
        assertEquals(dto.name(), actual.getName());
        assertEquals(dto.age(), actual.getAge());
        assertEquals(dto.profession(), actual.getProfession());

        verify(mockIdService, times(1)).generateId();
        verify(mockRepo).save(any(Character.class));
    }
}
