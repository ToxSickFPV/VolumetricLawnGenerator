package engineer.straub.generator;

import engineer.straub.model.Vector;
import engineer.straub.model.Grass;

import java.util.ArrayList;
import java.util.List;

public class RandomPattern {

    public static List<Vector> getRandomCoordinates(int width, int height, int amount) {
        if (width < 1 || height < 1 || amount < 1) {
            throw new IllegalArgumentException("values must be greater than 0");
        } else if (width * height < amount) {
            throw new IllegalArgumentException("amount must be smaller than with * height");
        }

        List<Vector> coordinates = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Vector coordinate = new Vector((int) (Math.random() * width), (int) (Math.random() * height));
            boolean isDuplicate = false;
            for (Vector c : coordinates) {
                if (c.equals(coordinate)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                i--;
                continue;
            }
            coordinates.add(coordinate);
        }
        return coordinates;
    }

    public static List<Grass> getRandomGrass(int width, int height, int amount, int maxGrassHeight, int minGrassHeight) {
        List<Grass> grasses = new ArrayList<>();
        List<Vector> positions = getRandomCoordinates(width, height, amount);
        for (Vector position : positions) {
            Vector orientationVector = new Vector(randomInt(8), randomInt(8));
            Vector tiltVector = new Vector((int) (Math.random() * 8), 8);
            grasses.add(new Grass(position, orientationVector, tiltVector,
                    (int) (Math.random() * (maxGrassHeight - minGrassHeight) + minGrassHeight)));
        }
        return grasses;
    }

    private static int randomInt(int limit) {
        int ret = (int) (Math.random() * limit);
        if ((int) (Math.random() * 2) == 1) {
            return -ret;
        }
        return ret;
    }
}
