package edu.farmingdale.recipegenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class HelloController {

    @FXML
    private Label welcomeText, alertLabel;

    @FXML
    private TextArea responseTextArea;

    @FXML
    private TextField nameTextField, quantityTextField, weightTextField;

    @FXML
    private Button generateButton, addButton;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, String> nameColumn;

    @FXML
    private TableColumn<Item, Integer> quantityColumn;

    @FXML
    private TableColumn<Item, String> weightColumn;

    @FXML
    private AnchorPane fridgePane;

    @FXML
    private ImageView fridgeImageView;

    private ObservableList<Item> itemsObservable;

    @FXML
    private ImageView tomatoImageView;  // Declare ImageView for the tomato image

    @FXML
    public void initialize() {
        setUpTableView();
        responseTextArea.setVisible(false);

        // Load and style fridge image
        Image fridgeImage = new Image(getClass().getResource("/images/open_fridge.png").toExternalForm());
        fridgeImageView.setImage(fridgeImage);
        fridgeImageView.setFitWidth(280);
        fridgeImageView.setFitHeight(200);
        fridgeImageView.setPreserveRatio(true);

        // Load the tomato image and make it clickable
        Image tomatoImage = new Image(getClass().getResource("/images/t1.jpg").toExternalForm());
        tomatoImageView = new ImageView(tomatoImage);
        tomatoImageView.setFitHeight(45);
        tomatoImageView.setFitWidth(45);
        tomatoImageView.setPreserveRatio(true);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(3.0);
        shadow.setOffsetX(2.0);
        shadow.setOffsetY(2.0);
        shadow.setColor(Color.gray(0.5));
        tomatoImageView.setEffect(shadow);

        // Position the image on the fridge
        tomatoImageView.setLayoutX(30 + Math.random() * 200);
        tomatoImageView.setLayoutY(20 + Math.random() * 130);

        fridgePane.getChildren().add(tomatoImageView);

        // Make the tomato image clickable
        tomatoImageView.setOnMouseClicked(event -> {
            addToTableView();
        });

        // Tooltips for better UX
        addButton.setTooltip(new Tooltip("Add item to fridge"));
        generateButton.setTooltip(new Tooltip("Generate recipe from fridge contents"));

        // Hover effects
        addButton.setOnMouseEntered(e -> addButton.setScaleX(1.05));
        addButton.setOnMouseExited(e -> addButton.setScaleX(1.0));

        generateButton.setOnMouseEntered(e -> generateButton.setScaleY(1.05));
        generateButton.setOnMouseExited(e -> generateButton.setScaleY(1.0));

        // Apply CSS
        fridgePane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().add(getClass().getResource("/Styling/style.css").toExternalForm());
            }
        });
    }

    public void setUpTableView() {
        itemsObservable = FXCollections.observableArrayList();
        tableView.setItems(itemsObservable);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
    }

    @FXML
    protected void addToTableView() {
        if ("Tomato".isEmpty()) {
            alertLabel.setText("Name is mandatory.");
            return;
        }

        if (1 <= 0 && ("Fresh" == null || "Fresh".isEmpty())) {
            alertLabel.setText("Either quantity or weight must be provided.");
            return;
        }

        try {
            Item newItem = new Item("Tomato", 1, "Fresh");
            itemsObservable.add(newItem);

            // Clear fields if you want to reset them
            nameTextField.clear();
            quantityTextField.clear();
            weightTextField.clear();
            alertLabel.setText("");
        } catch (Exception e) {
            alertLabel.setText("Invalid input.");
            return;
        }
    }

    @FXML
    public void generateButtonMethod() {
        ObservableList<Item> items = tableView.getItems();

        StringBuilder prompt = new StringBuilder();
        prompt.append("I will provide you with a list of ingredients. Please use these ingredients to generate a recipe.\n");
        prompt.append("Ensure that the recipe is concise and uses the given ingredients directly. Format the recipe clearly with proper indentation.\n\n");

        prompt.append("Ingredients:\n");
        for (Item item : items) {
            if (item.getQuantity() > 0) {
                prompt.append(item.getQuantity()).append(" ");
            }

            prompt.append(item.getName());

            if (item.getWeight() != null) {
                prompt.append(" ").append(item.getWeight());
            }

            prompt.append("\n");
        }

        prompt.append("\nRecipe:\n");
        prompt.append("1. Start by preparing the ingredients as follows:\n");
        prompt.append("   - For each ingredient, follow the steps as needed.\n");
        prompt.append("   - Ensure proper use of each ingredient in the recipe.\n\n");
        prompt.append("2. Prepare the recipe based on the ingredients provided.\n");
        prompt.append("3. Make sure the recipe is clear and easy to follow.\n");

        prompt.append("\nEnd of prompt.");

        try {
            String response = OpenAI.getTextResponse(prompt.toString());

            responseTextArea.setVisible(true);
            responseTextArea.setEditable(false);
            responseTextArea.setText(response);

            responseTextArea.getStyleClass().add("cool-text-area");

        } catch (Exception e) {
            e.printStackTrace();
            responseTextArea.setText("Failed to generate recipe. Please try again.");
        }
    }
}
