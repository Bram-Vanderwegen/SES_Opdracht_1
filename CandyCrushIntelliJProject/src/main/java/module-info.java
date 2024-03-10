module be.kuleuven.candycrushintellijproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens be.kuleuven.candycrushintellijproject to javafx.fxml;
    exports be.kuleuven.candycrushintellijproject;
}