package org.example.asterix_api.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("characters")
public class Character {
    @Id
    private String id;
    private String name;
    private int age;
    private String profession;

    public Character() {}

    public Character(String id, String name, int age, String profession) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.profession = profession;
    }
}
