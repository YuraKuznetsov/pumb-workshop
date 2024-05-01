package org.example.service.file;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.model.Animal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReader implements FileReader {

    @Override
    public FileExtension getSupportedExtension() {
        return FileExtension.CSV;
    }

    @Override
    public List<Animal> read(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())))) {
            csvReader.skip(1);
            return mapToAnimals(csvReader.readAll());
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }

    private List<Animal> mapToAnimals(List<String[]> rows) {
        List<Animal> animals = new ArrayList<>();

        for (String[] row : rows) {
            Animal animal = new Animal();

            animal.setName(row[0]);
            animal.setType(row[1]);
            animal.setSex(row[2]);
            animal.setWeight(getInteger(row[3]));
            animal.setCost(getInteger(row[4]));

            animals.add(animal);
        }

        return animals;
    }

    private Integer getInteger(String stringValue) {
        return stringValue.equals("") ? null : Integer.parseInt(stringValue);
    }
}
