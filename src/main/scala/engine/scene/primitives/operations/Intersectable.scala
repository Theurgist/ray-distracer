package engine.scene.primitives.operations

import engine.scene.primitives.{Ray, Vec3d}

/**
  * Trait for an object, which can be checked for an intersection with a ray
  */
trait Intersectable {

  /**
    * Calculate intersection distance
    * @param ray checking ray
    * @return distance to intersection, or None - if ray does not intersect object
    */
  def intersect(ray: Ray): Option[Double]

  /**
    * Calculate surface normal (outward)
    * @param hit collision point or direction (if needed)
    * @return
    */
  def surfaceNormal(hit: Vec3d): Vec3d
}
