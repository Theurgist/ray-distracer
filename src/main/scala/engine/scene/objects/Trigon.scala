package engine.scene.objects

import engine.materials.Material
import engine.scene.primitives.{Ray, Vec3d}

case class Trigon(v: Array[Vec3d], material: Material) extends VisibleObj {
  assert(v.length >= 3)

  val center: Vec3d = Vec3d(
    (v(0).x + v(1).x + v(2).x) / 3,
    (v(0).y + v(1).y + v(2).y) / 3,
    (v(0).z + v(1).z + v(2).z) / 3
  )


  /**
    * Calculate intersection distance using Möller–Trumbore algorithm. At now it does not provide invisible side
    * @param ray checking ray
    * @return distance to intersection, or None - if ray does not intersect object
    */
  override def intersect(ray: Ray): Option[Double] = {
    val edge1 = v(1) - v(0)
    val edge2 = v(2) - v(0)
    val h = ray.dir ^ edge2
    val a = edge1 * h

    if (a > -Trigon.epsilon && a < Trigon.epsilon)
      None // This ray is parallel to this triangle
    else {
      val f = 1.0 / a
      val s = ray.start - v(0)
      val u = f * (s * h)
      if (u < 0 || u > 1)
        None
      else {
        val q = s ^ edge1
        val v = f * (ray.dir * q)
        if (v < 0 || u + v > 1)
          None
        else {
          // At this stage we can compute t to find out where the intersection point is on the line.
          val t = f * (edge2 * q)
          if (t > Trigon.epsilon) {
            Some(t)
          } else
            None // This means that there is a line intersection but not a ray intersection.
        }
      }
    }
  }

  override def surfaceNormal(hit: Vec3d): Vec3d = {
    val edge1 = v(1) - center
    val edge2 = v(2) - center

    (edge2 ^ edge1).normalized
  }

}

object Trigon {
  private val epsilon = 1e-5
}