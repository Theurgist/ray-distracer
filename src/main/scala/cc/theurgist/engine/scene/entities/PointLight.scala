package cc.theurgist.engine.scene.entities

import cc.theurgist.engine.scene.primitives.Vec3d

/**
  * Point lighting
  * @param color lighting magnitude
  */
case class PointLight(
    pos: Vec3d,
    color: Vec3d = Vec3d.unit
)