package org.example.asterix_api.controller;

import org.example.asterix_api.model.Character;
import org.example.asterix_api.repository.CharacterRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asterix")
public class AsterixController {

    private final CharacterRepo characterRepo;

    public AsterixController(CharacterRepo characterRepo) {
        this.characterRepo = characterRepo;
    }

    @PostMapping("/characters")
    public Character addCharacter(@RequestBody Character character) {
        return characterRepo.save(character);
    }

    @GetMapping("/characters")
    public List<Character> getAllCharacters() {
        return characterRepo.findAll();
    }

    @GetMapping("/characters/{id}")
    public Character getCharacterById(@PathVariable String id) {
        return characterRepo.findById(id).orElse(null);
    }

    @PutMapping("/characters/{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody Character character) {
        Character existing = characterRepo.findById(id).orElse(null);
        if (existing != null) {
            return characterRepo.save(
                    existing
                            .withName(character.getName())
                            .withAge(character.getAge())
                            .withProfession(character.getProfession())
            );
        }
        return null;
    }

    @DeleteMapping("/characters/{id}")
    public void deleteCharacter(@PathVariable String id) {
        characterRepo.deleteById(id);
    }
}
