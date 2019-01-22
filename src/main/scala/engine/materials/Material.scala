package engine.materials

import engine.scene.primitives.Vec3d

/**
  * Properties of scene object appearance
  * @param diffuseColor color represented by pseudo-normalized vector
  * @param specularExponent specular spot size factor
  * @param specularAlbedo per-component specular albedo factor
  * @param diffuseAlbedo per-component diffuse albedo factor
  */
case class Material(
    diffuseColor: Vec3d,
    specularExponent: Double = 20.0,
    specularAlbedo: Vec3d = Vec3d.unit,
    diffuseAlbedo: Vec3d = Vec3d.unit
)
