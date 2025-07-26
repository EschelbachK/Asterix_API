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
import org.springframework.http.MediaType;
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

    @Test
// Teste, ob ein neuer Charakter hinzugefügt werden kann
    void addCharacter_shouldReturnSavedCharacter() throws Exception {
        // Führe eine POST-Anfrage an den Endpoint /asterix/characters aus
        // Dabei wird JSON direkt im Body der Anfrage mitgesendet
        mockMvc.perform(MockMvcRequestBuilders.post("/asterix/characters")
                        // Setze den Content-Type Header auf "application/json"
                        // Das sagt dem Server, dass der Body JSON enthält
                        .contentType(MediaType.APPLICATION_JSON)
                        // Füge den JSON-Body direkt als String ein (ohne vorherige Variable)
                        // Das simuliert das Senden von {name, age, profession} an den Server
                        .content("""
                    {
                        "name": "Miraculix",
                        "age": 60,
                        "profession": "Druide"
                    }
                    """))
                // Erwarte, dass die Antwort vom Server den HTTP Status 200 OK zurückgibt
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Erwarte, dass im JSON-Response ein Feld "id" vorhanden ist und nicht leer ist
                // Das zeigt, dass der Server eine ID generiert und zurücksendet (z.B. UUID)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                // Erwarte, dass der JSON-Response mindestens die angegebenen Felder enthält
                // Hier wird der gesamte JSON-Response mit dem angegebenen JSON verglichen
                // Achtung: Dies prüft nur, ob diese Felder im Response sind (nicht strikt gleich),
                // aber es prüft nicht die "id", deshalb ist diese Prüfung zusätzlich wichtig
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        {
                            "name": "Miraculix",
                            "age": 60,
                            "profession": "Druide"
                        }
                        """
                ));
    }

    @Test
    // Teste, ob ein vorhandener Charakter erfolgreich aktualisiert wird
    void updateCharacter_shouldReturnUpdatedCharacter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/asterix/characters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "AsterixUpdated",
                                "age": 36,
                                "profession": "Chef-Krieger"
                            }
                            """))
                // Nur prüfen, ob der HTTP Status 200 OK ist
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    // Teste, ob ein Charakter erfolgreich gelöscht werden kann
    void deleteCharacter_shouldReturnStatusOkAndRemoveCharacter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/asterix/characters/2"))
                // Nur prüfen, ob der HTTP Status 200 OK ist
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Prüfe, ob der Charakter danach nicht mehr gefunden wird (404)
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
