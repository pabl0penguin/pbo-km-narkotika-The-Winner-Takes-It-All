package com.example.tugasbesarpbonarikotikac.controller;

import com.example.tugasbesarpbonarikotikac.model.KnowledgeRepository;
import com.example.tugasbesarpbonarikotikac.model.Putusan;
import com.example.tugasbesarpbonarikotikac.model.StatistikPutusan;
import com.example.tugasbesarpbonarikotikac.util.InputSettings;

import java.io.File;
import java.util.ArrayList;

public class KnowledgeController {

    private final KnowledgeRepository repository;
    private final Putusan putusan;

    public KnowledgeController(KnowledgeRepository repository, Putusan putusan) {
        this.repository = repository;
        this.putusan = putusan;
    }

    public ArrayList<Putusan> tampilkanSemua() {
        return repository.getDaftarSemua();
    }

    public String tambahPutusan(
            String nomorPerkaraTxt,
            String pengadilanTxt,
            String tanggalTxt,
            String namaTerdakwaTxt,
            String umurTxt,
            String jenisNarkotikaTxt,
            String beratTxt,
            String pasalTxt,
            String peranTxt,
            String vonisHukumanTxt,
            String vonisDendaTxt,
            String hakimTxt
    ) {

        String nomorPerkara = InputSettings.validasiKolomString(nomorPerkaraTxt, "Nomor Perkara");
        String pengadilan = InputSettings.validasiKolomString(pengadilanTxt, "Pengadilan");
        String tanggal = InputSettings.validasiKolomString(tanggalTxt, "Tanggal");
        String namaTerdakwa = InputSettings.validasiKolomString(namaTerdakwaTxt, "Nama Terdakwa");
        String jenisNarkotika = InputSettings.validasiKolomString(jenisNarkotikaTxt, "Jenis Narkotika");
        String pasal = InputSettings.validasiKolomString(pasalTxt, "Pasal");
        String peran = InputSettings.validasiKolomString(peranTxt, "Peran");
        String hakim = InputSettings.validasiKolomString(hakimTxt, "Hakim");

        if (repository.cariByNomor(nomorPerkara) != null) {
            throw new IllegalArgumentException(
                    "Nomor perkara \"" + nomorPerkara + "\" sudah terdaftar."
            );
        }
        int umur = InputSettings.validasiKolomInt(umurTxt, "Umur Terdakwa");
        double berat = InputSettings.validasiKolomDouble(beratTxt, "Berat Barang Bukti");
        int vonisHukuman = InputSettings.validasiKolomInt(vonisHukumanTxt, "Vonis Hukuman");
        float vonisDenda = InputSettings.validasiKolomFloat(vonisDendaTxt, "Vonis Denda");

        Putusan pts = new Putusan(
                nomorPerkara,
                pengadilan,
                tanggal,
                namaTerdakwa,
                umur,
                jenisNarkotika,
                berat,
                pasal,
                peran,
                vonisHukuman,
                vonisDenda,
                hakim
        );

        repository.simpan(pts);

        return "Data berhasil ditambahkan.";
    }

    public boolean updatePutusan(
            String nomor,
            String nomorPerkaraTxt,
            String pengadilanTxt,
            String tanggalTxt,
            String namaTerdakwaTxt,
            String umurTxt,
            String jenisNarkotikaTxt,
            String beratTxt,
            String pasalTxt,
            String peranTxt,
            String vonisHukumanTxt,
            String vonisDendaTxt,
            String hakimTxt
    ) {

        String nomorPerkara = InputSettings.validasiKolomString(nomorPerkaraTxt, "Nomor Perkara");
        String pengadilan = InputSettings.validasiKolomString(pengadilanTxt, "Pengadilan");
        String tanggal = InputSettings.validasiKolomString(tanggalTxt, "Tanggal");
        String namaTerdakwa = InputSettings.validasiKolomString(namaTerdakwaTxt, "Nama Terdakwa");
        String jenisNarkotika = InputSettings.validasiKolomString(jenisNarkotikaTxt, "Jenis Narkotika");
        String pasal = InputSettings.validasiKolomString(pasalTxt, "Pasal");
        String peran = InputSettings.validasiKolomString(peranTxt, "Peran");
        String hakim = InputSettings.validasiKolomString(hakimTxt, "Hakim");

        int umur = InputSettings.validasiKolomInt(umurTxt, "Umur Terdakwa");
        double berat = InputSettings.validasiKolomDouble(beratTxt, "Berat Barang Bukti");
        int vonisHukuman = InputSettings.validasiKolomInt(vonisHukumanTxt, "Vonis Hukuman");
        float vonisDenda = InputSettings.validasiKolomFloat(vonisDendaTxt, "Vonis Denda");

        Putusan dataBaru = new Putusan(
                nomorPerkara,
                pengadilan,
                tanggal,
                namaTerdakwa,
                umur,
                jenisNarkotika,
                berat,
                pasal,
                peran,
                vonisHukuman,
                vonisDenda,
                hakim
        );

        repository.update(nomor, dataBaru);
        return true;
    }

    public boolean hapusPutusan(String nomor) {
        return repository.hapus(nomor);
    }

    public ArrayList<Putusan> cariPutusan(String keyword, String mode) {

        if (keyword == null || keyword.isBlank()) {
            return new ArrayList<>();
        }

        ArrayList<Putusan> hasil = new ArrayList<>();

        switch (mode.toLowerCase()) {
            case "nomor":
                Putusan p = repository.cariByNomor(keyword);
                if (p != null) {
                    hasil.add(p);
                }
                break;
            case "nama":
                hasil = repository.cariByNama(keyword);
                break;
            default:
                break;
        }
        return hasil;
    }

    public ArrayList<Putusan> filterPutusan(String kriteria, String nilai) {
        if (nilai == null || nilai.isBlank()) {
            return new ArrayList<>();
        }
        
        switch (kriteria.toLowerCase()) {
            case "jenis":
                return repository.filterByJenis(nilai);
            case "pengadilan":
                return repository.filterByPengadilan(nilai);
            default:
                return new ArrayList<>();
        }
    }

    public ArrayList<Putusan> filterByRentangVonis(int minBulan, int maxBulan) {
        return repository.filterByRentangVonis(minBulan, maxBulan);
    }

    public StatistikPutusan getStatistik() {
        return new StatistikPutusan(repository.getDaftarSemua());
    }

    public void importData(File file) {
        try {
            ArrayList<Putusan> data = txtFileHandler.bacaFile(file);
            for (Putusan p : data) {
                repository.simpan(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Putusan> filterByRentangVonis(String vonisMinStr, String vonisMaxStr) {
        int vonisMin = InputSettings.validasiKolomInt(vonisMinStr, "Vonis Minimal");
        int vonisMax = InputSettings.validasiKolomInt(vonisMaxStr, "Vonis Maksimal");
        if (vonisMin > vonisMax) {
            throw new IllegalArgumentException(
                    "Vonis Minimal tidak boleh lebih besar dari Vonis Maksimal."
            );
        }

        return repository.filterByVonisRange(vonisMin, vonisMax);
    }
}