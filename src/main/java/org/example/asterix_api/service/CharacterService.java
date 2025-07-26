package org.example.asterix_api.service;

import org.example.asterix_api.dto.CharacterDTO;
import org.example.asterix_api.model.AsterixCharacter;
import org.example.asterix_api.repository.CharacterRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepo repo;
    private final IdService idService;

    public CharacterService(CharacterRepo repo, IdService idService) {
        this.repo = repo;
        this.idService = idService;
    }

    public AsterixCharacter addCharacter(CharacterDTO dto) {
        AsterixCharacter asterixCharacter = new AsterixCharacter(
                idService.generateId(),
                dto.name(),
                dto.age(),
                dto.profession()
        );
        return repo.save(asterixCharacter);
    }

    public List<AsterixCharacter> getAllCharacters() {
        return repo.findAll();
    }

    public AsterixCharacter getCharacterById(String id) {
        return repo.findById(id).orElse(null);
    }

    public AsterixCharacter updateCharacter(String id, AsterixCharacter asterixCharacter) {
        AsterixCharacter existing = repo.findById(id).orElse(null);
        if (existing != null) {
            return repo.save(
                    existing
                            .withName(asterixCharacter.getName())
                            .withAge(asterixCharacter.getAge())
                            .withProfession(asterixCharacter.getProfession())
            );
        }
        return null;
    }

    public void deleteCharacter(String id) {
        repo.deleteById(id);
    }
}
