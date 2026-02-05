module com.tony.web {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.tony.web to javafx.fxml;
    exports com.tony.web;
}