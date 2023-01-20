package com.example.socks.services;

import com.example.socks.models.Color;
import com.example.socks.models.CottonPart;
import com.example.socks.models.Size;
import com.example.socks.models.Socks;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

@Service
public interface SocksService {

    @Nullable
    Socks addSocks(Socks sock) throws IOException;

    Integer getSocks(Color color, Size size, CottonPart cottonPart);


    boolean putSocks(Color color, Size size, CottonPart cottonPart, Integer i) throws IOException;

    boolean deleteSocks(Socks sock) throws IOException;

    Path createSocksTextFileAll() throws IOException;

    boolean createSocksJsonFileOperation(String operation, Color color,Size size, CottonPart cottonPart) throws IOException;

    void addSocksFromFile(InputStream inputStream) throws IOException;
}
