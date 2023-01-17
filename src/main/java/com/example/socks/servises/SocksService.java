package com.example.socks.servises;

import com.example.socks.models.Socks;
import org.springframework.stereotype.Service;

@Service
public interface SocksService {
    Socks addSocks(Socks sock);
}
