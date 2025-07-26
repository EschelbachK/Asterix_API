package org.example.asterix_api.controller;

import org.example.asterix_api.dto.CharacterDTO;
import org.example.asterix_api.model.AsterixCharacter;
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
    public AsterixCharacter addCharacter(@RequestBody CharacterDTO dto) {
        return service.addCharacter(dto);
    }

    @GetMapping("/characters")
    public List<AsterixCharacter> getAllCharacters() {
        return service.getAllCharacters();
    }

    @GetMapping("/characters/{id}")
    public AsterixCharacter getCharacterById(@PathVariable String id) {
        return service.getCharacterById(id);
    }

    @PutMapping("/characters/{id}")
    public AsterixCharacter updateCharacter(@PathVariable String id, @RequestBody AsterixCharacter asterixCharacter) {
        return service.updateCharacter(id, asterixCharacter);
    }

    @DeleteMapping("/characters/{id}")
    public void deleteCharacter(@PathVariable String id) {
        service.deleteCharacter(id);
    }
}
