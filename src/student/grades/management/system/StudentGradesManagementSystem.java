/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package student.grades.management.system;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 *
 * @author shamia-pc
 */
public class StudentGradesManagementSystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent ts = FXMLLoader.load(StudentGradesManagementSystem.class.getResource("s.fxml"));
        Scene s = new Scene(ts);

        // 2. إعداد حدث الضغط على زر ESCAPE للإغلاق
        s.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Dialog<ButtonType> d = new Dialog<>();
                d.setTitle("Exit");
                d.setHeaderText("Exiting");
                d.setContentText("Are you sure?");
                
                ButtonType btnyes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType btnyno = new ButtonType("No", ButtonBar.ButtonData.NO);
                
                d.getDialogPane().getButtonTypes().addAll(btnyes, btnyno);
                
                if (d.showAndWait().get() == btnyes) {
                    Platform.exit();
                }
            }
        });

        // 3. إعدادات النافذة الرئيسية
        stage.setScene(s);
        stage.setTitle("Student Grades Management System");
        stage.setMaximized(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // دالة main تكتفي فقط بإطلاق التطبيق
        launch(args);
    }
}