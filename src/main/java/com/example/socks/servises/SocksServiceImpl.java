package com.example.socks.servises;

import com.example.socks.models.Socks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class SocksServiceImpl implements SocksService {

    private static Map<Long, Socks> socks = new HashMap<>();

    private final FileService fileService;
    private static Long number = 1L;

    public SocksServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        try {
            readToFile();
        } catch (RuntimeException e) {
        }
    }

    @Override
    @Nullable
    public Socks addSocks(Socks sock) {
        if (!socks.containsKey(number)) {
            socks.put(number++, sock);
            saveFile();
            return sock;
        }

        number++;
        addSocks(sock);
        return sock;
    }

    @Override
    @Nullable
    public Map<Long, Socks> getAllSocks() {
        return socks;
    }

    @Override
    @Nullable
    public boolean deletePartSocks(Long number) {
        if (socks.containsKey(number)) {
            socks.remove(number);
            saveFile();
            return true;
        }
        return false;
    }

    public void saveFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socks);
            fileService.saveFileSocks(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readToFile() {

        try {
            String json = fileService.readFileSocks();
            socks = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Socks>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
