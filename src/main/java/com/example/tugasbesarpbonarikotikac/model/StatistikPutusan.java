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

        double totalVonis = 0;

        for(Putusan p : daftar){
            totalVonis += p.getVonisHukuman();
        }

        if(totalPutusan > 0){
            rataRataVonis = totalVonis / totalPutusan;
        }
    }

    double totalDenda = 0;

for(Putusan p : daftar){
        totalDenda += p.getVonisDenda();
    }

if(totalPutusan > 0){
        rataRataDenda = totalDenda / totalPutusan;
    }

}
