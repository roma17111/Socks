package com.example.socks.services;

import java.io.File;
import java.nio.file.Path;

public interface FileService {

    boolean saveFileSocks(String json);

    String readFileSocks();

    boolean cleanDataFileSocks();

    File getDataFile();

    Path createTempFile(String suffix);


    Path saveFileSocksOperation(String s);

    File getDataFileOperation();
}
