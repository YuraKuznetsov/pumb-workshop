package org.example.service.file;

import lombok.RequiredArgsConstructor;
import org.example.model.Animal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalFileService {

    private final List<FileReader> fileReaders;

    public List<Animal> retrieveAnimals(MultipartFile file) {
        FileExtension extension = getExtension(file);
        FileReader fileReader = getFileReader(extension);

        return fileReader.read(file);
    }

    private FileExtension getExtension(MultipartFile file) {
        try {
            return FileExtension.of(file);
        } catch (IllegalArgumentException e) {
            // throw custom rest exception
            throw new RuntimeException(e);
        }
    }

    private FileReader getFileReader(FileExtension extension) {
        for (FileReader fileReader : fileReaders) {
            if (fileReader.getSupportedExtension() == extension) {
                return fileReader;
            }
        }
        throw new IllegalArgumentException("No service that can read file with extension: " + extension);
    }
}
