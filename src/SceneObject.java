public abstract class SceneObject {
    protected Vector3D color;

    public SceneObject(Vector3D color){
        this.color = color;
    }


    public abstract float intersect(Ray ray);

    public abstract Vector3D getNormal(Vector3D point);
}
