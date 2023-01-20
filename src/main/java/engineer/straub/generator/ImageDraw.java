package engineer.straub.generator;

import engineer.straub.model.Vector;
import engineer.straub.model.Grass;
import engineer.straub.model.ImageDrawArgument;
import engineer.straub.util.GeneratorService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class ImageDraw {

    public static BufferedImage drawCircles(int width, int height, int radius, List<Vector> list) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        for (Vector c : list) {
            graphics.setColor(Color.GREEN);
            graphics.fillOval(c.getX()-(radius/2),c.getY()-(radius/2), radius, radius);
        }
        return image;
    }

    /**
     * Generates cross-section images of a grass plane
     * @param arguments Object of all arguments
     * @param service JavaFX service that calls this method
     * @throws IOException
     */
    public static void drawGrasses(ImageDrawArgument arguments, GeneratorService service) throws IOException {
        Function<Double, Integer> calcColor = x -> (int) (x * 255);
        Function<Integer, Integer> pow2 = x -> x * x;

        List<Grass> grasses = RandomPattern.getRandomGrass(
                arguments.getExportWidth(),
                arguments.getExportHeight(),
                arguments.getGrassAmount(),
                arguments.getGrassHeightMax(),
                arguments.getGrassHeightMin());
        java.awt.Color color = new java.awt.Color(
                calcColor.apply(arguments.getFxColor().getRed()),
                calcColor.apply(arguments.getFxColor().getGreen()),
                calcColor.apply(arguments.getFxColor().getBlue()));

        // load image of grass texture
        BufferedImage grassTexture = null;
        if (arguments.hasTexture()) {
            grassTexture = ImageIO.read(new File(arguments.getGrassTexturePath()));
        }

        int maxHeight = 0;
        for (Grass g : grasses) {
            if (maxHeight < g.getHeight()) {
                maxHeight = g.getHeight();
            }
        }

        for (int i = 1; i <= maxHeight; i++) {
            BufferedImage image = new BufferedImage(arguments.getExportWidth(),
                    arguments.getExportHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            for (Grass g : grasses) {
                if (i <= g.getHeight()) {
                    // creating a vector that is 90 deg to the orientation vector of the grass
                    Vector vectorRotated = new Vector(g.getOrientationY(), -g.getOrientationX());
                    // calculate length of the vector;
                    double vectorLen = Math.sqrt(pow2.apply(vectorRotated.getX()) + pow2.apply(vectorRotated.getY()));
                    // create components of the unit vector of 'vectorRotated'
                    double xUnit = vectorRotated.getX() / vectorLen;
                    double yUnit = vectorRotated.getY() / vectorLen;

                    int x = g.getPosX();
                    int y = g.getPosY();

                    // create a scalar with help of a square function
                    double thiccnessScalar = -((double) pow2.apply(i) / (pow2.apply(g.getHeight()) + 0.5)) + 1.0;
                    double curveScalar = -((double) pow2.apply(i) / (pow2.apply(g.getHeight()) + 0.5)) + 1.0;

                    // calculate offset of x & y to make a slight curve in the grass
                    x -= (int) (xUnit * ((1 - curveScalar) * g.getHeight()));
                    y -= (int) (yUnit * ((1 - curveScalar) * g.getHeight()));

                    // make a rotation by the angle of the orientation vector
                    AffineTransform old = graphics.getTransform();
                    graphics.rotate(Math.atan((double) (g.getOrientationY()) / (double) (g.getOrientationX())), x, y);
                    // draw the image TODO also implement Oval if wanted
                    // if a texture is given with texture else with given color
                    if (arguments.hasTexture()) {
                        if (grassTexture == null) throw new NullPointerException("Grass texture is not set");
                        graphics.drawImage(grassTexture,
                                x - ((int) (arguments.getGrassWidth() * thiccnessScalar / 2)),
                                y - ((int) (arguments.getGrassDepth() * thiccnessScalar / 2)),
                                (int) (arguments.getGrassWidth() * thiccnessScalar),
                                (int) (arguments.getGrassDepth() * thiccnessScalar),
                                null);
                    } else {
                        graphics.setColor(color);
                        graphics.fillOval(
                                x - ((int) (arguments.getGrassWidth() * thiccnessScalar / 2)),
                                y - ((int) (arguments.getGrassDepth() * thiccnessScalar / 2)),
                                (int) (arguments.getGrassWidth() * thiccnessScalar),
                                (int) (arguments.getGrassDepth() * thiccnessScalar));
                    }
                    graphics.setTransform(old);
                }
            }
            graphics.dispose();

            // export the image
            String exportPath = arguments.getExportPath();
            exportPath += exportPath.charAt(exportPath.length() - 1) != '/' ? "/" : ""; // correct path if necessary
            export(
                    image,
                    String.format("%sgrass%s.png", exportPath, formatInt(i, digitCount(arguments.getGrassHeightMax())))
            );

            service.serviceNotify();
        }
    }

    /**
     * Exports a given image to the given path
     */
    public static void export(BufferedImage image, String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "png", file);
    }

    /**
     * Used to make integers all the same length by adding zeros in front of the digit
     * @param i integer to process
     * @param digits number of digits i should match
     * @return formatted string of the integer
     */
    public static String formatInt(int i, int digits) {
        if (digits < 0) throw new IllegalArgumentException("digits can not be negative");
        if (i >= Math.pow(10, digits - 1)) return String.valueOf(i);
        return "0" + formatInt(i, digits - 1);
    }

    /**
     * Used to count the digits amount of an int for base 10
     * @param i integer to count the digit amount
     * @return number of digits i contains
     */
    public static int digitCount(int i) {
        if (i < 0) throw new IllegalArgumentException("number can not be negative");
        if (i < 10) return 1;
        return digitCount(i / 10) + 1;
    }
}
