package com.example.socks.servises;

import com.example.socks.models.Socks;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SocksService {
    Socks addSocks(Socks sock);

    Map<Long, Socks> getAllSocks();

    @Nullable
    boolean deletePartSocks(Long number);

    boolean putSocks(Long number, Integer i);
}
