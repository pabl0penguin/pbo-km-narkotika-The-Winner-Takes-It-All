package com.example.tugasbesarpbonarikotikac.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class Controller implements Initializable {


    @FXML private TableView<Putusan> putusanTable;
    @FXML private TableColumn<Putusan, String> colNomor, colPengadilan, colTanggal, colNama, colJenis, colPasal, colPeran, colHakim;
    @FXML private TableColumn<Putusan, Integer> colUmur, colVonisBulan;
    @FXML private TableColumn<Putusan, Double> colBerat, colVonisDenda;

    @FXML private TextField tfNomorPerkara, tfPengadilan, tfTanggal, tfNamaTerdakwa, tfUmur;
    @FXML private TextField tfJenisNarkotika, tfBerat, tfPasal, tfPeran, tfVonisBulan, tfVonisDenda, tfHakim;
    @FXML private TextField tfCari;

    @FXML private ComboBox<String> cbSearchBy, cbFilterJenis;
    @FXML private Label lblTotal;

    private final ObservableList<Putusan> dataPutusan = FXCollections.observableArrayList();
    private FilteredList<Putusan> filteredData;


