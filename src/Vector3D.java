public class Vector3D {
    float x;
    float y;
    float z;

    float nx;
    float ny;
    float nz;
    float magnitude;

    public Vector3D (float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3D Normalize(){
        this.magnitude = (float) Math.sqrt(x*x+y*y+z*z);
        this.nx = x/ magnitude;
        this.ny = y/ magnitude;
        this.nz = z/ magnitude;
        return new Vector3D(nx,ny,nz);
    }

    public float GetX() {
        return x;
    }

    public float GetY(){
        return y;
    }

    public float GetZ(){
        return z;
    }

    public Vector3D Multiply (float f)
    {
        return new Vector3D(x*f,y*f,z*f);
    }

    public Vector3D Add(Vector3D v)
    {
        return new Vector3D(x+v.x,y+v.y,z+v.z);
    }

    public Vector3D CrossP(Vector3D v){
        float x = (this.y*v.z) - (this.z*v.y);
        float y = (this.z*v.x) - (this.x*v.z);
        float z = (this.z*v.y) - (this.y*v.z);

        return new Vector3D(x,y,z);
    }

    public float DotP(Vector3D v){
        return this.x*v.x + this.y*v.y + this.z*v.z;
    }

    public Vector3D Sub(Vector3D v) {
        return new Vector3D(x-v.x,y-v.y,z-v.z);
    }

    public float Length() {
        return magnitude;
    }
}
