package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Animal;
import org.example.model.SearchParams;
import org.example.service.AnimalService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping(path = "files/uploads")
    public void uploadFile(@RequestBody MultipartFile file) {
        animalService.addAnimals(file);
    }

    @GetMapping(path = "/animals")
    public List<Animal> getAnimals(@Valid SearchParams searchParams) {
        return animalService.getAnimals(searchParams);
    }
}
