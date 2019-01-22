package engine.scene.primitives.operations

import engine.scene.primitives.Ray

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
}
