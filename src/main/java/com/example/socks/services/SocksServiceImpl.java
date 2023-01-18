package com.example.socks.services;

import com.example.socks.models.Color;
import com.example.socks.models.CottonPart;
import com.example.socks.models.Size;
import com.example.socks.models.Socks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class SocksServiceImpl implements SocksService {

    private static Set<Socks> socks = new HashSet<>();

    private final FileService fileService;
    private static Long number = 1L;

    public SocksServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        try {
            readToFile();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Nullable
    public Socks addSocks(Socks sock) {
        socks.add(sock);
        saveFile();
        return sock;
    }

    @Override
    public Integer getSocks(Color color, Size size, CottonPart cottonPart) {
        Integer i = 0;
        for (Socks sock : socks) {
            if (color == sock.getColor() &&
                    size == sock.getSize() &&
                    cottonPart == sock.getCottonPart()) {
                i = sock.getQuantity();
            }
        }
        return i;
    }

    @Override
    public boolean putSocks(Socks sock, Integer i) {
        if (socks.contains(sock)) {
            if (i > sock.getQuantity())
                return false;
            sock.setQuantity(sock.getQuantity() - i);
            if (sock.getQuantity() == 0) {
                socks.remove(sock);
                saveFile();
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean deleteSocks(Socks sock) {
        saveFile();
        return socks.remove(sock);
    }

    public void saveFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socks);
            fileService.saveFileSocks(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readToFile() {

        try {
            String json = fileService.readFileSocks();
            socks = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
