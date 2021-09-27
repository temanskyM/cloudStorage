package ru.netology.cloudstorage.contoller;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.FileApi;
import org.openapitools.model.DescriptionFileDto;
import org.openapitools.model.FileDto;
import org.openapitools.model.HasFileNameDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.service.FileService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController implements FileApi {
    private final FileService fileService;

    @Override
    public ResponseEntity<Void> fileDelete(@NotNull @Valid String filename) {
        return null;
    }

    @RequestMapping(value = "/api/file",
            produces = {"multipart/form-data"},
            method = RequestMethod.GET)
    public ResponseEntity<MultiValueMap<String, Object>> fileGet(@Valid String filename) {
        FileDto fileDto = fileService.get(filename);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("hash", fileDto.getHash());
        formData.add("file", fileDto.getFile());
        return ResponseEntity
                .ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(formData);
    }

    @Override
    public ResponseEntity<Void> filePost(@Valid String filename, String hash, @Valid MultipartFile file) {
        fileService.store(file);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> filePut(@NotNull @Valid String filename, @Valid HasFileNameDto hasFileNameDto) {
        fileService.rename(filename, hasFileNameDto.getFilename());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<DescriptionFileDto>> getAll(@Valid Integer limit) {
        List<DescriptionFileDto> result = fileService.getAll(limit);
        return ResponseEntity.ok(result);
    }
}
