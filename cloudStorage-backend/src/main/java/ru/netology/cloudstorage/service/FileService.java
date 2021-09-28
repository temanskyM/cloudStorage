package ru.netology.cloudstorage.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingInputStream;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.DescriptionFileDto;
import org.openapitools.model.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.exception.StorageException;
import ru.netology.cloudstorage.exception.StorageFileExistException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileService {
    private final StorageService storageService;

    public List<DescriptionFileDto> getAll(@Nullable Integer limit) {
        try (Stream<Path> stream = storageService.loadAll()) {
            List<Path> pathList = stream
                    .filter(file -> !Files.isDirectory(file))
                    .collect(Collectors.toList());
            List<DescriptionFileDto> result = new ArrayList<>();
            for (int i = 0; i < pathList.size(); i++) {
                Path path = pathList.get(i);
                if (limit != null && limit == i)
                    break;
                DescriptionFileDto descriptionFileDto = new DescriptionFileDto();
                descriptionFileDto.setFilename(path.getFileName().toString());
                descriptionFileDto.setSize((int) Files.size(path));
                result.add(descriptionFileDto);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Unable to get list files", e);
        }
    }

    public FileDto get(String filename) {
        Resource resource = storageService.loadAsResource(filename);
        return buildFileDto(resource);
    }

    public void store(MultipartFile file) {
        storageService.store(file);
    }

    public void rename(String filenameBefore, String filenameAfter) {
        Resource resource = storageService.loadAsResource(filenameBefore);
        try {
            File file = resource.getFile();
            File file2 = new File(filenameAfter);

            if (file2.exists())
                throw new StorageFileExistException(String.format("File with name %S is exists", filenameAfter));
            boolean success = file.renameTo(file2);

            if (!success) {
                throw new StorageException("Unable to rename file");
            }
        } catch (IOException e) {
            throw new StorageException("Unable to rename file", e);
        }


    }

    private FileDto buildFileDto(Resource resource) {
        try {
            HashCode hashCode = new HashingInputStream(Hashing.md5(), resource.getInputStream()).hash();

            FileDto fileDto = new FileDto();
            fileDto.setFile(resource);
            fileDto.setHash(hashCode.toString());
            return fileDto;
        } catch (IOException e) {
            throw new RuntimeException("Unable to build fileDto");
        }
    }
}
