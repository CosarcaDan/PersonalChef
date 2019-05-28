package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Recipe;
import service.Service;

import java.util.List;

public class RecipeController {

    private Stage stage;
    private Service service;
    private Recipe recipe;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private TableColumn<Recipe, String> colName;

    @FXML
    private TableColumn<Recipe, String> colCousine;

    @FXML
    private TableColumn<Recipe, String> colType;

    @FXML
    private TableColumn<Recipe, String> colProcess;

    @FXML
    private TableColumn<Recipe, String> colTools;

    @FXML
    private TableColumn<Recipe, String> colIngredients;

    @FXML
    private TableView table;

    public void handleRowSelect(MouseEvent mouseEvent) {
        Recipe recipeee = (Recipe) table.getSelectionModel().getSelectedItem();
        System.out.println("Selected recipe: " + recipeee.getName());
        recipe = recipeee;
    }

    public void chooseRecipe(MouseEvent mouseEvent) {
        service.enforceRecipe(recipe);
        System.out.println("Recipe enforced!");
    }

    public void exit(MouseEvent mouseEvent) {
        this.stage.close();
    }

    public void loadAllRecipes(List<Recipe> recipesList){

        ObservableList<Recipe> observableList = FXCollections.observableArrayList(recipesList);

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCousine.setCellValueFactory(new PropertyValueFactory<>("cousine"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTools.setCellValueFactory(new PropertyValueFactory<>("neededTools"));
        colProcess.setCellValueFactory(new PropertyValueFactory<>("process"));

        table.setItems(observableList);
    }
}
