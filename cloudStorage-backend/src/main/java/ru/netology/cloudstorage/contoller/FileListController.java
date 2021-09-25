package ru.netology.cloudstorage.contoller;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.ListApi;
import org.openapitools.model.DescriptionFileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.netology.cloudstorage.service.FileService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileListController implements ListApi {
    private final FileService fileService;

    @Override
    public ResponseEntity<List<DescriptionFileDto>> getAll(@Valid Integer limit) {
        List<DescriptionFileDto> result = fileService.getAll(limit);
        return ResponseEntity.ok(result);
    }
}
