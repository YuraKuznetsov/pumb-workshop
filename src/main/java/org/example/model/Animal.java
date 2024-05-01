package org.example.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Animal {

    Integer id;
    String name;
    String type;
    String sex;
    Integer weight;
    Integer cost;
    Integer category;

    public Animal(String name, String type, String sex, Integer weight, Integer cost, Integer category) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.weight = weight;
        this.cost = cost;
        this.category = category;
    }

    public Animal(String name, String type, String sex, Integer weight, Integer cost) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.weight = weight;
        this.cost = cost;
    }
}
