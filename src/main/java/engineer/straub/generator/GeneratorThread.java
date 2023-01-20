package engineer.straub.generator;

import engineer.straub.launcher.PrimaryController;

import java.io.IOException;

public class GeneratorThread extends Thread {

    private ImageDrawArgument arguments;
    private PrimaryController controller;
    private int layerAmount;
    private int finishedLayers = 0;

    public GeneratorThread(ImageDrawArgument arguments, PrimaryController controller) {
        this.arguments = arguments;
        this.controller = controller;
        this.layerAmount = arguments.getGrassHeightMax();
    }

    @Override
    public void run() {
        try {
            ImageDraw.drawGrasses(arguments, this);
            controller.onGenerationFinished();
        } catch (IOException ioe) {
            System.out.println("in ioe");
            controller.onGenerationFailed("ERROR: Not able to export textures! Check path name!");
        } catch (NumberFormatException nfe) {
            controller.onGenerationFailed("ERROR: Do only user numbers in text fields (except paths)");
        }
    }

    public void generatorNotify() {
        finishedLayers++;
        double factor = (double) finishedLayers / (double) layerAmount;
        controller.setProgressBarProgress((int) (factor * controller.MAX_WINDOW_WIDTH));
    }

}
