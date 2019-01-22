package engine.scene.entities

import engine.scene.primitives.Vec3d

/**
  * Point lighting
  * @param pos position
  * @param intensity lighting magnitude
  */
case class PointLight(pos: Vec3d, intensity: Double)