package com.example.tugasbesarpbonarikotikac.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private TableView<Putusan> putusanTable;
    @FXML
    private TableColumn<Putusan, String> colNomor, colPengadilan, colTanggal, colNama, colJenis, colPasal, colPeran, colHakim;
    @FXML
    private TableColumn<Putusan, Integer> colUmur, colVonisBulan;
    @FXML
    private TableColumn<Putusan, Double> colBerat, colVonisDenda;

    @FXML
    private TextField tfNomorPerkara, tfPengadilan, tfTanggal, tfNamaTerdakwa, tfUmur;
    @FXML
    private TextField tfJenisNarkotika, tfBerat, tfPasal, tfPeran, tfVonisBulan, tfVonisDenda, tfHakim;
    @FXML
    private TextField tfCari;

    @FXML
    private ComboBox<String> cbSearchBy, cbFilterJenis;
    @FXML
    private Label lblTotal;

    private final ObservableList<Putusan> dataPutusan = FXCollections.observableArrayList();
    private FilteredList<Putusan> filteredData;

    public void initialize(URL url, ResourceBundle rb) {
        tampilkanMenu();
        tampilkanDaftarPutusan();

        putusanTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) tampilkanDetail(newSel);
        });
    }
    public int tampilkanMenu() {
        cbSearchBy.getItems().addAll("Nomor Perkara", "Nama Terdakwa", "Nama Pengadilan");
        cbSearchBy.getSelectionModel().select(0);
        cbFilterJenis.getItems().addAll("Semua Jenis", "Sabu-sabu", "Ganja", "Ekstasi", "Heroin", "Kokain");
        cbFilterJenis.getSelectionModel().select(0);
        return 0;
    }
    public void tampilkanDaftarPutusan() {
        colNomor.setCellValueFactory(new PropertyValueFactory<>("nomorPerkara"));
        colPengadilan.setCellValueFactory(new PropertyValueFactory<>("pengadilan"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPutusan"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaTerdakwa"));
        colUmur.setCellValueFactory(new PropertyValueFactory<>("umurTerdakwa"));
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenisNarkotika"));
        colBerat.setCellValueFactory(new PropertyValueFactory<>("beratBarangBukti"));
        colPasal.setCellValueFactory(new PropertyValueFactory<>("pasalDilanggar"));
        colPeran.setCellValueFactory(new PropertyValueFactory<>("peranTerdakwa"));
        colVonisBulan.setCellValueFactory(new PropertyValueFactory<>("vonisHukumanBulan"));
        colVonisDenda.setCellValueFactory(new PropertyValueFactory<>("vonisDendaRupiah"));
        colHakim.setCellValueFactory(new PropertyValueFactory<>("namaHakim"));

        filteredData = new FilteredList<>(dataPutusan, p -> true);
        putusanTable.setItems(filteredData);
        tampilkanStatistik();
    }
    public void tampilkanDetail(Putusan p) {
        tfNomorPerkara.setText(p.getNomorPerkara());
        tfPengadilan.setText(p.getPengadilan());
        tfTanggal.setText(p.getTanggalPutusan());
        tfNamaTerdakwa.setText(p.getNamaTerdakwa());
        tfUmur.setText(String.valueOf(p.getUmurTerdakwa()));
        tfJenisNarkotika.setText(p.getJenisNarkotika());
        tfBerat.setText(String.valueOf(p.getBeratBarangBukti()));
        tfPasal.setText(p.getPasalDilanggar());
        tfPeran.setText(p.getPeranTerdakwa());
        tfVonisBulan.setText(String.valueOf(p.getVonisHukumanBulan()));
        tfVonisDenda.setText(String.valueOf(p.getVonisDendaRupiah()));
        tfHakim.setText(p.getNamaHakim());
    }
    public void tampilkanStatistik() {
        int total = filteredData.size();
        double totalVonis = 0, totalDenda = 0;

        for (Putusan p : filteredData) {
            totalVonis += p.getVonisHukumanBulan();
            totalDenda += p.getVonisDendaRupiah();
        }

        String jenisTerbanyak = "=";
        int maxJenis = 0;
        for (Putusan p : filteredData) {
            String j = p.getJenisNarkotika();
            int count = (int) filteredData.stream().filter(x -> x.getJenisNarkotika().equalsIgnoreCase(j)).count();
            if (count > maxJenis) {
                maxJenis = count;
                jenisTerbanyak = j;
            }
        }

        long pengguna = filteredData.stream().filter(p -> p.getPeranTerdakwa().toLowerCase().contains("pengguna")).count();
        long pengedar = filteredData.stream().filter(p -> p.getPeranTerdakwa().toLowerCase().contains("pengedar") || p.getPeranTerdakwa().toLowerCase().contains("jual")).count();
        long kurir = filteredData.stream().filter(p -> p.getPeranTerdakwa().toLowerCase().contains("kurir")).count();
        long perantara = filteredData.stream().filter(p -> p.getPeranTerdakwa().toLowerCase().contains("perantara")).count();

        double rataVonis = total == 0 ? 0 : totalVonis / total;
        double rataDenda = total == 0 ? 0 : totalDenda / total;

        String infoPeran = total == 0 ? "-" : String.format("Pengguna: %d, Pengedar/Penjual: %d, Kurir: %d, Perantara: %d",
                pengguna, pengedar, kurir, perantara);

        lblTotal.setText(String.format(
                "Ringkasan Statistik:\n" +
                        "• Total: %d Kasus  |  Rata Vonis: %.1f Bulan  |  Rata Denda: Rp %,.0f\n" +
                        "• Jenis Narkotika Terbanyak: %s (%d kasus)\n" +
                        "• Distribusi Peran: [%s]",
                total, rataVonis, rataDenda, jenisTerbanyak, maxJenis, infoPeran
        ));
    }
    public void tampilkanPesan(String pesan) {
        Alert.AlertType type = Alert.AlertType.INFORMATION;
        if (pesan.contains("Warning")) type = Alert.AlertType.WARNING;
        if (pesan.contains("Failed")) type = Alert.AlertType.ERROR;

        new Alert(type, pesan).showAndWait();
    }
    public String[] inputFormPutusan() {
        return new String[]{
                tfNomorPerkara.getText().trim(),
                tfPengadilan.getText().trim(),
                tfTanggal.getText().trim(),
                tfNamaTerdakwa.getText().trim(),
                tfUmur.getText().trim(),
                tfJenisNarkotika.getText().trim(),
                tfBerat.getText().trim(),
                tfPasal.getText().trim(),
                tfPeran.getText().trim(),
                tfVonisBulan.getText().trim(),
                tfVonisDenda.getText().trim(),
                tfHakim.getText().trim()
        };
    }
    @FXML
    private void tambahData() {
        if (tfNomorPerkara.getText().trim().isEmpty() || tfNamaTerdakwa.getText().trim().isEmpty()) {
            tampilkanPesan(" Nomor Perkara dan Nama Terdakwa wajib diisi!");
            return;
        }
        try {
            String[] data = inputFormPutusan();
            Putusan baru = new Putusan(
                    data[0], data[1], data[2], data[3],
                    Integer.parseInt(data[4]), data[5], Double.parseDouble(data[6]),
                    data[7], data[8], Integer.parseInt(data[9]), Double.parseDouble(data[10]), data[11]
            );
            dataPutusan.add(baru);
            bersihkanForm();
            tampilkanStatistik();
            tampilkanPesan(" Data berhasil ditambahkan!");
        } catch (Exception e) {
            tampilkanPesan(" Format angka pada kolom Umur/Berat/Vonis keliru!");
        }
    }
    @FXML
    private void hapusData() {
        Putusan selected = putusanTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dataPutusan.remove(selected);
            bersihkanForm();
            tampilkanStatistik();
            tampilkanPesan(" Data berhasil dihapus.");
        } else {
            tampilkanPesan(" Silakan pilih baris data pada tabel dahulu.");
        }
    }
    @FXML
    private void updateData() {
        Putusan selected = putusanTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            tampilkanPesan(" Silakan pilih data pada tabel yang ingin diupdate!");
            return;
        }
        try {
            String[] data = inputFormPutusan();
            selected.setNomorPerkara(data[0]);
            selected.setPengadilan(data[1]);
            selected.setTanggalPutusan(data[2]);
            selected.setNamaTerdakwa(data[3]);
            selected.setUmurTerdakwa(Integer.parseInt(data[4]));
            selected.setJenisNarkotika(data[5]);
            selected.setBeratBarangBukti(Double.parseDouble(data[6]));
            selected.setPasalDilanggar(data[7]);
            selected.setPeranTerdakwa(data[8]);
            selected.setVonisHukumanBulan(Integer.parseInt(data[9]));
            selected.setVonisDendaRupiah(Double.parseDouble(data[10]));
            selected.setNamaHakim(data[11]);

            putusanTable.refresh();
            bersihkanForm();
            tampilkanStatistik();
            tampilkanPesan(" Data berhasil diupdate!");
        } catch (Exception e) {
            tampilkanPesan(" Gagal update. Periksa input tipe numerik kamu.");
        }
    }
    @FXML
    private void bersihkanForm() {
        TextInputControl[] fields = {tfNomorPerkara, tfPengadilan, tfTanggal, tfNamaTerdakwa, tfUmur,
                tfJenisNarkotika, tfBerat, tfPasal, tfPeran, tfVonisBulan, tfVonisDenda, tfHakim};
        for (TextInputControl field : fields) field.clear();
        putusanTable.getSelectionModel().clearSelection();
    }
    private void applyFilter() {
        String keyword = tfCari.getText().trim().toLowerCase();
        String kriteria = cbSearchBy.getValue();
        String selectedJenis = cbFilterJenis.getValue();

        filteredData.setPredicate(p -> {
            if (selectedJenis != null && !"Semua Jenis".equals(selectedJenis)) {
                if (!p.getJenisNarkotika().equalsIgnoreCase(selectedJenis)) {
                    return false;
                }
            }

            if (!keyword.isEmpty() && kriteria != null) {
                String valueToCompare = "";
                if (kriteria.equals("Nomor Perkara")) {
                    valueToCompare = p.getNomorPerkara().toLowerCase();
                } else if (kriteria.equals("Nama Terdakwa")) {
                    valueToCompare = p.getNamaTerdakwa().toLowerCase();
                }

                else if (kriteria.equals("Nama Pengadilan")) {
                    valueToCompare = p.getPengadilan().toLowerCase();
                }

                return valueToCompare.contains(keyword);
            }

            return true;
        });
        tampilkanStatistik();
    }
    @FXML public void cariData(ActionEvent actionEvent) { applyFilter(); }
}



