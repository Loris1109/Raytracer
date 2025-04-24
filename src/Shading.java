import java.util.List;

public class Shading {

    public static Vector3D calculateLighting(Vector3D point, Vector3D normal, Vector3D viewDir, List<Light> lights, List<SceneObject> objects) {
        Vector3D color = new Vector3D(0, 0, 0); // start mit schwarz

        for (Light light : lights) {
            Vector3D lightDir = light.getPosition().Sub(point).Normalize();

            // Schatten prüfen (Shadow Ray)
            Ray shadowRay = new Ray(point.Add(normal.Multiply(0.001f)), lightDir); // Punkt leicht verschieben
            boolean inShadow = false;

            for (SceneObject obj : objects) {
                double t = obj.intersect(shadowRay);
                double distToLight = light.getPosition().Sub(point).Length();
                if (t > 0.001 && t < distToLight) {
                    inShadow = true;
                    break;
                }
            }

            if (inShadow) continue;

            // Diffuse Komponente (Lambert)
            float diff = Math.max(0, normal.DotP(lightDir));
            Vector3D diffuse = light.getColor().Multiply(diff);

            // Specular Komponente (optional später)

            color = color.Add(diffuse);
        }

        return color;
    }
}
