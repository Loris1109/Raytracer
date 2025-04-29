public class Camera {
    private Vector3D c;
    private Vector3D v;
    private Vector3D u = new Vector3D(0,1,0); //up
    private Vector3D r = new Vector3D(1,0,0); //right

    public final float distanceToView = 1;
    public final float viewWidth = 4;
    public final float viewHeight = 3;

    public Camera(){
        this.c = new Vector3D(0,0,0);
        this.v = new Vector3D(0,0,1);
    }

    public Camera(Vector3D c, Vector3D v){
        this.c = c;
        this.v = v;
    }

    public Vector3D getR()
    {
        return r;
    }

    public Vector3D getU(){
        return u;
    }
    public Vector3D getV(){
        return v;
    }
    public Vector3D getC(){
        return c;
    }
}
