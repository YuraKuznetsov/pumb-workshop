package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Animal;
import org.example.model.SearchParams;
import org.example.repository.AnimalRepository;
import org.example.service.file.AnimalFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalFileService animalFileService;
    private final AnimalRepository animalRepository;

    public void addAnimals(MultipartFile file) {
        List<Animal> animals = animalFileService.retrieveAnimals(file);
        List<Animal> processedAnimals = animals.stream()
                .filter(this::isValidAnimal)
                .peek(animal -> animal.setCategory(getCategory(animal.getCost())))
                .toList();
        animalRepository.saveAll(processedAnimals);
    }

    private boolean isValidAnimal(Animal animal) {
        return animal.getName() != null && !animal.getName().equals("") &&
                animal.getType() != null && !animal.getType().equals("") &&
                animal.getSex() != null && !animal.getSex().equals("") &&
                animal.getWeight() != null && animal.getWeight() >= 0 &&
                animal.getCost() != null && animal.getCost() >= 0;
    }

    private Integer getCategory(Integer cost) {
        if (cost <= 20) return 1;
        if (cost <= 40) return 2;
        if (cost <= 60) return 3;
        return 4;
    }

    public List<Animal> getAnimals(SearchParams searchParams) {
        injectionCheck(searchParams.getOrderBy());
        List<Animal> sortedAnimals = animalRepository.getSorted(searchParams.getOrderBy());

        return sortedAnimals.stream()
                .filter(animal -> searchParams.getType() == null ||
                        animal.getType().equals(searchParams.getType()))
                .filter(animal -> searchParams.getCategory() == null ||
                        animal.getCategory() == searchParams.getCategory())
                .filter(animal -> searchParams.getSex() == null ||
                        animal.getSex().equals(searchParams.getSex()))
                .collect(Collectors.toList());
    }

    private void injectionCheck(String orderBy) {
        if (orderBy == null) return;

        for (Field animalField : Animal.class.getDeclaredFields()) {
            if (animalField.getName().equals(orderBy)) return;
        }

        throw new IllegalArgumentException("Class animal doesn't have such field: " + orderBy);
    }
}
