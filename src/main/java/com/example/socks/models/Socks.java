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
        String s = "Носки :" + "\n" +
                "Цвет - " + getColor() + "\n" +
                "Размер - " + size + size.getSize() + "\n" +
                "Процент хлопка - " + cottonPart.getPercent() + "\n" +
                "Количество пар на складе - " + getQuantity()+ "\n";

        return s.toString();
    }
}
