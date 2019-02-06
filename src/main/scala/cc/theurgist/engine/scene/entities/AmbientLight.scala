package cc.theurgist.engine.scene.entities

import cc.theurgist.engine.scene.primitives.Vec3d

/**
  * Diffuse lighting
  * @param clr lighting color magnitude
  */
case class AmbientLight(clr: Vec3d)