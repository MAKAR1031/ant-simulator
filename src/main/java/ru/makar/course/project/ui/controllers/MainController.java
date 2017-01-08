package ru.makar.course.project.ui.controllers;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import ru.makar.course.project.ExperimentLauncher;
import ru.makar.course.project.gp.data.FieldData;
import ru.makar.course.project.ui.FoodCord;
import ru.makar.course.project.util.FieldDataCompiler;
import ru.makar.course.project.util.FieldDataStore;

import java.io.File;
import java.net.URL;
import java.util.*;

import static javafx.animation.Animation.Status.RUNNING;

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
    private TextField omega1Field;

    @FXML
    private TextField omega2Field;

    @FXML
    private TableView<FoodCord> foodCoorsTable;

    @FXML
    private Button randomButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button launchButton;

    private ObservableList<FoodCord> foodCords;
    private FieldDataCompiler dataCompiler;

    private Animation invalidInputAnimation;

    public MainController() {
        dataCompiler = new FieldDataCompiler();
        foodCords = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalidInputAnimation = createInvalidInputAnimation();
        ChangeListener<String> numericFieldsChangeListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                StringProperty textProperty = (StringProperty) observable;
                textProperty.setValue(oldValue);
            }
        };
        widthField.textProperty().addListener(numericFieldsChangeListener);
        heightField.textProperty().addListener(numericFieldsChangeListener);
        foodField.textProperty().addListener(numericFieldsChangeListener);
        antField.textProperty().addListener(numericFieldsChangeListener);
        omega1Field.textProperty().addListener(numericFieldsChangeListener);
        omega2Field.textProperty().addListener(numericFieldsChangeListener);

        BooleanBinding fieldsFillProperty = widthField.textProperty().isEmpty()
                .or(heightField.textProperty().isEmpty())
                .or(foodField.textProperty().isEmpty())
                .or(antField.textProperty().isEmpty())
                .or(omega1Field.textProperty().isEmpty())
                .or(omega2Field.textProperty().isEmpty());

        foodCoorsTable.disableProperty().bind(fieldsFillProperty);
        widthField.focusTraversableProperty().bind(fieldsFillProperty);
        heightField.focusTraversableProperty().bind(fieldsFillProperty);
        foodField.focusTraversableProperty().bind(fieldsFillProperty);
        antField.focusTraversableProperty().bind(fieldsFillProperty);
        randomButton.disableProperty().bind(fieldsFillProperty);
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
    private void generateRandom() {
        int width = Integer.parseInt(widthField.getText());
        int height = Integer.parseInt(heightField.getText());
        int foodCount = Integer.parseInt(foodField.getText());
        List<FoodCord> foodCords = new LinkedList<>();
        FoodCord foodCord;
        Random random = new Random();
        for (int i = 0; i < foodCount; i++) {
            do {
                foodCord = new FoodCord(random.nextInt(width), random.nextInt(height));
            } while (foodCords.contains(foodCord));
            foodCords.add(foodCord);
        }
        this.foodCords.clear();
        this.foodCords.addAll(foodCords);
    }

    @FXML
    private void fillFromFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (Scanner scanner = new Scanner(file)) {
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                int foodCount = scanner.nextInt();
                int antCount = scanner.nextInt();
                List<FoodCord> foodCords = new LinkedList<>();
                for (int i = 0; i < foodCount; i++) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    FoodCord data = new FoodCord(x, y);
                    foodCords.add(data);
                }

                widthField.setText(String.valueOf(width));
                heightField.setText(String.valueOf(height));
                foodField.setText(String.valueOf(foodCount));
                antField.setText(String.valueOf(antCount));
                this.foodCords.clear();
                this.foodCords.addAll(foodCords);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void saveData() {
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int antCount = Integer.parseInt(antField.getText());
            if (validateData(width, height, antCount, foodCords)) {
                FieldData fieldData = dataCompiler.compile(width, height, antCount, foodCords);
                FieldDataStore dataStore = FieldDataStore.getInstance();
                dataStore.setData(fieldData);
                dataStore.setOmega1(Integer.parseInt(omega1Field.getText()));
                dataStore.setOmega2(Integer.parseInt(omega2Field.getText()));
                launchButton.setDisable(false);
            } else {
                if (!RUNNING.equals(invalidInputAnimation.getStatus())) {
                    invalidInputAnimation.play();
                }
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
        column.setOnEditCommit(
                (CellEditEvent<FoodCord, Integer> t) ->
                        t.getTableView()
                                .getItems()
                                .get(t.getTablePosition().getRow())
                                .set(t.getNewValue(), property)
        );
        return column;
    }

    private Animation createInvalidInputAnimation() {
        RotateTransition rt1 = new RotateTransition(Duration.millis(50), saveButton);
        int byAngle = 15;
        rt1.setByAngle(byAngle);
        rt1.setCycleCount(2);
        rt1.setAutoReverse(true);
        RotateTransition rt2 = new RotateTransition(Duration.millis(50), saveButton);
        rt2.setByAngle(-byAngle);
        rt2.setCycleCount(2);
        rt2.setAutoReverse(true);
        return new SequentialTransition(rt1, rt2);
    }

    private boolean validateData(int width, int height, int antCount, List<FoodCord> foodCords) {
        boolean isValid = true;
        if (2 > width || width > 200 || 2 > height || height > 200) {
            isValid = false;
        }

        if (0 > foodCords.size() || foodCords.size() > width * height) {
            isValid = false;
        }

        Set<FoodCord> foodCordSet = new HashSet<>(foodCords);
        if (foodCordSet.size() != foodCords.size()) {
            isValid = false;
        }

        if (1 >= antCount || antCount > 20) {
            isValid = false;
        }

        return isValid;
    }
}
