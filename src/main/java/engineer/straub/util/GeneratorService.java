package engineer.straub.util;

import engineer.straub.generator.*;
import engineer.straub.launcher.PrimaryController;
import engineer.straub.model.GeneratorResult;
import engineer.straub.model.ImageDrawArgument;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

public class GeneratorService extends Service<GeneratorResult> {

    private ImageDrawArgument arguments;
    private PrimaryController controller;
    private int layerAmount;
    private int finishedLayers = 0;

    public GeneratorService(ImageDrawArgument arguments, PrimaryController controller) {
        this.arguments = arguments;
        this.controller = controller;
        this.layerAmount = arguments.getGrassHeightMax();

        // TODO: rebuild to use setOnFailed()
        setOnSucceeded(event -> {
            GeneratorResult result = (GeneratorResult) event.getSource().getValue();
            if (result.isSuccess()) {
                controller.onGenerationFinished();
            } else {
                controller.onGenerationFailed(result.getMessage());
            }
            //infoLabel.setText((String) event.getSource().getValue());
        });
    }

    @Override
    protected Task<GeneratorResult> createTask() {
        return new Task<>() {
            @Override
            protected GeneratorResult call() throws Exception {
                try {
                    startGenerator();
                    return new GeneratorResult(true);
                } catch (IOException ioe) {
                    return new GeneratorResult(
                            false,
                            "ERROR: Not able to export textures! Check path name!"
                    );
                } catch (NumberFormatException nfe) {
                    return new GeneratorResult(
                            false,
                            "ERROR: Do only user numbers in text fields (except paths)"
                    );
                } catch (Exception e) {
                    return new GeneratorResult(
                            false,
                            String.format("EXCEPTION: %s: %s", e.getClass(), e.getMessage())
                    );
                }
            }
        };
    }

    private void startGenerator() throws IOException {
        ImageDraw.drawGrasses(arguments, this);
    }

    public void serviceNotify() {
        finishedLayers++;
        double factor = (double) finishedLayers / (double) layerAmount;
        controller.setProgressBarProgress((int) (factor * controller.MAX_WINDOW_WIDTH));
    }
}
