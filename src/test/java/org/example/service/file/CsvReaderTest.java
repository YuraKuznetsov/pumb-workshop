package org.example.service.file;

import org.example.model.Animal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvReaderTest {

    @Mock
    private MultipartFile mockMultipartFile;

    @Test
    void testReadValidCsvFile() throws IOException {
        String csvData = getCsvData();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
        when(mockMultipartFile.getInputStream()).thenReturn(inputStream);

        CsvReader csvReader = new CsvReader();
        List<Animal> expected = List.of(
                new Animal("Buddy", "cat", "female", 41, 78),
                new Animal("Cooper", "", "female", 46, 23),
                new Animal("Duke", "cat", "male", 33, 108),
                new Animal("Lola", "dog", "male", 35, 105)
        );
        List<Animal> actual = csvReader.read(mockMultipartFile);

        assertEquals(expected, actual);
    }

    private String getCsvData() {
        return """
                Name,Type,Sex,Weight,Cost
                Buddy,cat,female,41,78
                Cooper,,female,46,23
                Duke,cat,male,33,108
                Lola,dog,male,35,105
                """;
    }
}
