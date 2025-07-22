package org.example.asterix_api.controller;

import org.example.asterix_api.dto.CharacterDTO;
import org.example.asterix_api.model.Character;
import org.example.asterix_api.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asterix")
public class AsterixController {

    private final CharacterService service;

    public AsterixController(CharacterService service) {
        this.service = service;
    }

    @PostMapping("/characters")
    public Character addCharacter(@RequestBody CharacterDTO dto) {
        return service.addCharacter(dto);
    }

    @GetMapping("/characters")
    public List<Character> getAllCharacters() {
        return service.getAllCharacters();
    }

    @GetMapping("/characters/{id}")
    public Character getCharacterById(@PathVariable String id) {
        return service.getCharacterById(id);
    }

    @PutMapping("/characters/{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody Character character) {
        return service.updateCharacter(id, character);
    }

    @DeleteMapping("/characters/{id}")
    public void deleteCharacter(@PathVariable String id) {
        service.deleteCharacter(id);
    }
}
