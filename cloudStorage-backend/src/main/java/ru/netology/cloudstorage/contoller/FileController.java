package ru.netology.cloudstorage.contoller;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.FileApi;
import org.openapitools.model.FileDto;
import org.openapitools.model.HasNameDto;
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

@RestController
@RequiredArgsConstructor
public class FileController implements FileApi {
    private final FileService fileService;

    @Override
    public ResponseEntity<Void> fileDelete(@NotNull @Valid String filename) {
        return null;
    }

    @RequestMapping(value = "/file",
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
    public ResponseEntity<Void> filePut(@Valid HasNameDto hasNameDto, @Valid String filename) {
        fileService.rename(filename, hasNameDto.getName());
        return ResponseEntity.ok().build();
    }
}
