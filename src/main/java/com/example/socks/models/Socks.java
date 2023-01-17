package com.example.socks.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;

@Data
@AllArgsConstructor
@NonNull
@NoArgsConstructor
public class Socks {

    private  Color color;
    private  Size size;
    private  CottonPart cottonPart;
    private Integer quantity;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return color == socks.color && size == socks.size && cottonPart == socks.cottonPart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPart);
    }

    @Override
    public String toString() {
        return "Носки:" + "\n" +
                "Цвет - " + color + "\n" +
                "Размер - " + size + "\n" +
                "Процент хлопка - " + cottonPart + "\n" +
                "Количество пар на складе - " + quantity + "\n";
    }
}
