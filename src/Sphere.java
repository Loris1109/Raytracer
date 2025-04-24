public class Sphere extends SceneObject {
    private Vector3D center; //Center
    private float radius;
    //maybe mit material Klasse ersetzen

    public Sphere(Vector3D center, float radius, Vector3D color)
    {
        super(color);
        this.radius = radius;
        this.center = center;
    }

    public float getRadius(){
        return radius;
    }

    public Vector3D getCenter(){
        return center;
    }

    @Override
    public float intersect(Ray ray) {
        Vector3D oc = ray.getP().Sub(center); //origin to center
        Vector3D dir = ray.getV();
        //a * t² + b * t + c = 0
        float a = dir.DotP(dir);
        float b = 2 * oc.DotP(dir);
        float c = oc.DotP(oc) - radius*radius;
        float discriminant = b*b - 4*a*c;
//        discriminant > 0	Two real roots — ray hits twice (entry & exit points)
//        discriminant = 0	One real root — ray grazes the sphere tangent
//        discriminant < 0	No real roots — ray misses the sphere

        if(discriminant < 0) return -1;

        float t1 = (float) ((-b - Math.sqrt(discriminant)) / (2.0 * a));
        float t2 = (float) ((-b + Math.sqrt(discriminant)) / (2.0 * a));

        if (t1 > 0.001) return t1;
        if (t2 > 0.001) return t2;

        return -1.0f;


    }

    @Override
    public Vector3D getNormal(Vector3D point) {
        return point.Sub(center).Normalize();
    }
}
