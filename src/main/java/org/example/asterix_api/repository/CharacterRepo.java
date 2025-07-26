package org.example.asterix_api.repository;

import org.example.asterix_api.model.AsterixCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepo extends MongoRepository<AsterixCharacter, String> {
}
