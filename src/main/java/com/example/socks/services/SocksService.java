package com.example.socks.services;

import com.example.socks.models.Color;
import com.example.socks.models.CottonPart;
import com.example.socks.models.Size;
import com.example.socks.models.Socks;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SocksService {

    @Nullable
    Socks addSocks(Socks sock);

    Socks getSocks();

    Integer getSocks(Color color, Size size, CottonPart cottonPart);

    boolean putSocks(Socks sock, Integer i);

    boolean deleteSocks(Socks sock);
}
