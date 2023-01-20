package engineer.straub.model;

public class Grass {

    private final Coordinate position;
    private final Coordinate orientationVector;
    private final Coordinate tiltVector;
    private final int height;

    public Grass(Coordinate position, Coordinate orientationVector, Coordinate tiltVector, int height) {
        this.position = position;
        this.orientationVector = orientationVector;
        this.tiltVector = tiltVector;
        this.height = height;
    }

    public Coordinate getPosition() {
        return position;
    }

    public int getPosX() {
        return position.getX();
    }

    public int getPosY() {
        return position.getY();
    }

    public Coordinate getOrientationVector() {
        return orientationVector;
    }

    public int getOrientationX() {
        return orientationVector.getX();
    }

    public int getOrientationY() {
        return orientationVector.getY();
    }

    public Coordinate getTiltVector() {
        return tiltVector;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "position: (" + position.getX() + ", " + position.getY() + "), orientations Vector: ("
                + orientationVector.getX() + ", " + orientationVector.getY() + "), tilt Vector: ("
                + tiltVector.getX() + ", " + tiltVector.getY() + "), height: " + height;
    }
}
