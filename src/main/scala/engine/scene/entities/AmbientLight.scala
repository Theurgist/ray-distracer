package engine.scene.entities

import engine.scene.primitives.Vec3d

/**
  * Diffuse lighting
  * @param clr lighting color magnitude
  */
case class AmbientLight(clr: Vec3d)