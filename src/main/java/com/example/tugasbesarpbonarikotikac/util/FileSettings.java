package com.example.tugasbesarpbonarikotikac.util;

import com.example.tugasbesarpbonarikotikac.model.Putusan;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class FileSettings {
    public static ArrayList<Putusan> readDoc (File doc) {
        ArrayList<Putusan> list = new ArrayList<>();

        // Konfigurasi CSVFormat: Menggunakan standar RFC4180 dan otomatis melewati baris header
        CSVFormat format = CSVFormat.RFC4180.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true) // Mengabaikan baris kosong di akhir file
                .setTrim(true)// Otomatis menghapus spasi di awal/akhir teks kolom
                .build();

        try (Reader reader = new FileReader(doc);
             CSVParser csvParser = new CSVParser(reader, format)) {

            for (CSVRecord record : csvParser) {
                // Mengambil data berdasarkan urutan indeks kolom (mulai dari 0)
                String nomorPerkara = record.get(0);
                String pengadilan = record.get(1);
                String tanggalPutusan = record.get(2);
                String namaTerdakwa = record.get(3);

                // Parsing nilai integer dengan aman
                int umurTerdakwa = Integer.parseInt(record.get(4));

                String jenisNarkotika = record.get(5);

                // Mengatasi koma desimal Indonesia ("0,761") agar bisa di-parse oleh Double Java (".")
                String beratStr = record.get(6).replace(",", ".");
                double beratBarangBukti = Double.parseDouble(beratStr);

                String pasalDilanggar = record.get(7);
                String peranTerdakwa = record.get(8);
                int vonisHukuman = Integer.parseInt(record.get(9));

                // Mengatasi tanda titik pemisah ribuan pada denda (contoh: 1.000.000.000 -> 1000000000)
                String dendaStr = record.get(10).replace(".", "");
                double vonisDenda = Double.parseDouble(dendaStr);

                String namaHakim = record.get(11);

                // Membuat objek Putusan baru
                Putusan p = new Putusan(
                        nomorPerkara,
                        pengadilan,
                        tanggalPutusan,
                        namaTerdakwa,
                        umurTerdakwa,
                        jenisNarkotika,
                        beratBarangBukti,
                        pasalDilanggar,
                        peranTerdakwa,
                        vonisHukuman,
                        vonisDenda,
                        namaHakim
                );

                list.add(p);
            }

        } catch (IOException e) {
            System.err.println("Tidak bisa membuka file CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Tidak bisa melakukan parsing angka: " + e.getMessage());
        }

        return list;
    }
}
