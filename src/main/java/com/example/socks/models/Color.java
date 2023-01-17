package com.example.socks.models;

public enum Color {
    BLACK("чёрный"),
    WHITE("Белый"),
    GRAY("Серый"),
    RAD("Красный"),
    YELLOW("Жёлтый");

    String color;

    Color(String color) {
        this.color = color;
    }
}
