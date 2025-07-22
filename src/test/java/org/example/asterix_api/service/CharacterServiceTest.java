package org.example.asterix_api.service;

import org.example.asterix_api.dto.CharacterDTO;
import org.example.asterix_api.model.Character;
import org.example.asterix_api.repository.CharacterRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    @Test
    void getAllCharacters() {

        // Mock für Repository erstellen
        CharacterRepo mockRepo = Mockito.mock(CharacterRepo.class);
        // Mock für IdService erstellen, weil es im Konstruktor steht
        IdService mockIdService = Mockito.mock(IdService.class);

        // Service mit beiden Mocks erstellen
        CharacterService service = new CharacterService(mockRepo, mockIdService);

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
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void getCharacterById() {
        CharacterRepo mockRepo = Mockito.mock(CharacterRepo.class);
        IdService mockIdService = Mockito.mock(IdService.class);
        CharacterService service = new CharacterService(mockRepo, mockIdService);

        // GIVEN
        Character character = new Character("1", "Asterix", 30, "Warrior");
        when(mockRepo.findById("1")).thenReturn(Optional.of(character));

        // WHEN
        Character actual = service.getCharacterById("1");

        // THEN
        assertEquals(character, actual);
        verify(mockRepo, times(1)).findById("1");
    }

    @Test
    void updateCharacter() {
        CharacterRepo mockRepo = Mockito.mock(CharacterRepo.class);
        IdService mockIdService = Mockito.mock(IdService.class);
        CharacterService service = new CharacterService(mockRepo, mockIdService);

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
        CharacterRepo mockRepo = Mockito.mock(CharacterRepo.class);
        IdService mockIdService = Mockito.mock(IdService.class);
        CharacterService service = new CharacterService(mockRepo, mockIdService);

        // WHEN
        service.deleteCharacter("1");

        // THEN
        verify(mockRepo, times(1)).deleteById("1");
    }

    @Test
    void addCharacter_shouldGenerateIdAndSave() {
        // Mocks erstellen
        CharacterRepo mockRepo = Mockito.mock(CharacterRepo.class);
        IdService mockIdService = Mockito.mock(IdService.class);

        // Service mit Mocks erstellen
        CharacterService service = new CharacterService(mockRepo, mockIdService);

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
        verify(mockRepo, times(1)).save(any(Character.class));
    }

}
