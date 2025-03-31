package edu.farmingdale.recipegenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController {

    @FXML
    private Label welcomeText,alertLabel;
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

    private ObservableList<Item> itemsObservable;

    @FXML
    public void initialize() {
        setUpTableView();

        responseTextArea.setVisible(false);

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

        // Check if the name is provided (mandatory)
        if (name.isEmpty()) {
            alertLabel.setText("Name is mandatory.");
            return;
        }

        // If neither quantity nor weight is provided, show an error message
        if (quantityTextField.getText().isEmpty() && weightTextField.getText().isEmpty()) {
            alertLabel.setText("Either quantity or weight must be provided.");
            return;
        }

        try {
            // Check if quantity is provided
            if (!quantityTextField.getText().isEmpty()) {
                quantity = Integer.parseInt(quantityTextField.getText());
            }

            // Check if weight is provided
            if (!weightTextField.getText().isEmpty()) {
                weight = weightTextField.getText().trim();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        //Create new item with validated fields
        Item newItem = new Item(name, quantity, weight.isEmpty() ? null : weight);

        // Add item to ObservableList
        itemsObservable.add(newItem);

        //Clear text fields after adding the item
        nameTextField.clear();
        quantityTextField.clear();
        weightTextField.clear();
        alertLabel.setText("");
    }

    @FXML
    public void generateButtonMethod() {
        // Collect all the items from the TableView
        ObservableList<Item> items = tableView.getItems();

        // Build a string representation of the items for the prompt
        StringBuilder prompt = new StringBuilder();
        prompt.append("I will provide you with a list of ingredients. Please use these ingredients to generate a recipe.\n");
        prompt.append("Ensure that the recipe is concise and uses the given ingredients directly. Format the recipe clearly with proper indentation.\n\n");

        prompt.append("Ingredients:\n");
        for (Item item : items) {
            // Check if the quantity is not null or zero before appending it
            if (item.getQuantity() > 0) {
                prompt.append(item.getQuantity()).append(" ");
            }

            // Always append the item name
            prompt.append(item.getName());

            // If weight is provided it will be added
            if (item.getWeight() != null) {
                prompt.append(" ").append(item.getWeight());
            }

            prompt.append("\n");
        }
            // this gives a prompt on how to reply
        prompt.append("\nRecipe:\n");
        prompt.append("1. Start by preparing the ingredients as follows:\n");
        prompt.append("   - For each ingredient, follow the steps as needed.\n");
        prompt.append("   - Ensure proper use of each ingredient in the recipe.\n\n");
        prompt.append("2. Prepare the recipe based on the ingredients provided.\n");
        prompt.append("3. Make sure the recipe is clear and easy to follow.\n");

        prompt.append("\nEnd of prompt.");

        // Send the prompt to OpenAI
        try {
            String response = OpenAI.getTextResponse(prompt.toString());

            responseTextArea.setVisible(true);
            responseTextArea.setEditable(false);
            //display the response from OpenAI
            responseTextArea.setText(response);
        } catch (Exception e) {
            e.printStackTrace();
            responseTextArea.setText("Failed to generate recipe. Please try again.");
        }
    }

}
