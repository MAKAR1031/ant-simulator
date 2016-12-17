package ru.makar.cource.project.ui.controllers;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import ru.makar.cource.project.ExperimentLauncher;
import ru.makar.cource.project.gp.data.FieldData;
import ru.makar.cource.project.ui.FoodCord;
import ru.makar.cource.project.util.FieldDataCompiler;
import ru.makar.cource.project.util.FieldDataStore;
import ru.makar.cource.project.util.FileDataReader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextField widthField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField foodField;

    @FXML
    private TextField antField;

    @FXML
    private TableView<FoodCord> foodCoorsTable;

    @FXML
    private Button saveButton;

    @FXML
    private Button launchButton;

    private ObservableList<FoodCord> foodCords;
    private FieldDataCompiler dataCompiler;

    public MainController() {
        dataCompiler = new FieldDataCompiler();
        foodCords = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanBinding fieldsFillProperty = widthField.textProperty().isEmpty()
                .or(heightField.textProperty().isEmpty())
                .or(foodField.textProperty().isEmpty())
                .or(antField.textProperty().isEmpty());

        foodCoorsTable.disableProperty().bind(fieldsFillProperty);
        widthField.focusTraversableProperty().bind(fieldsFillProperty);
        heightField.focusTraversableProperty().bind(fieldsFillProperty);
        foodField.focusTraversableProperty().bind(fieldsFillProperty);
        antField.focusTraversableProperty().bind(fieldsFillProperty);
        saveButton.disableProperty().bind(fieldsFillProperty);

        TableColumn<FoodCord, Integer> xColumn = createColumn("x");
        TableColumn<FoodCord, Integer> yColumn = createColumn("y");
        foodCoorsTable.getColumns().add(xColumn);
        foodCoorsTable.getColumns().add(yColumn);
        foodCoorsTable.setItems(foodCords);

        foodCoorsTable.disableProperty().addListener(e -> {
            BooleanProperty property = (BooleanProperty) e;
            if (!property.get()) {
                try {
                    int foodCount = Integer.parseInt(foodField.getText());
                    int itemsSize = foodCords.size();
                    if (foodCount > itemsSize) {
                        for (int i = 0; i < foodCount - itemsSize; i++) {
                            foodCords.add(new FoodCord(0, 0));
                        }
                    } else if (foodCount < itemsSize) {
                        for (int i = 0; i < itemsSize - foodCount; i++) {
                            foodCords.remove(0);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void readFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                FileDataReader reader = new FileDataReader();
                FieldData fieldData = reader.readData(file);
                System.out.println(fieldData.toString());
                FieldDataStore.getCurrentInstance().setData(fieldData);
                launchButton.setDisable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void saveData() {
        try {
            if (validateData()) {
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                int antCount = Integer.parseInt(antField.getText());
                FieldData fieldData = dataCompiler.compile(width, height, antCount, foodCords);
                FieldDataStore.getCurrentInstance().setData(fieldData);
                launchButton.setDisable(false);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void launch() {
        ExperimentLauncher.launch();
    }

    private TableColumn<FoodCord, Integer> createColumn(String property) {
        TableColumn<FoodCord, Integer> column = new TableColumn<>(property);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit((CellEditEvent<FoodCord, Integer> t) ->
                t.getTableView()
                        .getItems()
                        .get(t.getTablePosition().getRow())
                        .set(t.getNewValue(), property));
        return column;
    }

    //TODO: реализовать валидацию данных
    private boolean validateData() {
        boolean isValid = true;
        return isValid;
    }
}
