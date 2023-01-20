package com.example.socks.controllers;

import com.example.socks.models.Color;
import com.example.socks.models.CottonPart;
import com.example.socks.models.Size;
import com.example.socks.models.Socks;
import com.example.socks.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Склад носков", description = "Операции со складом")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("/add")
    @Operation(description = "Добавить партию на склад")
    @ApiResponse(responseCode = "200",
            description = "удалось добавить приход")
    @ApiResponse(responseCode = "400",
            description = "параметры запроса отсутствуют или имеют некорректный формат;")
    @ApiResponse(responseCode = "500",
            description = "произошла ошибка, не зависящая от вызывающей стороны.")
    public ResponseEntity addSocks(@RequestBody Socks sock) {
        try {
            return ResponseEntity.ok(socksService.addSocks(sock));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get")
    @Operation(description = "Посмотреть на складе количество партий носков")
    @ApiResponse(responseCode = "200",
            description = "Список партий носков на складе")
    @ApiResponse(responseCode = "400",
            description = "параметры запроса отсутствуют или имеют некорректный формат;")
    @ApiResponse(responseCode = "500",
            description = "произошла ошибка, не зависящая от вызывающей стороны.")
    public ResponseEntity getAllSocks(@RequestParam Color color,
                                      @RequestParam Size size,
                                      @RequestParam CottonPart cottonPart) {
        return ResponseEntity.ok("Количество носков на складе - " + socksService.getSocks(color, size, cottonPart).toString());
    }

    @DeleteMapping("/delete")
    @Operation(description = "Выбраковка товара")
    @ApiResponse(responseCode = "200",
            description = "Товары списаны")
    @ApiResponse(responseCode = "400",
            description = "параметры запроса отсутствуют или имеют некорректный формат;")
    @ApiResponse(responseCode = "500",
            description = "произошла ошибка, не зависящая от вызывающей стороны.")
    public ResponseEntity delete(@RequestBody Socks sock) {
        return ResponseEntity.ok(socksService.deleteSocks(sock));
    }

    @PutMapping("/take")
    @Operation(description = "Забрать носки со склада")
    @ApiResponse(responseCode = "200",
            description = "Вы забрали носки")
    @ApiResponse(responseCode = "400",
            description = "параметры запроса отсутствуют или имеют некорректный формат;")
    @ApiResponse(responseCode = "500",
            description = "произошла ошибка, не зависящая от вызывающей стороны.")
    public ResponseEntity takeSocks(@RequestParam Color color,
                                    @RequestParam Size size,
                                    @RequestParam CottonPart cottonPart,
                                    @RequestParam Integer integer) {
        return ResponseEntity.ok(socksService.putSocks(color, size, cottonPart, integer));
    }

    @GetMapping("/download/all")
    @Operation(description = "Загрузка всех носков")
    @ApiResponse(responseCode = "200",
            description = "Successfully")
    public ResponseEntity downloadAllRecipes() {
        try {
            Path path = socksService.createSocksTextFileAll();
            InputStreamResource inputStream = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"AllSocks.doc\"")
                    .contentLength(Files.size(path))
                    .body(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @PostMapping(value = "/importspcks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addSocksFromPcFile(@RequestParam MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()){
            socksService.addSocksFromFile(inputStream);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
           e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
