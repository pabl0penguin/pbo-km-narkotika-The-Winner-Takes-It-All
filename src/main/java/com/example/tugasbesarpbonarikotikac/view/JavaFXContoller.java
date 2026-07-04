package com.example.tugasbesarpbonarikotikac.view;

import com.example.tugasbesarpbonarikotikac.controller.KnowledgeController;
import com.example.tugasbesarpbonarikotikac.model.Putusan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller {


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
    private KnowledgeController Controller;

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
        try{
            Controller.tambahPutusan(
                tfNomorPerkara.getText(),
                tfPengadilan.getText(),
                tfTanggal.getText(),
                        tfNamaTerdakwa.getText(),
                        tfUmur.getText(),
                        tfJenisNarkotika.getText(),
                        tfBerat.getText(),
                        tfPasal.getText(),
                        tfPeran.getText(),
                        tfVonisBulan.getText(),
                        tfVonisDenda.getText(),
                        tfHakim.getText()
                        );
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
          Controller.hapusPutusan(selected);
            bersihkanForm();
            tampilkanStatistik();
            tampilkanPesan(" Data berhasil dihapus.");
        } else {
            tampilkanPesan(" Silakan pilih baris data pada tabel dahulu.");
        }
    }
    @FXML
    private void perbaharuiData() {
        Putusan selected = putusanTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            tampilkanPesan(" Silakan pilih data pada tabel yang ingin diupdate!");
            return;
        }
        try {
            Controller.updatePutusan(selected,
                    tfNomorPerkara.getText(),
                    tfPengadilan.getText(),
                    tfTanggal.getText(),
                    tfNamaTerdakwa.getText(),
                    tfUmur.getText(),
                    tfJenisNarkotika.getText(),
                    tfBerat.getText(),
                    tfPasal.getText(),
                    tfPeran.getText(),
                    tfVonisBulan.getText(),
                    tfVonisDenda.getText(),
                    tfHakim.getText()
            );

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
                tfNomorPerkara.clear();
                tfPengadilan.clear();
                tfTanggal.clear();
                tfNamaTerdakwa.clear();
                tfUmur.clear();
                tfJenisNarkotika.clear();
                tfBerat.clear();
                tfPasal.clear();
                tfPeran.clear();
                tfVonisBulan.clear();
                tfVonisDenda.clear();
                tfHakim.clear();
        ;
    }

    @FXML
    private void UploadFile() {
        FileChooser chooser = new FileChooser();
        JFXPanel btnUploadFile = null;
        Stage stage = (Stage) btnUploadFile.getScene().getWindow();

        File file = chooser.showOpenDialog(stage);

        Controller.importData(file);

        putusanTable.refresh(Controller.tampilkanSemua());
    }

    private void perbaharuiTabel(ArrayList<Putusan> daftar) {
        dataPutusan.setAll();
        putusanTable.refresh();
    }
    @FXML
    public void cariData(ActionEvent actionEvent) {
        String kataKunci = tfCari.getText();
        String mode = tfSearchMode.getValue();
        ArrayList<Putusan> hasil = Controller.cariPutusan(kataKunci, mode);
        perbaharuiTabel(hasil);
        tampilkanPesan("Data telah ditemukan");
    }
    @FXML public void filterData() {  }

    @FXML
    public void tampilkanSemua() {
        tfCari.clear();
        cbSearchBy.getSelectionModel().select(0);
        cbFilterJenis.getSelectionModel().select(0);

    }
}



