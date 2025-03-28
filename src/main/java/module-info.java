module edu.farmingdale.recipegenerator {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.farmingdale.recipegenerator to javafx.fxml;
    exports edu.farmingdale.recipegenerator;
}