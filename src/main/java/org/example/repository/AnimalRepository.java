package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.model.Animal;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnimalRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void saveAll(List<Animal> animals) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO animal (name, type, sex, weight, cost, category) " +
                        "VALUES (:name, :type, :sex, :weight, :cost, :category)",
                SqlParameterSourceUtils.createBatch(animals));
    }

    public List<Animal> getSorted(String orderBy) {
        return jdbcTemplate.query(
                "SELECT id, name, type, sex, weight, cost, category FROM animal ORDER BY " + orderBy,
                new AnimalRowMapper());
    }
}
