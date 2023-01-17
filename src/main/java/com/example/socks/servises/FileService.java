package com.example.socks.servises;

public interface FileService {

    boolean saveFileSocks(String json);

    String readFileSocks();

    boolean cleanDataFileSocks();
}
