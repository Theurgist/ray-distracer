package engine.scene.objects

import engine.scene.primitives.Vec3d
import engine.scene.primitives.operations.Intersectable

/**
  * Visible scene object abstraction
  */
abstract class VisibleObj extends Intersectable {
  def center: Vec3d
  def color: Int
}
