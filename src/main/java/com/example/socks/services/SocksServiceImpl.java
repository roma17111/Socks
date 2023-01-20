package com.example.socks.services;

import com.example.socks.models.Color;
import com.example.socks.models.CottonPart;
import com.example.socks.models.Size;
import com.example.socks.models.Socks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

@Service
public class SocksServiceImpl implements SocksService {

    private static Set<Socks> socks = new HashSet<>();

    private final FileService fileService;


    public SocksServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        try {
            readToFile();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Nullable
    public Socks addSocks(Socks sock) throws IOException {
        if (!socks.contains(sock)) {
            socks.add(sock);
            createSocksJsonFileOperation("Приход носков " +
                    sock.getQuantity(), sock.getColor(),sock.getSize(), sock.getCottonPart());
            saveFile();
            return sock;
        }
        if (socks.contains(sock)) {
            addSock(sock.getQuantity());
            saveFile();

            createSocksJsonFileOperation("Приход носков " +
                    sock.getQuantity(), sock.getColor(),sock.getSize(), sock.getCottonPart());
            return sock;
        }
        throw new RuntimeException();
    }


    @Override
    public Integer getSocks(Color color, Size size, CottonPart cottonPart) {
        Integer i = 0;
        for (Socks sock : socks) {
            if (color.equals(sock.getColor()) &&
                    size.equals(sock.getSize())&&
                    cottonPart.equals(sock.getCottonPart())) {
                i = sock.getQuantity();
            }
        }
        return i;
    }

    @Override
    public boolean putSocks(Color color, Size size, CottonPart cottonPart, Integer i) throws IOException {
        for (Socks sock : socks) {
            if (color.equals(sock.getColor())  &&
                    size.equals(sock.getSize()) &&
                    cottonPart.equals(sock.getCottonPart())) {
                int a = sock.getQuantity();
                if (i > a){
                    return false;
                }
                sock.setQuantity(sock.getQuantity() - i);
                createSocksJsonFileOperation("Взято носков " + i,  sock.getColor(),sock.getSize(), sock.getCottonPart());
                if (sock.getQuantity() == 0) {
                    socks.remove(sock);
                    saveFile();
                    createSocksJsonFileOperation("Взято носков " + i,sock.getColor(),sock.getSize(), sock.getCottonPart());
                    return true;
                }
            }
        }

        saveFile();
        return true;
    }

    private Integer addSock(Integer i) {
        for (Socks sock : socks) {
            if (i <= 0) {
                i = Math.abs(i);
            }
            sock.setQuantity(sock.getQuantity()+ i);
        }
        return i;
    }

    private Integer deleteSock(Integer i) {
        for (Socks sock : socks) {
            if (i < 0) {
                i = Math.abs(i);
            }
            if (i > sock.getQuantity()) {
                i = sock.getQuantity();
            }
            sock.setQuantity(sock.getQuantity()- i);
        }
        return i;
    }

    @Override
    public boolean deleteSocks(Socks sock) throws IOException {
        if (socks.contains(sock)) {
            deleteSock(sock.getQuantity());
            createSocksJsonFileOperation("Списано носков " + sock.getQuantity() + "\n",
                    sock.getColor(), sock.getSize(), sock.getCottonPart());
            saveFile();
            return true;
        }
        if (sock.getQuantity() == 0) {
            socks.remove(sock);
            saveFile();
            createSocksJsonFileOperation("Списано носков "+ sock.getQuantity() +"\n", sock.getColor(),sock.getSize(), sock.getCottonPart());
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
            socks = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Path createSocksTextFileAll() throws IOException {
        Path path = fileService.createTempFile("SocksFiles");
        try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            for (Socks sock : socks) {
                writer.append(sock.toString());
            }
        }
        return path;
    }

    @Override
    public Path createSocksJsonFileOperation(String operation, Color color,Size size, CottonPart cottonPart) throws IOException {
        Path path = fileService.createTempFile("SocksFiles");
        try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.WRITE)) {
            writer.append(operation);
            writer.append("\n" +
                    "Цвет - " + color.toString() +" "+ color.getColor()+"\n" +
                    "Размер - " + size.toString() +" "+size.getSize()+ "\n" +
                    "Процент хлопка - " + cottonPart.toString() + " "+cottonPart.getPercent() + "%"+ "\n" )
            ;
        }
        return path;
    }


    @Override
    public void addSocksFromFile(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] array = StringUtils.split(line, '|');
           Socks socks1 = new Socks((Color.valueOf(array[0])), (Size.valueOf(array[1])), (CottonPart.valueOf(array[2]))
                    , Integer.valueOf(array[3]));
            addSocks(socks1);
        }
    }

}
