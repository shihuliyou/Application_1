package com.example.application_1;

public class Rate {
    private final String code;
    private final String name;
    private final double rate;

    public Rate(String code, String name, double rate) {
        this.code = code;
        this.name = name;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }
}
