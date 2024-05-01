package org.example.service.file;

import org.springframework.web.multipart.MultipartFile;

public enum FileExtension {
    CSV,
    XML;

    public static FileExtension of(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) throw new IllegalArgumentException("File name can't be null");
        String extension = filename.substring(filename.lastIndexOf('.') + 1);

        return FileExtension.valueOf(extension.toUpperCase());
    }
}
