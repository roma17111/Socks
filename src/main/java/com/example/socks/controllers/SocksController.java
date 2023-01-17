package com.example.socks.controllers;

import com.example.socks.models.Socks;
import com.example.socks.servises.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
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
    public ResponseEntity getAllSocks() {
        return ResponseEntity.ok(socksService.getAllSocks().toString());
    }
}
