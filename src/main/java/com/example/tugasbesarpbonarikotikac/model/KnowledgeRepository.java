package com.example.tugasbesarpbonarikotikac.model;

import java.util.ArrayList;

public class KnowledgeRepository {

    private ArrayList<Putusan> daftarPutusan;

    public KnowledgeRepository() {
        daftarPutusan = new ArrayList<>();
    }

    public void simpan(Putusan p) {
        daftarPutusan.add(p);
    }

    public Putusan cariByNomor(String nomor) {
        for (Putusan p : daftarPutusan) {
            if (p.getNomorPerkara().equalsIgnoreCase(nomor)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Putusan> cariByNama(String nama) {

        ArrayList<Putusan> hasil = new ArrayList<>();

        for (Putusan p : daftarPutusan) {

            if (p.getNamaTerdakwa()
                    .toLowerCase()
                    .contains(nama.toLowerCase())) {

                hasil.add(p);
            }
        }

        return hasil;
    }

}
