package org.example.asterix_api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("characters")
public class AsterixCharacter {
    @Id
    private String id;
    private String name;
    private int age;
    private String profession;
}
