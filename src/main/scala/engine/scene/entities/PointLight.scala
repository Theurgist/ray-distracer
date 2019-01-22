package engine.scene.entities

import engine.scene.primitives.Vec3d

/**
  * Point lighting
  * @param color lighting magnitude
  */
case class PointLight(
    pos: Vec3d,
    color: Vec3d = Vec3d.unit
)