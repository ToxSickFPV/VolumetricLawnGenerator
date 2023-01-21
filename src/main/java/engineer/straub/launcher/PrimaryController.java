package engineer.straub.launcher;

import engineer.straub.generator.ImageDraw;
import engineer.straub.model.ImageDrawArgument;
import engineer.straub.util.GeneratorService;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.awt.font.ImageGraphicAttribute;

public class PrimaryController {

    public final int MAX_WINDOW_WIDTH = 900;
    public final String COLOR_PG_GREEN = "#00e300";
    public final String COLOR_PG_DARK_GREEN = "#006600";
    public final String COLOR_PG_RED = "#e30000";

    @FXML
    public TextField tfGrassHeightMax, tfGrassHeightMin, tfGrassWidth, tfGrassDepth, tfGrassCurve, tfGrassAmount,
            tfGrassTexturePath, tfExportHeight, tfExportWidth, tfExportPath;
    public CheckBox cbTexture, cbOval;
    public ColorPicker cpOvalColor;
    public Button btnGenerate, btnCancel;
    public Label error, info;
    public Pane progressBar;
    private GeneratorService service = null;

    @FXML
    private void initialize() {
        cbTexture.setSelected(true);
        cpOvalColor.setDisable(true);
        btnCancel.setDisable(true);
        error.setStyle("-fx-text-fill: #000000");
        error.setText("");
        setProgressBarProgress(0);
        // TODO: Next line is temporary
        tfExportPath.setText("C:\\Users\\pasca\\Documents\\grassGeneratorTest");
    }

    @FXML
    private void onGenerate() {
        System.out.println(ImageDraw.formatInt(49, 5));
        System.out.println(ImageDraw.digitCount(1000));
        error.setText("");
        info.setText("Generating...");
        btnGenerate.setDisable(true);
        btnCancel.setDisable(false);
        setProgressBarColor(COLOR_PG_GREEN);
        setProgressBarProgress(0);

        try {
            // getting all data from the textinputs and convert them to the right format
            ImageDrawArgument arguments = new ImageDrawArgument(
                    Integer.parseInt(tfGrassHeightMax.getText()),
                    Integer.parseInt(tfGrassHeightMin.getText()),
                    Integer.parseInt(tfGrassWidth.getText()),
                    Integer.parseInt(tfGrassDepth.getText()),
                    Integer.parseInt(tfGrassCurve.getText()),
                    Integer.parseInt(tfGrassAmount.getText()),
                    tfGrassTexturePath.getText(),
                    Integer.parseInt(tfExportHeight.getText()),
                    Integer.parseInt(tfExportWidth.getText()),
                    tfExportPath.getText(),
                    cbTexture.isSelected(),
                    cpOvalColor.getValue());

            service = new GeneratorService(arguments, this);
            service.start();

        } catch (NumberFormatException ne) {
            onGenerationFailed("ERROR: Do only user numbers in text fields (except paths)");
        }
    }

    @FXML
    private void onCancel() {
        btnCancel.setDisable(true);
        btnGenerate.setDisable(false);
        if (service == null || !service.isRunning()) return;
        info.setText("Canceling...");
        ImageDraw.interrupt();
    }

    @FXML
    public void onGenerationFinished(String message) {
        btnGenerate.setDisable(false);
        btnCancel.setDisable(true);
        setProgressBarColor(COLOR_PG_DARK_GREEN);
        setProgressBarProgress(MAX_WINDOW_WIDTH);
        error.setText("");
        info.setText(message);
    }

    @FXML
    public void onGenerationFailed(String errorMessage) {
        btnGenerate.setDisable(false);
        btnCancel.setDisable(true);
        setProgressBarProgress(MAX_WINDOW_WIDTH);
        setProgressBarColor(COLOR_PG_RED);
        info.setText("");
        error.setText(errorMessage);
    }

    @FXML
    public void setProgressBarProgress(int width) {
        if (width < 0) width = 0;
        if (width > MAX_WINDOW_WIDTH) width = MAX_WINDOW_WIDTH;
        progressBar.setPrefWidth(width);
    }

    @FXML
    public void setProgressBarColor(String color) {
        progressBar.setStyle("-fx-background-color: " + color);
    }

    @FXML
    private void textureAction() {
        if (cbOval.isSelected()) {
            cbOval.setSelected(false);
            cbTexture.setSelected(true);
            tfGrassTexturePath.setDisable(false);
            cpOvalColor.setDisable(true);
        } else {
            cbTexture.setSelected(true);
        }
    }

    @FXML
    private void ovalAction() {
        if (cbTexture.isSelected()) {
            cbTexture.setSelected(false);
            cbOval.setSelected(true);
            tfGrassTexturePath.setDisable(true);
            cpOvalColor.setDisable(false);
        } else {
            cbOval.setSelected(true);
        }
    }

}
