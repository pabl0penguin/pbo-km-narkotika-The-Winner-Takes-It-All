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

    public static double validasiDouble(String prompt, Scanner sc) {
        int percobaan = 0;
        while (percobaan < MAX_RETRY) {
            System.out.print(prompt);
            String input = sc.nextLine().trim().replace(",", ".");
            try {
                if (input.isEmpty()) throw new IllegalArgumentException("Input tidak boleh kosong.");
                double nilai = Double.parseDouble(input);
                if (nilai < 0) throw new IllegalArgumentException("Nilai tidak boleh negatif.");
                return nilai;
            } catch (NumberFormatException e) {
                percobaan++;
                System.out.printf("  [ERROR] '%s' bukan angka desimal. "
                        + "Sisa percobaan: %d%n", input, MAX_RETRY - percobaan);
            } catch (IllegalArgumentException e) {
                percobaan++;
                System.out.printf("  [ERROR] %s Sisa percobaan: %d%n",
                        e.getMessage(), MAX_RETRY - percobaan);
            }
        }
        throw new IllegalStateException("Melebihi batas percobaan input. Proses dibatalkan.");
    }

    public static String validasiString(String prompt, Scanner sc) {
        int percobaan = 0;
        while (percobaan < MAX_RETRY) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            percobaan++;
            System.out.printf("  [ERROR] Input tidak boleh kosong. "
                    + "Sisa percobaan: %d%n", MAX_RETRY - percobaan);
        }
        throw new IllegalStateException("Melebihi batas percobaan input. Proses dibatalkan.");
    }

    public static int validasiPilihan(String prompt, int min, int max, Scanner sc) {
        int percobaan = 0;
        while (percobaan < MAX_RETRY) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                if (input.isEmpty()) throw new IllegalArgumentException("Pilihan tidak boleh kosong.");
                int pilihan = Integer.parseInt(input);
                if (pilihan < min || pilihan > max) {
                    throw new IllegalArgumentException(
                            String.format("Pilihan harus antara %d dan %d.", min, max));
                }
                return pilihan;
            } catch (NumberFormatException e) {
                percobaan++;
                System.out.printf("  [ERROR] '%s' bukan pilihan yang valid. "
                        + "Sisa percobaan: %d%n", input, MAX_RETRY - percobaan);
            } catch (IllegalArgumentException e) {
                percobaan++;
                System.out.printf("  [ERROR] %s Sisa percobaan: %d%n",
                        e.getMessage(), MAX_RETRY - percobaan);
            }
        }
        throw new IllegalStateException("Melebihi batas percobaan pilihan. Proses dibatalkan.");
    }

    public static int validasiUmur(String prompt, Scanner sc) {
        return validasiIntRentang(
                prompt, UMUR_MIN, UMUR_MAX, sc
        );
    }

    public static double validasiBeratBukti(String prompt, Scanner sc) {
        int percobaan = 0;
        while (percobaan < MAX_RETRY) {
            System.out.print(prompt);
            String input = sc.nextLine().trim().replace(",", ".");
            try {
                if (input.isEmpty()) throw new IllegalArgumentException("Berat tidak boleh kosong.");
                double berat = Double.parseDouble(input);
                if (berat < BERAT_MIN) {
                    throw new IllegalArgumentException(
                            String.format("Berat harus lebih dari %.3f gram.", BERAT_MIN));
                }
                return berat;
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
        throw new IllegalStateException("Melebihi batas percobaan. Proses dibatalkan.");
    }

    public static int validasiVonisBulan(String prompt, Scanner sc) {
        return validasiIntRentang(
                prompt, VONIS_MIN_BULAN, VONIS_MAX_BULAN, sc
        );
    }

    public static String validasiTanggal(String prompt, Scanner sc) {
        int percobaan = 0;
        while (percobaan < MAX_RETRY) {
            System.out.print(prompt + " [dd-MM-yyyy]: ");
            String input = sc.nextLine().trim();
            try {
                if (input.isEmpty()) throw new IllegalArgumentException("Tanggal tidak boleh kosong.");
                // Normalisasi separator
                String normalized = input.replace("/", "-");
                String[] parts = normalized.split("-");
                if (parts.length != 3) throw new IllegalArgumentException("Format harus dd-MM-yyyy.");

                int hari  = Integer.parseInt(parts[0]);
                int bulan = Integer.parseInt(parts[1]);
                int tahun = Integer.parseInt(parts[2]);

                if (hari < 1 || hari > 31)     throw new IllegalArgumentException("Hari tidak valid (1-31).");
                if (bulan < 1 || bulan > 12)   throw new IllegalArgumentException("Bulan tidak valid (1-12).");
                if (tahun < 2000 || tahun > 2100) throw new IllegalArgumentException("Tahun tidak valid.");

                return String.format("%02d-%02d-%04d", hari, bulan, tahun);
            } catch (NumberFormatException e) {
                percobaan++;
                System.out.printf("  [ERROR] Format tanggal tidak valid. "
                        + "Sisa percobaan: %d%n", MAX_RETRY - percobaan);
            } catch (IllegalArgumentException e) {
                percobaan++;
                System.out.printf("  [ERROR] %s Sisa percobaan: %d%n",
                        e.getMessage(), MAX_RETRY - percobaan);
            }
        }
        throw new IllegalStateException("Melebihi batas percobaan. Proses dibatalkan.");
    }

    public static String validasiStringGui(String nilai, String namaField) {
        if (nilai == null || nilai.isBlank()) {
            throw new IllegalArgumentException(namaField + " tidak boleh kosong.");
        }
        return nilai.trim();
    }

    public static int validasiIntGui(String nilai, String namaField, int min, int max) {
        if (nilai == null || nilai.isBlank()) {
            throw new IllegalArgumentException(namaField + " tidak boleh kosong.");
        }
        try {
            int angka = Integer.parseInt(nilai.trim());
            if (angka < min || angka > max) {
                throw new IllegalArgumentException(
                        namaField + " harus antara " + min + " dan " + max + ".");
            }
            return angka;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    namaField + " harus berupa angka bulat, bukan '" + nilai.trim() + "'.");
        }
    }
}