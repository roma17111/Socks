package com.example.socks.controllers;

import com.example.socks.models.Socks;
import com.example.socks.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    @Operation(description = "Посмотреть на складе количество партий носков")
    @ApiResponse(responseCode = "200",
            description = "Список партий носков на складе")
    @ApiResponse(responseCode = "400",
            description = "параметры запроса отсутствуют или имеют некорректный формат;")
    @ApiResponse(responseCode = "500",
            description = "произошла ошибка, не зависящая от вызывающей стороны.")
    public ResponseEntity getAllSocks() {
        return ResponseEntity.ok(socksService.getAllSocks().toString());
    }

    @DeleteMapping("/delete{num}")
    @Operation(description = "Выбраковка товара")
    @ApiResponse(responseCode = "200",
            description = "Товары списаны")
    @ApiResponse(responseCode = "400",
            description = "параметры запроса отсутствуют или имеют некорректный формат;")
    @ApiResponse(responseCode = "500",
            description = "произошла ошибка, не зависящая от вызывающей стороны.")
    public ResponseEntity delete(@RequestParam Long num) {
        return ResponseEntity.ok(socksService.deletePartSocks(num));
    }

    @PutMapping("/take")
    @Operation(description = "Забрать носки со склада")
    @ApiResponse(responseCode = "200",
            description = "Вы забрали носки")
    @ApiResponse(responseCode = "400",
            description = "параметры запроса отсутствуют или имеют некорректный формат;")
    @ApiResponse(responseCode = "500",
            description = "произошла ошибка, не зависящая от вызывающей стороны.")
    public ResponseEntity takeSocks(@RequestParam Long numberParty, Integer takeSocks) {
        return ResponseEntity.ok(socksService.putSocks(numberParty, takeSocks));
    }
}
