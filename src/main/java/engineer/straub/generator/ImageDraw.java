package engineer.straub.generator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class ImageDraw {

    public static BufferedImage drawCircles(int width, int height, int radius, List<Coordinate> list) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        for (Coordinate c : list) {
            graphics.setColor(Color.GREEN);
            graphics.fillOval(c.getX()-(radius/2),c.getY()-(radius/2), radius, radius);
        }
        return image;
    }

//    public static void drawGrasses(int width, int height, List<Grass> grasses, double grassWidth,  double grassDepth,
//                                   int grassCurve, boolean hasTexture, String texturePath,
//                                   String exportPath, Color color) throws IOException {
//
//        // TODO make else the BufferedImage
//        // image of grass texture
//        File file;
//        if (hasTexture) {
//            file = new File(texturePath);
//        } else {
//            file = new File("src/main/resources/imgs/grass2.png");
//        }
//        BufferedImage grassTexture = ImageIO.read(file);
//        Function<Integer, Integer> pow2 = x -> x * x;
//
//        int maxHeight = 0;
//        for (Grass g : grasses) {
//            if (maxHeight < g.getHeight()) {
//                maxHeight = g.getHeight();
//            }
//        }
//
//        for (int i = 1; i <= maxHeight; i++) {
//            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D graphics = image.createGraphics();
//            for (Grass g : grasses) {
//                if (i <= g.getHeight()) {
//                    // creating a vector that is 90 deg to the orientation vector of the grass
//                    Coordinate vectorRotated = new Coordinate(g.getOrientationY(), -g.getOrientationX());
//                    // calculate length of the vector;
//                    double vectorLen = Math.sqrt(pow2.apply(vectorRotated.getX()) + pow2.apply(vectorRotated.getY()));
//                    // create components of the unit vector of 'vectorRotated'
//                    double xUnit = vectorRotated.getX() / vectorLen;
//                    double yUnit = vectorRotated.getY() / vectorLen;
//
//                    int x = g.getPosX();
//                    int y = g.getPosY();
//
//                    // create a scalar with help of a square function
//                    double thiccnessScalar = -((double) pow2.apply(i) / (pow2.apply(g.getHeight()) + 0.5)) + 1.0;
//                    double curveScalar = -((double) pow2.apply(i) / (pow2.apply(g.getHeight()) + 0.5)) + 1.0;
//
//                    // calculate offset of x & y to make a slight curve in the grass
//                    x -= (int) (xUnit * ((1 - curveScalar) * g.getHeight()));
//                    y -= (int) (yUnit * ((1 - curveScalar) * g.getHeight()));
//
//                    // make a rotation by the angle of the orientation vector
//                    AffineTransform old = graphics.getTransform();
//                    graphics.rotate(Math.atan((double) (g.getOrientationY()) / (double) (g.getOrientationX())), x, y);
//                    // draw the image TODO also implement Oval if wanted
//                    if (hasTexture) {
//                        graphics.drawImage(grassTexture,
//                                x - ((int) (grassWidth * thiccnessScalar / 2)),
//                                y - ((int) (grassDepth * thiccnessScalar / 2)),
//                                (int) (grassWidth * thiccnessScalar),
//                                (int) (grassDepth * thiccnessScalar),
//                                null);
//                    } else {
//                        graphics.setColor(color);
//                        graphics.fillOval(
//                                x - ((int) (grassWidth * thiccnessScalar / 2)),
//                                y - ((int) (grassDepth * thiccnessScalar / 2)),
//                                (int) (grassWidth * thiccnessScalar),
//                                (int) (grassDepth * thiccnessScalar));
//                    }
//                    graphics.setTransform(old);
//                }
//            }
//            graphics.dispose();
//
//            // export the image
//            exportPath += exportPath.charAt(exportPath.length() - 1) != '/' ? "/" : "";
//            export(image, exportPath + "grass" + i + ".png");
//        }
//    }

    public static void drawGrasses(ImageDrawArgument arguments, GeneratorThread thread) throws IOException {
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

        // TODO make else the BufferedImage
        // image of grass texture
        File file;
        if (arguments.hasTexture()) {
            file = new File(arguments.getGrassTexturePath());
        } else {
            file = new File("src/main/resources/imgs/grass2.png");
        }
        BufferedImage grassTexture = ImageIO.read(file);

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
                    Coordinate vectorRotated = new Coordinate(g.getOrientationY(), -g.getOrientationX());
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
                    if (arguments.hasTexture()) {
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
            exportPath += exportPath.charAt(exportPath.length() - 1) != '/' ? "/" : "";
            export(image, exportPath + "grass" + i + ".png");

            thread.generatorNotify();
        }
    }

    public static void export(BufferedImage image, String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "png", file);
    }
}
