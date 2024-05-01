package org.example.service.file;

import org.example.model.Animal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlReader implements FileReader {

    @Override
    public FileExtension getSupportedExtension() {
        return FileExtension.XML;
    }

    @Override
    public List<Animal> read(MultipartFile file) {
        Document document = getDocument(file);
        NodeList animalNodes = document.getElementsByTagName("animal");

        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < animalNodes.getLength(); i++) {
            Element animalElement = (Element) animalNodes.item(i);
            Animal animal = mapToAnimal(animalElement);
            animals.add(animal);
        }

        return animals;
    }

    private Document getDocument(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            return createDocument(inputStream);
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException e) {
            throw new RuntimeException("Error reading XML file", e);
        }
    }

    private Document createDocument(InputStream inputStream)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        document.getDocumentElement().normalize();

        return document;
    }

    private Animal mapToAnimal(Element animalElement) {
        Animal animal = new Animal();
        animal.setName(getString(animalElement, "name"));
        animal.setType(getString(animalElement, "type"));
        animal.setSex(getString(animalElement, "sex"));
        animal.setWeight(getInteger(animalElement, "weight"));
        animal.setCost(getInteger(animalElement, "cost"));

        return animal;
    }

    private String getString(Element animalElement, String tagName) {
        Node tagElement = animalElement.getElementsByTagName(tagName).item(0);
        if (tagElement == null) return null;

        return tagElement.getChildNodes().item(0).getNodeValue();
    }

    private Integer getInteger(Element animalElement, String tagName) {
        String stringValue = getString(animalElement, tagName);

        return stringValue == null ? null : Integer.parseInt(stringValue);
    }
}
