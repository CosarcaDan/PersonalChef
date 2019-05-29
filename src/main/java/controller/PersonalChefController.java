package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import model.Process;
import repository.UserDatabaseRepository;
import service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonalChefController {

    private User user;
    private Stage stage;
    private Service service;
    private Parent root;

    public PersonalChefController(){
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private ChoiceBox choiceBoxProcess;

    @FXML
    private ChoiceBox choiceBoxType;


    @FXML
    private CheckBox checkBoxOven;

    @FXML
    private CheckBox checkBoxMixer;

    @FXML
    private CheckBox checkBoxStove;

    @FXML
    private CheckBox checkBoxPan;

    @FXML
    private TextField textFieldCousin;

    @FXML
    private TextField textFieldIngredient;

    @FXML
    private TextField textFieldName;

    @FXML
    private Label labelGreeting;


    @FXML
    private void initialize(){
        ObservableList<String> processList = FXCollections.observableArrayList(Process.BAKED.toString(), Process.BOILED.toString(), Process.FRIED.toString(), Process.STEAMED.toString());
        choiceBoxProcess.setItems(processList);

        ObservableList<String> typeList = FXCollections.observableArrayList(Type.BREAKFAST.toString(), Type.DINNER.toString(), Type.LUNCH.toString(), Type.SNACK.toString());
        choiceBoxType.setItems(typeList);

    }

    public void findMatches(MouseEvent mouseEvent) {
        Process process;
        if (choiceBoxProcess.getSelectionModel().isEmpty()){
            process = null;
        }else{
            process = getProcess((String) choiceBoxProcess.getSelectionModel().getSelectedItem());
        }

        Type type;
        if (choiceBoxType.getSelectionModel().isEmpty()){
           type = null;
        }else{
            type = getType((String) choiceBoxType.getSelectionModel().getSelectedItem());
        }

        String cousine;
        if ( textFieldCousin.getText().isEmpty()){
            cousine = null;
        }else{
            cousine = textFieldCousin.getText();
        }

        Ingredient ingredient;
        if (textFieldIngredient.getText().isEmpty()){
            ingredient = null;
        }else{
            String ingr = textFieldIngredient.getText();
            ingredient = new Ingredient(ingr, new ArrayList<Ingredient>());
        }

        NeededTools tool = null;
        if (!checkBoxMixer.isSelected()){
            tool = NeededTools.MIXER;
        }else if (!checkBoxOven.isSelected()){
            tool = NeededTools.OVEN;
        }else if (!checkBoxPan.isSelected()){
            tool = NeededTools.PAN;
        }else if (!checkBoxStove.isSelected()){
            tool = NeededTools.STOVE;
        }

        List<Recipe> recipesList = service.recipesMatch(process, type, tool, cousine, ingredient);

        try{
            FXMLLoader loader = new  FXMLLoader();
            loader.setLocation(RecipeController.class.getResource("/matchedRecipes2.fxml"));
            Parent root2 = loader.load();
            RecipeController recipeCtrl = loader.getController();

            recipeCtrl.loadAllRecipes(recipesList);
            Stage stage = new Stage();
            stage.setTitle("Recipes.");
            stage.setScene(new Scene(root2, 650, 400));
            recipeCtrl.setStage(stage);
            recipeCtrl.setService(service);
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void delete(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        if (textFieldName.getText().isEmpty()){
            alert.setContentText("Name is empty!");
            alert.setHeaderText("No given name.");
            alert.showAndWait();
        }else{
            service.deleteIngredient(textFieldName.getText());
            textFieldName.clear();
        }

    }

    public void add(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        if (textFieldName.getText().isEmpty()){
            alert.setContentText("Name is empty!");
            alert.setHeaderText("No given name.");
            alert.showAndWait();
        }
        else{
            List<Ingredient> alternatives = new ArrayList<>();
            service.addIngredient(textFieldName.getText(), alternatives);
            textFieldName.clear();
        }

    }

    public void exit(MouseEvent mouseEvent) {
        this.stage.close();
    }

    public Process getProcess(String process){
        if (process.equals(Process.BAKED.toString())){
            return Process.BAKED;
        }
        if (process.equals(Process.BOILED.toString())){
            return Process.BOILED;
        }
        if (process.equals(Process.FRIED.toString())){
            return Process.FRIED;
        }
        if (process.equals(Process.STEAMED.toString())){
            return Process.STEAMED;
        }
        return null;
    }

    public Type getType (String type){
        if (type.equals(Type.BREAKFAST.toString())){
            return Type.BREAKFAST;
        }
        if (type.equals(Type.SNACK.toString())){
            return Type.SNACK;
        }
        if (type.equals(Type.LUNCH.toString())){
            return Type.LUNCH;
        }
        if (type.equals(Type.DINNER.toString())){
            return Type.DINNER;
        }
        return null;
    }


}
