package com.example.tugasbesarpbonarikotikac.model;

import java.util.ArrayList;

public class StatistikPutusan {

    private int totalPutusan;
    private double rataRataVonis;
    private double rataRataDenda;
    private String jenisNarkotikaTerbanyak;
    private String[] distribusiPeran;

    private ArrayList<Putusan> daftar;

    public StatistikPutusan(ArrayList<Putusan> daftar) {
        this.daftar = daftar;
    }

    public void hitungSemua() {
        totalPutusan = daftar.size();
    }

}
