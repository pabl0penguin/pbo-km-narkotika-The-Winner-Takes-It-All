package com.example.tugasbesarpbonarikotikac.model;

import java.util.ArrayList;
import java.util.HashMap;

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

        for (Putusan p : daftar) {
            totalVonis += p.getVonisHukuman();
        }

        if (totalPutusan > 0) {
            rataRataVonis = totalVonis / totalPutusan;
        }


        double totalDenda = 0;

        for (Putusan p : daftar) {
            totalDenda += p.getVonisDenda();
        }

        if (totalPutusan > 0) {
            rataRataDenda = totalDenda / totalPutusan;
        }

        HashMap<String, Integer> peranMap = new HashMap<>();

        for(Putusan p : daftar){

            peranMap.put(
                    p.getPeranTerdakwa(),
                    peranMap.getOrDefault(
                            p.getPeranTerdakwa(), 0
                    ) + 1
            );
        }

        distribusiPeran = new String[peranMap.size()];

        int i = 0;

        for(String peran : peranMap.keySet()){

            distribusiPeran[i] =
                    peran + " : " + peranMap.get(peran);

            i++;
        }
    }

}
