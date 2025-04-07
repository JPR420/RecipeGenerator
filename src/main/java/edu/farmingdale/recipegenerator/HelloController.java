package edu.farmingdale.recipegenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    public void initialize() {
        setUpTableView();
        responseTextArea.setVisible(false);

        // Load fridge image
        Image fridgeImage = new Image(getClass().getResource("/images/open_fridge.png").toExternalForm());
        fridgeImageView.setImage(fridgeImage);
        fridgeImageView.setFitWidth(300);
        fridgeImageView.setPreserveRatio(true);
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
        String name = nameTextField.getText().trim();
        int quantity = 0;
        String weight = "";

        if (name.isEmpty()) {
            alertLabel.setText("Name is mandatory.");
            return;
        }

        if (quantityTextField.getText().isEmpty() && weightTextField.getText().isEmpty()) {
            alertLabel.setText("Either quantity or weight must be provided.");
            return;
        }

        try {
            if (!quantityTextField.getText().isEmpty()) {
                quantity = Integer.parseInt(quantityTextField.getText());
            }

            if (!weightTextField.getText().isEmpty()) {
                weight = weightTextField.getText().trim();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        Item newItem = new Item(name, quantity, weight.isEmpty() ? null : weight);
        itemsObservable.add(newItem);

        // Clear fields
        nameTextField.clear();
        quantityTextField.clear();
        weightTextField.clear();
        alertLabel.setText("");

        // Display item image in fridge
        Image itemImage = new Image(getClass().getResource("/images/item.png").toExternalForm());
        ImageView itemView = new ImageView(itemImage);
        itemView.setFitHeight(40);
        itemView.setFitWidth(40);

        // Random placement inside the fridge pane
        itemView.setLayoutX(50 + Math.random() * 180);
        itemView.setLayoutY(60 + Math.random() * 250);

        fridgePane.getChildren().add(itemView);
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
        } catch (Exception e) {
            e.printStackTrace();
            responseTextArea.setText("Failed to generate recipe. Please try again.");
        }
    }
}
