package com.example.application_1;

public class Rate {
    private long id;
    private String code;
    private String name;
    private double rate;

    public Rate() {}

    public Rate(String code, String name, double rate) {
        this.code = code;
        this.name = name;
        this.rate = rate;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }
}
