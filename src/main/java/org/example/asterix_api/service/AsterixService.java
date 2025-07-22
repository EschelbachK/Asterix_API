package org.example.asterix_api.service;

import org.example.asterix_api.model.Character;
import org.example.asterix_api.repository.CharacterRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AsterixService {

    private final CharacterRepo repo;

    public AsterixService(CharacterRepo repo) {
        this.repo = repo;
    }

    public Character addCharacter(Character character) {
        return repo.save(character);
    }

    public List<Character> getAllCharacters() {
        return repo.findAll();
    }

    public Character getCharacterById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Character updateCharacter(String id, Character character) {
        Character existing = repo.findById(id).orElse(null);
        if (existing != null) {
            return repo.save(
                    existing
                            .withName(character.getName())
                            .withAge(character.getAge())
                            .withProfession(character.getProfession())
            );
        }
        return null;
    }

    public void deleteCharacter(String id) {
        repo.deleteById(id);
    }
}
