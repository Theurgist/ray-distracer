package samples

import java.awt.Color

import engine.scene.Scene3D
import engine.scene.entities.Camera
import engine.scene.objects.Sphere
import engine.scene.primitives.Vec3d


/**
  * Just some spheres
  */
class Spheres {

  private val objects = Iterable(
    Sphere(Vec3d(0,0,-40), 5, new Color(0, 100, 255).getRGB),

    Sphere(Vec3d(-3,0,-15), 2, new Color(255, 100, 0).getRGB),
    Sphere(Vec3d(-3,3,-30), 2, new Color(50, 100, 100).getRGB),
    Sphere(Vec3d(3,0,-30), 2, new Color(50, 255, 100).getRGB),
  )

  val cam = new Camera(Vec3d(0,0,0), Vec3d(1,0,0), math.Pi/2)
  val scene = new Scene3D(objects)
}
