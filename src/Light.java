public abstract class Light {
    protected Vector3D position;
    protected Vector3D color;

    public Light(Vector3D position, Vector3D color) {
        this.position = position;
        this.color = color;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getColor() {
        return color;
    }
}
