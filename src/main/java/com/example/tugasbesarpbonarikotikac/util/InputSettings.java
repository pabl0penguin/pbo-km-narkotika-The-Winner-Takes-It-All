package com.example.tugasbesarpbonarikotikac.util;
import java.util.Scanner;

public class InputSettings {

    public static String validasiKolomString(String nilai, String namaKolom) {
        if (nilai == null || nilai.isBlank()) {
            throw new IllegalArgumentException(namaKolom + " wajib diisi!");
        }
        return nilai.trim();
    }

    public static int validasiKolomInt(String nilai, String namaKolom) {
        try {
            return Integer.parseInt(nilai.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(namaKolom + " harus berupa angka bulat yang valid!");
        }
    }

    public static double validasiKolomDouble(String nilai, String namaKolom) {
        try {
            return Double.parseDouble(nilai.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(namaKolom + " harus berupa angka desimal yang valid!");
        }
    }

    public static float validasiKolomFloat(String nilai, String namaKolom) {
        try {
            return Float.parseFloat(nilai.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(namaKolom + " harus berupa angka desimal yang valid!");
        }
    }
}