package primitives.operations

import primitives.Ray

trait Intersectable {
  def intersect(ray: Ray): Boolean
}
