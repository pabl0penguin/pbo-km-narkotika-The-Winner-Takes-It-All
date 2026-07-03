package com.example.tugasbesarpbonarikotikac.controller;
import com.example.demopbonar.model.KnowledgeRepository;
import com.example.demopbonar.model.Putusan;
import com.example.demopbonar.model.StatistikPutusan;

import java.util.ArrayList;
public class KnowledgeController {

    private final KnowledgeRepository repository;

    public KnowledgeController(KnowledgeRepository repository) {
        this.repository = repository;
    }

    public String tambahPutusan(String[] data) {
        if (data == null || data.length < 12) {
            return "ERROR: Data tidak lengkap (diharapkan 12 field).";
        }
        try {
            String nomorPerkara = validasiStringField(data[0], "Nomor Perkara");
            String pengadilan = validasiStringField(data[1], "Pengadilan");
            String tanggal = validasiStringField(data[2], "Tanggal Putusan");
            String namaTerdakwa = validasiStringField(data[3], "Nama Terdakwa");
            int umur = validasiIntField(data[4], "Umur Terdakwa", 10, 100);
            String jenis = validasiStringField(data[5], "Jenis Narkotika");
            double berat = validasiDoublePositif(data[6], "Berat Barang Bukti");
            String pasal = validasiStringField(data[7], "Pasal Dilanggar");
            String peran = validasiStringField(data[8], "Peran Terdakwa");
            int vonis = validasiIntField(data[9], "Vonis Hukuman", 1, 600);
            double denda = validasiDoubleNonNegatif(data[10], "Vonis Denda");
            String hakim = validasiStringField(data[11], "Nama Hakim");

            if (repository.cariByNomor(nomorPerkara) != null) {
                return "ERROR: Nomor perkara '" + nomorPerkara + "' sudah ada dalam sistem.";
            }

            Putusan p = new Putusan(nomorPerkara, pengadilan, tanggal, namaTerdakwa,
                    umur, jenis, berat, pasal, peran, vonis, denda, hakim);
            repository.simpan(p);
            return "OK: Putusan berhasil ditambahkan. Total data: " + repository.getTotalData();

        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    public ArrayList<Putusan> tampilkanSemua() {
        return repository.getDaftarSemua();
    }

    public ArrayList<Putusan> cariPutusan(String keyword, String mode) {
        if (keyword == null || keyword.isBlank()) return new ArrayList<>();

        ArrayList<Putusan> hasil = new ArrayList<>();
        switch (mode.toLowerCase()) {
            case "nomor":
                Putusan p = repository.cariByNomor(keyword);
                if (p != null) hasil.add(p);
                break;
            case "nama":
                hasil = repository.cariByNama(keyword);
                break;
            default:
        }
        return hasil;
    }
}