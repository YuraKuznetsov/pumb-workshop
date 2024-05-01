package org.example.service.file;

import org.example.model.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileReader {

    FileExtension getSupportedExtension();
    List<Animal> read(MultipartFile file);
}
