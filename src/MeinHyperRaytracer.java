import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MeinHyperRaytracer {


    public static void main(String[] args) {

        int resX = 1024;
        int resY = 768;

        int[] pixels = new int[resX * resY];

        MemoryImageSource mis = new MemoryImageSource(resX, resY, new DirectColorModel(24, 0xff0000, 0xff00, 0xff), pixels, 0, resX);
        mis.setAnimated(true);
        Image image = Toolkit.getDefaultToolkit().createImage(mis);

        JFrame frame = new JFrame("s0591904");
        frame.add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Camera camera = new Camera();
        Vector3D pos = camera.getC()
                .Add(camera.getV().Multiply(camera.distanceToView)) // move forward
                .Sub(camera.getR().Multiply(camera.viewWidth / 2.0f)) // left half screen
                .Add(camera.getU().Multiply(camera.viewHight / 2.0f)); // top half screen

        List<SceneObject> scene = new ArrayList<>();
        Sphere sphere1 = new Sphere(new Vector3D(0,2,6),1f,new Vector3D(255,0,0));
        Sphere sphere2 = new Sphere(new Vector3D(0,0,5),1f,new Vector3D(0,255,0));
        Sphere sphere3 = new Sphere(new Vector3D(2,0,5),1f,new Vector3D(0,0,255));
        scene.add(sphere1);
        scene.add(sphere2);
        scene.add(sphere3);

        List<Light> lights = new ArrayList<>();
        lights.add(new PointLight(new Vector3D(2, 2, 0), new Vector3D(255, 255, 255)));
        int hitCount = 0;

        for (int y = 0; y < resY; ++y) {
            for (int x = 0; x < resX; ++x) {

                Vector3D pixelPos3D = pos
                        .Add(camera.getR().Multiply((x + 0.5f) * (camera.viewWidth / resX)))
                        .Sub(camera.getU().Multiply((y + 0.5f) * (camera.viewHight / resY)));
                //cameraPos + Abstand * Blickrichtung - R * width /2 -U * height/2 + x * R * width/resX

                Vector3D rayDir = pixelPos3D.Sub(camera.getC()).Normalize();
                Ray  ray = new Ray(camera.getC(), rayDir);
                //compute Ray direction

                int pixelColor = 0x000000;
                float closestIntersection = (float) Double.POSITIVE_INFINITY;


                for (SceneObject obj : scene) {
                    float intersection = obj.intersect(ray);
                    if (intersection > 0 && intersection < closestIntersection) {
                        closestIntersection = intersection;
                        Vector3D hitPoint = ray.getP().Add(ray.getV().Multiply(intersection));
                        Vector3D normal = obj.getNormal(hitPoint);
                        Vector3D viewDir = ray.getV().Multiply(-1).Normalize();

                        pixelColor = calculateLighting(hitPoint,normal,viewDir, obj.color, lights,scene);
                        hitCount++;

                    }
                }
                pixels[y * resX + x] = pixelColor;
            }
        }
        System.out.println("Hits: " + hitCount);
        mis.newPixels();
    }

    public static int calculateLighting(Vector3D point, Vector3D normal, Vector3D viewDir, Vector3D objectColor, List<Light> lights, List<SceneObject> objects) {
        Vector3D color = new Vector3D(0, 0, 0); // start mit schwarz

        for (Light light : lights) {
            Vector3D lightDir = light.getPosition().Sub(point).Normalize();

//            // Schatten prüfen (Shadow Ray)
//            Ray shadowRay = new Ray(point.Add(normal.Multiply(0.001f)), lightDir); // Punkt leicht verschieben
//            boolean inShadow = false;
//
//            for (SceneObject obj : objects) {
//                double t = obj.intersect(shadowRay);
//                double distToLight = light.getPosition().Sub(point).Length();
//                if (t > 0.001 && t < distToLight) {
//                    inShadow = true;
//                    break;
//                }
//            }
//
//            if (inShadow) continue;

            // Diffuse Komponente (Lambert)
            float diff = Math.max(0, normal.DotP(lightDir));

            // Normalisiere Lichtfarbe (255-basierend)
            Vector3D lightColor = light.getColor().Multiply(1.0f / 255.0f);

            // Farbmischung: Objektfarbe * Lichtfarbe * Intensität
            Vector3D diffuse = new Vector3D(
                    objectColor.x * lightColor.x * diff,
                    objectColor.y * lightColor.y * diff,
                    objectColor.z * lightColor.z * diff
            );
            // Objektfarbe * Lichtfarbe * Intensität

            color = color.Add(diffuse);
        }

        return rgbToHex(color);
    }
    public static int rgbToHex(Vector3D color) {
        int red = (int)(color.x);
        int green = (int)(color.y);
        int blue = (int)(color.z);

        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);
        return (red << 16) | (green << 8) | blue;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}