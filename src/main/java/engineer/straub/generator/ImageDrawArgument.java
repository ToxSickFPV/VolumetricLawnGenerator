package engineer.straub.generator;

import engineer.straub.launcher.PrimaryController;
import javafx.scene.paint.Color;

public class ImageDrawArgument {

    private int grassHeightMax;
    private int grassHeightMin;
    private int grassWidth;
    private int grassDepth;
    private int grassCurve;
    private int grassAmount;
    private String grassTexturePath;
    private int exportHeight;
    private int exportWidth;
    private String exportPath;
    private boolean hasTexture;
    private Color fxcolor;

    public ImageDrawArgument(int grassHeightMax, int grassHeightMin,
                             int grassWidth, int grassDepth, int grassCurve, int grassAmount, String grassTexturePath,
                             int exportHeight, int exportWidth, String exportPath, boolean hasTexture, Color fxcolor) {
        this.grassHeightMax = grassHeightMax;
        this.grassHeightMin = grassHeightMin;
        this.grassWidth = grassWidth;
        this.grassDepth = grassDepth;
        this.grassCurve = grassCurve;
        this.grassAmount = grassAmount;
        this.grassTexturePath = grassTexturePath;
        this.exportHeight = exportHeight;
        this.exportWidth = exportWidth;
        this.exportPath = exportPath;
        this.hasTexture = hasTexture;
        this.fxcolor = fxcolor;
    }

    public int getGrassHeightMax() {
        return grassHeightMax;
    }

    public int getGrassHeightMin() {
        return grassHeightMin;
    }

    public int getGrassWidth() {
        return grassWidth;
    }

    public int getGrassDepth() {
        return grassDepth;
    }

    public int getGrassCurve() {
        return grassCurve;
    }

    public int getGrassAmount() {
        return grassAmount;
    }

    public String getGrassTexturePath() {
        return grassTexturePath;
    }

    public int getExportHeight() {
        return exportHeight;
    }

    public int getExportWidth() {
        return exportWidth;
    }

    public String getExportPath() {
        return exportPath;
    }

    public boolean hasTexture() {
        return hasTexture;
    }

    public Color getFxColor() {
        return fxcolor;
    }
}
