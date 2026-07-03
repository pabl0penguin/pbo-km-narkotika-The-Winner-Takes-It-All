package com.example.tugasbesarpbonarikotikac.model;

public class Putusan {
    private String nomorPerkara;
    private String pengadilan;
    private String tanggalPutusan;
    private String namaTerdakwa;
    private int umurTerdakwa;
    private String jenisNarkotika;
    private double beratBarangBukti;
    private String pasalDilanggar;
    private String peranTerdakwa;
    private int vonisHukuman;
    private double vonisDenda;
    private String namaHakim;

    private static int jumlahDibuat = 0;

    public Putusan() {
        jumlahDibuat++;
    }
    public Putusan(String nomorPerkara, String pengadilan,
                   String tanggalPutusan, String namaTerdakwa,
                   int umurTerdakwa, String jenisNarkotika,
                   double beratBarangBukti, String pasalDilanggar,
                   String peranTerdakwa, int vonisHukuman,
                   double vonisDenda, String namaHakim) {

        this.nomorPerkara = nomorPerkara;
        this.pengadilan = pengadilan;
        this.tanggalPutusan = tanggalPutusan;
        this.namaTerdakwa = namaTerdakwa;
        this.umurTerdakwa = umurTerdakwa;
        this.jenisNarkotika = jenisNarkotika;
        this.beratBarangBukti = beratBarangBukti;
        this.pasalDilanggar = pasalDilanggar;
        this.peranTerdakwa = peranTerdakwa;
        this.vonisHukuman = vonisHukuman;
        this.vonisDenda = vonisDenda;
        this.namaHakim = namaHakim;

        jumlahDibuat++;
    }

}
