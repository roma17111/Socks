package com.example.socks.models;
import java.util.Objects;

public class Socks {

    private final Color color;
    private final Size size;
    private final CottonPart cottonPart;
    private Integer quantity;

    public Socks(Color color, Size size, CottonPart cottonPart, Integer quantity) {
        this.color = color;
        this.size = size;
        this.cottonPart = cottonPart;
        if (quantity == null || quantity < 0 || quantity > 100_000) {
            throw new IllegalArgumentException();
        }else {
            this.quantity = quantity;
        }
    }


    public Color getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public CottonPart getCottonPart() {
        return cottonPart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity > getQuantity()) {
            throw new IllegalArgumentException();
        }else {
            this.quantity = quantity;
        }
    }

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
