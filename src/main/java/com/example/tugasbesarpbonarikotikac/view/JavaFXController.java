package com.example.tugasbesarpbonarikotikac.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
}



