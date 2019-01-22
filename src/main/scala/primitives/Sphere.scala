package primitives

import primitives.operations.Intersectable

case class Sphere(center: Vec3d, radius: Double) extends Intersectable {
  override def intersect(ray: Ray): Boolean = {
    val L = center - ray.start
    val tca = L * ray.dir
    val d2 = L*L - tca*tca
    if (d2 > radius*radius)
      false
    else {
      val thc = math.sqrt(radius*radius - d2)
      val t0 = tca - thc
      val t1 = tca + thc
      if (t0 < 0)
        t1 >= 0
      else
        t0 >= 0
    }
  }
}
