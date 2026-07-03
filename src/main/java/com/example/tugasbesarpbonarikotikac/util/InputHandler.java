package com.example.tugasbesarpbonarikotikac.util;
import java.util.Scanner;

public class InputHandler {
    private static final int UMUR_MIN = 10;
    private static final int UMUR_MAX = 100;
    private static final double BERAT_MIN = 0.001;   // gram
    private static final int VONIS_MIN_BULAN = 1;
    private static final int VONIS_MAX_BULAN = 600;     // 50 tahun
    private static final double DENDA_MIN = 0.0;
    private static final int MAX_RETRY = 5;       // batas percobaan input

    private InputHandler() {

    }

    public static int validasiInt(String prompt, Scanner sc) {
        int percobaan = 0;
        while (percobaan < MAX_RETRY) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Input tidak boleh kosong.");
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                percobaan++;
                System.out.printf("  [ERROR] '%s' bukan angka bulat. "
                        + "Sisa percobaan: %d%n", input, MAX_RETRY - percobaan);
            } catch (IllegalArgumentException e) {
                percobaan++;
                System.out.printf("  [ERROR] %s Sisa percobaan: %d%n",
                        e.getMessage(), MAX_RETRY - percobaan);
            }
        }
        throw new IllegalStateException(
                "Melebihi batas percobaan input (" + MAX_RETRY + "x). Proses dibatalkan.");
    }

    public static int validasiIntRentang(String prompt, int min, int max, Scanner sc) {
        int percobaan = 0;
        while (percobaan < MAX_RETRY) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                if (input.isEmpty()) throw new IllegalArgumentException("Input tidak boleh kosong.");
                int nilai = Integer.parseInt(input);
                if (nilai < min || nilai > max) {
                    throw new IllegalArgumentException(
                            String.format("Nilai harus antara %d dan %d.", min, max));
                }
                return nilai;
            } catch (NumberFormatException e) {
                percobaan++;
                System.out.printf("  [ERROR] '%s' bukan angka. Sisa percobaan: %d%n",
                        input, MAX_RETRY - percobaan);
            } catch (IllegalArgumentException e) {
                percobaan++;
                System.out.printf("  [ERROR] %s Sisa percobaan: %d%n",
                        e.getMessage(), MAX_RETRY - percobaan);
            }
        }
        throw new IllegalStateException("Melebihi batas percobaan input. Proses dibatalkan.");
    }
}