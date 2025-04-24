public class Ray {
    private Vector3D p; //start Punkt
    private Vector3D v; //Richtung
    private float s; //distance von P auf v

    public Ray (Vector3D p, Vector3D v){
        this.p = p;
        this.v = v;
    }

    public Vector3D getP(){
        return p;
    }

    public Vector3D getV(){
        return v;
    }

    public Vector3D getPoint(float t) {
        return p.Add(v.Multiply(t));
    }

}
