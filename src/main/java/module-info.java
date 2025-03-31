module edu.farmingdale.recipegenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;


    opens edu.farmingdale.recipegenerator to javafx.fxml;
    exports edu.farmingdale.recipegenerator;
}