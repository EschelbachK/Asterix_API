package org.example.asterix_api.service;

import org.example.asterix_api.dto.CharacterDTO;
import org.example.asterix_api.model.AsterixCharacter;
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
        List<AsterixCharacter> expected = List.of(
                new AsterixCharacter("1", "Asterix", 30, "Warrior"),
                new AsterixCharacter("2", "Obelix", 35, "Strongman")
        );
        when(mockRepo.findAll()).thenReturn(expected);

        // WHEN
        List<AsterixCharacter> actual = service.getAllCharacters();

        // THEN
        assertEquals(expected, actual);
        verify(mockRepo).findAll();
    }

    @Test
    void getCharacterById() {
        // GIVEN
        AsterixCharacter asterixCharacter = new AsterixCharacter("1", "Asterix", 30, "Warrior");
        when(mockRepo.findById("1")).thenReturn(Optional.of(asterixCharacter));

        // WHEN
        AsterixCharacter actual = service.getCharacterById("1");

        // THEN
        assertEquals(asterixCharacter, actual);
        verify(mockRepo).findById("1");
    }

    @Test
    void updateCharacter() {
        // GIVEN
        AsterixCharacter oldAsterixCharacter = new AsterixCharacter("1", "Asterix", 30, "Warrior");
        AsterixCharacter updateData = new AsterixCharacter(null, "Asterix Updated", 31, "Chief");
        AsterixCharacter updatedAsterixCharacter = new AsterixCharacter("1", "Asterix Updated", 31, "Chief");

        when(mockRepo.findById("1")).thenReturn(Optional.of(oldAsterixCharacter));
        when(mockRepo.save(any(AsterixCharacter.class))).thenReturn(updatedAsterixCharacter);

        // WHEN
        AsterixCharacter actual = service.updateCharacter("1", updateData);

        // THEN
        assertEquals(updatedAsterixCharacter, actual);
        verify(mockRepo).save(any(AsterixCharacter.class));
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

        AsterixCharacter savedAsterixCharacter = new AsterixCharacter(generatedId, "Asterix", 30, "Warrior");
        when(mockRepo.save(any(AsterixCharacter.class))).thenReturn(savedAsterixCharacter);

        // WHEN
        AsterixCharacter actual = service.addCharacter(dto);

        // THEN
        assertEquals(generatedId, actual.getId());
        assertEquals(dto.name(), actual.getName());
        assertEquals(dto.age(), actual.getAge());
        assertEquals(dto.profession(), actual.getProfession());

        verify(mockIdService, times(1)).generateId();
        verify(mockRepo).save(any(AsterixCharacter.class));
    }
}
