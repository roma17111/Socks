package com.example.socks.servises;

import com.example.socks.models.Socks;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class SocksServiceImpl implements SocksService {

    private final static Map<Long,Socks> socks = new HashMap<>();

    private static Long number = 1L;

    @Override
    public Socks addSocks(Socks sock) {
        if (!socks.containsKey(number)) {
            socks.put(number++, sock);
            return sock;
        }
        number++;
        addSocks(sock);
        return sock;
    }

}
