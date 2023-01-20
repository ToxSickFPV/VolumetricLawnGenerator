package engineer.straub.model;

public class Grass {

    private final Vector position;
    private final Vector orientationVector;
    private final Vector tiltVector;
    private final int height;

    public Grass(Vector position, Vector orientationVector, Vector tiltVector, int height) {
        this.position = position;
        this.orientationVector = orientationVector;
        this.tiltVector = tiltVector;
        this.height = height;
    }

    public Vector getPosition() {
        return position;
    }

    public int getPosX() {
        return position.getX();
    }

    public int getPosY() {
        return position.getY();
    }

    public Vector getOrientationVector() {
        return orientationVector;
    }

    public int getOrientationX() {
        return orientationVector.getX();
    }

    public int getOrientationY() {
        return orientationVector.getY();
    }

    public Vector getTiltVector() {
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
