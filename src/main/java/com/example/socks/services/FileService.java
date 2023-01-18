package com.example.socks.services;

public interface FileService {

    boolean saveFileSocks(String json);

    String readFileSocks();

    boolean cleanDataFileSocks();
}
