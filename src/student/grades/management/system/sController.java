package student.grades.management.system;

import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class sController implements Initializable {

    @FXML
    private BarChart<String, Number> Student;
    @FXML
    private TableView<StudentModel> t_Student;
    @FXML
    private TableColumn<StudentModel, String> ID;
    @FXML
    private TableColumn<StudentModel, String> Name;

    @FXML
    private Button addData, edit, delete, search;
    @FXML
    private TextField tf_id, tf_name, tf_grade;
    @FXML
    private ImageView imagestudent;
    @FXML
    private Label resultLabel;

    private studentBag<String> bag = new studentBag<>(100);
    @FXML
    private Button clear;

    @FXML
    private Button frequency;
//    @FXML
//    private CategoryAxis xAxis;
    @FXML
    private TableColumn<StudentModel, Double> grade;
//    @FXML
//    private CategoryAxis xAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            imagestudent.setImage(new Image(getClass().getResourceAsStream("/image/Student.png")));
        } catch (Exception e) {

            System.out.println("Sorry,Image NOt Found");
        }
           ID.setCellValueFactory(new PropertyValueFactory<>("id"));
           Name.setCellValueFactory(new PropertyValueFactory<>("name"));
           grade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        edit.setDisable(true);
        delete.setDisable(true);

        fill_Table();
        updateBarChart();
        updateStatisticsLabel();
        selectRow();
    }

    private void massage(String header, String content) {
        Dialog<String> d = new Dialog<>();
        d.setTitle("System Message");
        d.setHeaderText(header);
        d.setContentText(content);
        d.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
        d.showAndWait();
    }

    private boolean isValidation() {
        return !tf_id.getText().trim().isEmpty()
                && !tf_name.getText().trim().isEmpty()
                && !tf_grade.getText().trim().isEmpty();
    }

    @FXML
    private void clearFields() {
        tf_name.clear();
        tf_id.clear();
        tf_grade.clear();
        addData.setDisable(false);
        edit.setDisable(true);
        delete.setDisable(true);
    }

    @FXML
    private void AddData(ActionEvent event) {
        if (!isValidation()) {
            massage("ERROR", "Please fill all fields.");
            return;
        }

        String id = tf_id.getText().trim();
        String name = tf_name.getText().trim();
        String gradeStr = tf_grade.getText().trim();

        if (bag.contains(id)) {
            massage("ERROR", "Student ID already exists!");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeStr);

            if (grade < 0 || grade > 100) {
                massage("ERROR", "Grade must be between 0 and 100.");
                return;
            }

            boolean added = bag.add(id, name, gradeStr);

            if (added) {
                fill_Table();
                updateBarChart();
                updateStatisticsLabel();
                clearFields();
                massage("SUCCESS", "Student Added Successfully.");
            } else {
                massage("ERROR", "Sorry, Bag Is Full.");
            }
        } catch (NumberFormatException e) {
            massage("ERROR", "Grade must be a valid number.");
        }
    }

    @FXML
    private void Edit(ActionEvent event) {
        int selectedIndex = t_Student.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            massage("ERROR", "Please select a student from the table to edit.");
            return;
        }
        if (!isValidation()) {
            massage("ERROR", "Please fill all fields.");
            return;
        }

        String id = tf_id.getText().trim();
        String name = tf_name.getText().trim();
        String gradeStr = tf_grade.getText().trim();

        bag.updateAt(selectedIndex, id, name, gradeStr);

        fill_Table();
        updateBarChart();
        updateStatisticsLabel();
        clearFields();
        massage("SUCCESS", "Student Updated Successfully.");
    }

    @FXML
    private void Delete(ActionEvent event) {
          if (!bag.IsEmppty()) {
        int selectedIndex = t_Student.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            massage("ERROR", "Please select a student to delete.");
            return;
        }

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirm Delete");
        dialog.setHeaderText("Are you sure you want to delete this student?");
        dialog.getDialogPane().getButtonTypes().addAll(yes, no);

        dialog.showAndWait().ifPresent(type -> {
            if (type == yes) {
                bag.removeAt(selectedIndex);
                fill_Table();
                updateBarChart();
                updateStatisticsLabel();
                clearFields();
                massage("SUCCESS", "Student Deleted Successfully.");
            }else{
                massage("Information", "Thank you for contacting us, and have a nice day!");
            }
        });} else {
            massage("Search Student", "Sorry, Bag Is Empty");
        }
    }

    @FXML
    private void Search(ActionEvent event) {
        if (!bag.IsEmppty()) {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Search Student");
            tid.setHeaderText("Search Student by ID");
            tid.setContentText("Enter Student ID:");

            tid.showAndWait().ifPresent(idStr -> {
                String searchId = idStr.trim();
                if (searchId.isEmpty()) {
                    return;
                }

                if (bag.contains(searchId)) {
                    massage("Found", "Student with ID [" + searchId + "] exists in the system.");
                } else {
                    massage("Not Found", "No student found with ID [" + searchId + "].");
                }
            });
        } else {
            massage("Search Student", "Sorry, Bag Is Empty");
        }
    }

    private void selectRow() {
        t_Student.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                tf_id.setText(newSel.getId());
                tf_name.setText(newSel.getName());
                tf_grade.setText(String.valueOf(newSel.getGrade()));

                addData.setDisable(true);
                edit.setDisable(false);
                delete.setDisable(false);
            } else {
                clearFields();
            }
        });
    }

   private void fill_Table() {
    ObservableList<StudentModel> list = FXCollections.observableArrayList();

   
    if (!bag.IsEmppty()) {
        for (int i = 0; i < bag.Size(); i++) {
            String id = String.valueOf(bag.getId(i));
            String name = String.valueOf(bag.getName(i));
            double grade = Double.parseDouble(String.valueOf(bag.getGrade(i)));
            list.add(new StudentModel(id, name, grade));
        }
    }

   
    t_Student.setItems(list);
    t_Student.refresh();
}

    private void updateBarChart() {
    Student.getData().clear();

    if (Student.getYAxis() instanceof NumberAxis) {
        NumberAxis yAxis = (NumberAxis) Student.getYAxis();
        yAxis.setTickUnit(5);
        yAxis.setMinorTickVisible(false);
        yAxis.setForceZeroInRange(true);
       
    }

    XYChart.Series<String, Number> seriesgrade = new XYChart.Series<>();
    seriesgrade.setName("Grade Distribution");

    int[][] dist = bag.calculateGradeDistribution();

    seriesgrade.getData().add(new XYChart.Data<>("90-100", dist[0][1]));
    seriesgrade.getData().add(new XYChart.Data<>("80-89", dist[1][1]));
    seriesgrade.getData().add(new XYChart.Data<>("70-79", dist[2][1]));
    seriesgrade.getData().add(new XYChart.Data<>("60-69", dist[3][1]));
    seriesgrade.getData().add(new XYChart.Data<>("<60", dist[4][1]));

    Student.getData().add(seriesgrade);

   javafx.application.Platform.runLater(() -> {
    Student.lookupAll(".chart-bar").forEach(node -> 
        node.setStyle("-fx-bar-fill: #401F71;")
    );
});
}

    private void updateStatisticsLabel() {
        if (bag.IsEmppty()) {
            if (resultLabel != null) {
                resultLabel.setText("");
            }
            return;
        }

        double avg = bag.calculateAverage();
        double max = bag.getMaxGrade();
        double min = bag.getMinGrade();
        int size = bag.Size();
        

        if (resultLabel != null) {
 
            resultLabel.setText("Statistics Panel \n"+"Size:"+size+"\n"+"Avg: "+avg+"\n"+"Max: "+max+"\n"+"Min: "+min);
              resultLabel.setTextFill(Color.WHITE);
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        resultLabel.setAlignment(javafx.geometry.Pos.CENTER);
        }
    }

    @FXML
    private void Frequency(ActionEvent event) {
        if (!bag.IsEmppty()) {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Grade Frequency Check");
            tid.setHeaderText("Check Grade Frequency");
            tid.setContentText("Enter Grade to find frequency:");

            tid.showAndWait().ifPresent(inputStr -> {

                String gradeStr = inputStr.trim();
                if (gradeStr.isEmpty()) {
                    return;
                }

                try {

                    double gradeVal = Double.parseDouble(gradeStr);

                    int count = bag.frequencyOf(String.valueOf(gradeVal));

                    massage("Frequency Result", "The grade [" + gradeVal + "] appears " + count + " time(s) in the bag.");

                } catch (NumberFormatException e) {
                    massage("Error", "Please enter a valid numeric grade!");
                }
            });
        } else {
            massage("Frequency Result", "Sorry, Bag Is Empty");
        }
    }
}
