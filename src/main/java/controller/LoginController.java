package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.User;
import repository.UserDatabaseRepository;
import service.Service;

import java.io.IOException;

public class LoginController {

    private PersonalChefController personalCtrl;
    private Stage stage;
    private Service service;

    public LoginController (){}

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private TextField textFieldUsername;

    public void login(javafx.scene.input.MouseEvent mouseEvent) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        if (textFieldUsername.getText().isEmpty()){
            alert.setContentText("Name is empty!");
            alert.setHeaderText("No given name.");
            alert.showAndWait();
        }else if (textFieldPassword.getText().isEmpty()) {
            alert.setContentText("No password given!");
            alert.setHeaderText("No password given.");
            alert.showAndWait();
        } else{

            String name = textFieldUsername.getText();
            String password = textFieldPassword.getText();
            User user = service.login(name, password);

            //user does not exist in database
            if (user == null){
                alert.setContentText("Invalid Username and Password");
                alert.setHeaderText("Login failed");
                alert.showAndWait();
            } else{
                try{
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PersonalChefController.class.getResource("/personalChef.fxml"));
                    Parent root = loader.load();
                    PersonalChefController personalCtrl = loader.getController();

                    Stage mainStage = new Stage();
                    mainStage.setTitle("Personal Chef");
                    Scene scene = new Scene(root);
                    mainStage.setScene(scene);

                    personalCtrl.setStage(mainStage);
                    personalCtrl.setUser(user);
                    personalCtrl.setService(service);
                    personalCtrl.setRoot(root);

                    scene.getRoot().requestFocus();
                    mainStage.show();
                    this.stage.close();
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }

            }
        }
    }

    public void exit(javafx.scene.input.MouseEvent mouseEvent) {
        this.stage.close();
    }
}
