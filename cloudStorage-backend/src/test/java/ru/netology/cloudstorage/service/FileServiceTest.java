package ru.netology.cloudstorage.service;

import org.junit.jupiter.api.Test;
import org.openapitools.model.DescriptionFileDto;
import org.openapitools.model.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.AbstractContainerTest;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest extends AbstractContainerTest {
    @Autowired
    private FileService fileService;

    @Test
    void getAll() {
        String filename = "nameGetAll";
        createFile(filename);

        List<DescriptionFileDto> fileDtos = fileService.getAll(3);
        assertNotEquals(0, fileDtos.size());
    }

    @Test
    void get() {
        String filename = "nameGet";
        MultipartFile file = createFile(filename);

        FileDto fileDto = fileService.get(filename);
        assertNotNull(fileDto);
        assertNotNull(fileDto.getFile());
        assertNotNull(fileDto.getHash());
    }

    @Test
    void store() {
        String filename = "nameStore";
        createFile(filename);
    }

    @Test
    void rename() {
        String filename = "nameRename";
        createFile(filename);

        String filenameBefore = filename + UUID.randomUUID().toString();

        fileService.rename(filename, filenameBefore);
    }

    private MultipartFile createFile(String filename) {
        byte[] fileContent = "fileContent".getBytes();
        MultipartFile file = new MockMultipartFile(filename, filename, null, fileContent);
        fileService.store(file);
        return file;
    }

}