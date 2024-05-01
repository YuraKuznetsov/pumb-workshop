package org.example.service;

import org.example.model.Animal;
import org.example.model.SearchParams;
import org.example.repository.AnimalRepository;
import org.example.service.file.AnimalFileService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalFileService animalFileService;
    @Mock
    private AnimalRepository animalRepository;
    @InjectMocks
    private AnimalService animalService;

    @Test
    void testFilteringByType() {
        SearchParams searchParams = new SearchParams();
        searchParams.setType("Cat");

        when(animalRepository.getSorted(any())).thenReturn(getRepoAnimals());

        List<Animal> expected = List.of(
                new Animal("Name1", "Cat", "male", 10, 10, 1),
                new Animal("Name2", "Cat", "female", 20, 20, 2)
        );
        List<Animal> actual = animalService.getAnimals(searchParams);
        assertEquals(expected, actual);
    }

    @Test
    void testFilteringByCategory() {
        SearchParams searchParams = new SearchParams();
        searchParams.setCategory(2);

        when(animalRepository.getSorted(any())).thenReturn(getRepoAnimals());

        List<Animal> expected = List.of(
                new Animal("Name2", "Cat", "female", 20, 20, 2),
                new Animal("Name6", "Parrot", "female", 60, 60, 2)
        );
        List<Animal> actual = animalService.getAnimals(searchParams);
        assertEquals(expected, actual);
    }

    @Test
    void testFilteringBySex() {
        SearchParams searchParams = new SearchParams();
        searchParams.setSex("male");

        when(animalRepository.getSorted(any())).thenReturn(getRepoAnimals());

        List<Animal> expected = List.of(
                new Animal("Name1", "Cat", "male", 10, 10, 1),
                new Animal("Name3", "Dog", "male", 30, 30, 3),
                new Animal("Name5", "Parrot", "male", 50, 50, 1)
        );
        List<Animal> actual = animalService.getAnimals(searchParams);
        assertEquals(expected, actual);
    }

    @Test
    void testFilteringByMultipleParams() {
        SearchParams searchParams = new SearchParams();
        searchParams.setType("Dog");
        searchParams.setSex("female");

        when(animalRepository.getSorted(any())).thenReturn(getRepoAnimals());

        List<Animal> expected = List.of(
                new Animal("Name4", "Dog", "female", 40, 40, 4)
        );
        List<Animal> actual = animalService.getAnimals(searchParams);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddAnimals() {
        MockMultipartFile mockFile = new MockMultipartFile("file", new byte[] {});

        when(animalFileService.retrieveAnimals(any(MultipartFile.class)))
                .thenReturn(getFileAnimals());

        animalService.addAnimals(mockFile);

        List<Animal> processedAnimals = List.of(
                new Animal("Name1", "Cat", "male", 10, 10, 1),
                new Animal("Name2", "Cat", "female", 20, 20, 1),
                new Animal("Name3", "Dog", "male", 30, 30, 2),
                new Animal("Name4", "Dog", "female", 40, 40, 2),
                new Animal("Name5", "Parrot", "male", 50, 50, 3),
                new Animal("Name6", "Parrot", "female", 61, 61, 4)
        );

        verify(animalRepository).saveAll(processedAnimals);
    }

    private List<Animal> getRepoAnimals() {
        return List.of(
                new Animal("Name1", "Cat", "male", 10, 10, 1),
                new Animal("Name2", "Cat", "female", 20, 20, 2),
                new Animal("Name3", "Dog", "male", 30, 30, 3),
                new Animal("Name4", "Dog", "female", 40, 40, 4),
                new Animal("Name5", "Parrot", "male", 50, 50, 1),
                new Animal("Name6", "Parrot", "female", 60, 60, 2)
        );
    }

    private List<Animal> getFileAnimals() {
        return List.of(
                new Animal("Name1", "Cat", "male", 10, 10),
                new Animal("Name2", "Cat", "female", 20, 20),
                new Animal("Name3", "Dog", "male", 30, 30),
                new Animal("Name4", "Dog", "female", 40, 40),
                new Animal("Name5", "Parrot", "male", 50, 50),
                new Animal("Name6", "Parrot", "female", 61, 61),
                new Animal("Name7", "Parrot", "female", 61, null),
                new Animal("Name8", "Parrot", "female", null, 61),
                new Animal("Name9", "Parrot", null, 61, 61),
                new Animal("Name9", null, "female", 61, 61),
                new Animal(null, "Parrot", "female", 61, 61),
                new Animal("Name9", "Parrot", "female", -100, 61),
                new Animal("Name9", "Parrot", "female", 61, -100)
        );
    }
}
