package engineer.straub.util;

import engineer.straub.generator.*;
import engineer.straub.launcher.PrimaryController;
import engineer.straub.model.GeneratorResult;
import engineer.straub.model.ImageDrawArgument;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

public class GeneratorService extends Service<GeneratorResult> {

    private ImageDrawArgument imgDrawArguments;
    private PrimaryController controller;
    private int layerAmount;
    private int finishedLayers = 0;

    public GeneratorService(ImageDrawArgument imgDrawArguments, PrimaryController controller) {
        this.imgDrawArguments = imgDrawArguments;
        this.controller = controller;
        this.layerAmount = imgDrawArguments.getGrassHeightMax();

        // TODO: rebuild to use setOnFailed()
        setOnSucceeded(event -> {
            GeneratorResult result = (GeneratorResult) event.getSource().getValue();
            if (result.isSuccess()) {
                controller.onGenerationFinished();
            } else {
                controller.onGenerationFailed(result.getMessage());
            }
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
                            "ERROR: Not able to export textures! Check export path location!"
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
        ImageDraw.drawGrasses(imgDrawArguments, this);
    }

    /**
     * Notification from the ImageDraw class to tell if an image layer is finished
     */
    public void serviceNotify() {
        finishedLayers++;
        double factor = (double) finishedLayers / (double) layerAmount;
        controller.setProgressBarProgress((int) (factor * controller.MAX_WINDOW_WIDTH));
    }
}
