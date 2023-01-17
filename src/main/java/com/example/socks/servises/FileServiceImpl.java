package com.example.socks.servises;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService{
    @Value("${name.of.data.file}")
    private String fileName;

    @Value("${path.to.data.file}")
    private String fileDataPath;

    @Override
    public boolean saveFileSocks(String json) {
        try {
            cleanDataFileSocks();
            Files.writeString(Path.of(fileDataPath, fileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readFileSocks() {
        try {
            return Files.readString(Path.of(fileDataPath, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanDataFileSocks() {
        try {
            Files.deleteIfExists(Path.of(fileDataPath, fileName));
            Files.createFile(Path.of(fileDataPath, fileName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
