package com.example.socks.servises;

import com.example.socks.models.Socks;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SocksService {
    Socks addSocks(Socks sock);

    Map<Long, Socks> getAllSocks();
}
