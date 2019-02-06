package cc.theurgist.engine.materials

import cc.theurgist.engine.scene.primitives.Vec3d

/**
  * Properties of scene object appearance
  * @param diffuseColor color represented by pseudo-normalized vector
  * @param specularExponent specular spot size factor
  * @param refractiveIndex refraction factor (1.0< for proper refraction)
  * @param specularAlbedo per-component specular albedo factor
  * @param diffuseAlbedo per-component diffuse albedo factor
  * @param refractAlbedo per-component refract albedo factor
  */
case class Material(
    diffuseColor: Vec3d,
    specularExponent: Double = 20.0,
    refractiveIndex: Double = 1.0,

    specularAlbedo: Vec3d = Vec3d.unit,
    diffuseAlbedo: Vec3d = Vec3d.unit,
    reflectAlbedo: Vec3d = Vec3d.zero,
    refractAlbedo: Vec3d = Vec3d.zero,
)
