package engine.scene.entities

import engine.scene.primitives.Vec3d

/**
  * Rendering spot description
  * @param pos position of an eye
  * @param dir viewing direction
  * @param fov field-of-view (radians)
  */
class Camera(var pos: Vec3d, var dir: Vec3d, var fov: Double)
