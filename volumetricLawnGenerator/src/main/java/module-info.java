module ch.vgg.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens engineer.straub.launcher to javafx.fxml;
    exports engineer.straub.launcher;
}