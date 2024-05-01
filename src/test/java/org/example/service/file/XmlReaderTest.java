package org.example.service.file;

import org.example.model.Animal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class XmlReaderTest {

    @Mock
    private MultipartFile mockMultipartFile;

    @Test
    void testReadValidXmlFile() throws Exception {
        String xmlData = getXmlData();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlData.getBytes());
        when(mockMultipartFile.getInputStream()).thenReturn(inputStream);

        XmlReader xmlReader = new XmlReader();
        List<Animal> expected = List.of(
                new Animal("Buddy", "cat", "female", 41, 78),
                new Animal("Cooper", null, "female", 46, 23),
                new Animal("Duke", "cat", "male", 33, 108),
                new Animal("Lola", "dog", "male", 35, 105)
        );
        List<Animal> actual = xmlReader.read(mockMultipartFile);

        assertEquals(expected, actual);
    }

    private String getXmlData() {
        return """
                <animals>
                    <animal>
                        <name>Buddy</name>
                        <type>cat</type>
                        <sex>female</sex>
                        <weight>41</weight>
                        <cost>78</cost>
                    </animal>
                    <animal>
                        <name>Cooper</name>
                        <sex>female</sex>
                        <weight>46</weight>
                        <cost>23</cost>
                    </animal>
                    <animal>
                        <name>Duke</name>
                        <type>cat</type>
                        <sex>male</sex>
                        <weight>33</weight>
                        <cost>108</cost>
                    </animal>
                    <animal>
                        <name>Lola</name>
                        <type>dog</type>
                        <sex>male</sex>
                        <weight>35</weight>
                        <cost>105</cost>
                    </animal>
                </animals>
                """;
    }
}
