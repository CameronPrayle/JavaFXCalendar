module com.example.calendertest2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.datatransfer;
    requires java.desktop;

    opens com.example.calendertest2 to javafx.fxml;
    exports com.example.calendertest2;
}