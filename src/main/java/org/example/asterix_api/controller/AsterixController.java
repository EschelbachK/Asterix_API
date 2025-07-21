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

    // POST: Charakter speichern
    @PostMapping("/characters")
    public Character addCharacter(@RequestBody Character character) {
        return characterRepo.save(character);
    }

    // GET: Alle Charaktere abrufen
    @GetMapping("/characters")
    public List<Character> getCharacters() {
        return characterRepo.findAll();
    }
}
