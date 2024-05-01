package org.example.repository;

import org.example.model.Animal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalRowMapper implements RowMapper<Animal> {

    @Override
    public Animal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Animal animal = new Animal();

        animal.setId(rs.getInt("id"));
        animal.setName(rs.getString("name"));
        animal.setType(rs.getString("type"));
        animal.setSex(rs.getString("sex"));
        animal.setWeight(rs.getInt("weight"));
        animal.setCost(rs.getInt("cost"));
        animal.setCategory(rs.getInt("category"));

        return animal;
    }
}
