package cc.theurgist.engine.scene.objects

import cc.theurgist.engine.materials.Material
import cc.theurgist.engine.scene.primitives.Vec3d
import cc.theurgist.engine.scene.primitives.operations.Intersectable

/**
  * Visible scene object abstraction
  */
abstract class VisibleObj extends Intersectable {
  def center: Vec3d
  def material: Material
}
