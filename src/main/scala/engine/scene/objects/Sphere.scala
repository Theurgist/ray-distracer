package engine.scene.objects

import engine.materials.Material
import engine.scene.primitives.{Ray, Vec3d}

/**
  * Basic 3D object: spheric surface
  * @param center sphere center
  * @param radius sphere radius
  * @param material surface rendering properties
  */
case class Sphere(center: Vec3d, radius: Double, material: Material) extends VisibleObj {

  override def intersect(ray: Ray): Option[Double] = {
    val L = center - ray.start
    val tca = L * ray.dir
    val d2 = L*L - tca*tca
    if (d2 > radius*radius)
      None
    else {
      val thc = math.sqrt(radius*radius - d2)
      val t0 = tca - thc
      val t1 = tca + thc
      if (t0 < 0) {
        if (t1 >= 0)
          Option(t1)
        else
          None
      }
      else if (t0 >= 0)
        Option(t0)
      else
        None
    }
  }
}
