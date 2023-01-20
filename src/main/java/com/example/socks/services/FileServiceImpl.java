package com.example.socks.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Value("${name.of.data.file}")
    private String fileName;

    @Value("${path.to.data.file}")
    private String fileDataPath;

    @Value("name.of.data.fileOperations")
    private String fileNameOperation;

    @Value("path.to.data.fileOperations")
    private String fileDataPathOperation;

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

    @Override
    public File getDataFile() {
        return new File(fileDataPath + "/" + fileName);
    }

    @Override
    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(fileDataPath), "tenpfile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path saveFileSocksOperation(String s) {
        try {
            return Files.createTempFile(Path.of(fileDataPath), "tenpfile", s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public File getDataFileOperation() {
        return new File(fileDataPathOperation + "/" + fileNameOperation);
    }
}
