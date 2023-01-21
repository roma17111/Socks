package com.example.socks.controllers;

import com.example.socks.models.Socks;
import com.example.socks.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files/recipe")
public class FilesController {

    private final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Загрузить носок с компьютера")
    @ApiResponse(responseCode = "200",
            description = "Загрузка прошла успешно")
    public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
        File file = fileService.getDataFile();
        if (file.exists()) {
            InputStreamResource inputStream = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Socks.json\"")
                    .contentLength(file.length())
                    .body(inputStream);

        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    @Operation(description = "Загрузить носок на компьютер")
    @ApiResponse(responseCode = "200",
            description = "Загрузка прошла успешно")
    public ResponseEntity<Void> uploadDataFile(@NotNull @RequestParam MultipartFile file) throws IOException {
        fileService.cleanDataFileSocks();
        File file1 = fileService.getDataFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file1)){
            IOUtils.copy(file.getInputStream(), fileOutputStream);
            return ResponseEntity.ok().build();
        }    catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/download/operations")
    @Operation(description = "Загрузка всех операций")
    @ApiResponse(responseCode = "200",
            description = "Successfully")
    public ResponseEntity downloadAllRecipesOperations() {
        try {
            Path path = fileService.getDataFileOperation().toPath();
                    InputStreamResource inputStream = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SockOperation.txt\"")
                    .contentLength(Files.size(path))
                    .body(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
