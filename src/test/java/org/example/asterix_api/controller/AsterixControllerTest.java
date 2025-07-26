package org.example.asterix_api.controller;

// Importiere das Repository für Charaktere
import org.example.asterix_api.repository.CharacterRepo;
// Importiere das Modell der Charaktere
import org.example.asterix_api.model.AsterixCharacter;
// Importiere Test-Annotations
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Importiere Spring Boot Test-Funktionen
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
// Importiere MockMvc für Web-Tests
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@SpringBootTest
// Erzeuge eine Spring Boot Test-Umgebung
@AutoConfigureMockMvc
// Konfiguriere MockMvc automatisch
class AsterixControllerTest {

    @Autowired
    // Füge MockMvc ein zum Testen von HTTP-Anfragen
    private MockMvc mockMvc;

    @Autowired
    // Füge das Charakter-Repository ein, um mit Daten zu arbeiten
    private CharacterRepo repo;

    @BeforeEach
        // Führe diese Methode vor jedem Test aus
    void setup() {
        // Leere die Datenbank vor jedem Test
        repo.deleteAll();

        // Speichere zwei Beispielcharaktere in die Datenbank
        repo.saveAll(Arrays.asList(
                new AsterixCharacter("1", "Asterix", 35, "Krieger"),
                new AsterixCharacter("2", "Obelix", 38, "Hinkelsteinlieferant")
        ));
    }

    @Test
        // Teste, ob alle Charaktere zurückgegeben werden
    void getAllCharacters_shouldReturnAllCharacters() throws Exception {
        // Mache eine GET-Anfrage an den Endpunkt /asterix/characters
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters"))
                // Prüfe, ob der HTTP Status 200 OK ist
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Prüfe, ob die Antwort genau die zwei Charaktere als JSON enthält
                .andExpect(MockMvcResultMatchers.content().json(
                        """
 [
     {
         "id": "1",
         "name": "Asterix",
         "age": 35,
         "profession": "Krieger"
     },
     {
         "id": "2",
         "name": "Obelix",
         "age": 38,
         "profession": "Hinkelsteinlieferant"
     }
 ]
 """
                ));
    }

    @Test
        // Teste, ob ein Charakter per ID zurückgegeben wird
    void getCharacterById_shouldReturnCharacter() throws Exception {
        // Mache eine GET-Anfrage an den Endpunkt /asterix/characters/1
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/1"))
                // Prüfe, ob der HTTP Status 200 OK ist
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Prüfe, ob die Antwort den Charakter mit ID 1 enthält
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        {
                            "id": "1",
                            "name": "Asterix",
                            "age": 35,
                            "profession": "Krieger"
                        }
                        """
                ));
    }
}
