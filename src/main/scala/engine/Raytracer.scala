package engine

import engine.image.BitmapImage
import engine.materials.Material
import engine.scene.Scene3D
import engine.scene.entities.{Camera, PointLight}
import engine.scene.primitives.{Ray, Vec3d}

import scala.language.postfixOps

/**
  * Raytracing engine
  * @param width canvas width
  * @param height canvas height
  * @param scene renderable scene
  */
class Raytracer(width: Int, height: Int, scene: Scene3D, maxDepth: Int = 5) {

  def gen(camera: Camera): BitmapImage = {
    println("⏲ Start raytracing")

    val img = BitmapImage.genBlack(width, height)
    val h = height.toDouble
    val w = width.toDouble

    //val framebuffer: Array[Int] = Array.ofDim[Int](width*height)

    //TODO camera's code does not work with position properly
    (0 until width).foreach(i => (0 until height).foreach(j => {
      val x =  (2*(i + 0.5)/w  - 1)*math.tan(camera.fov/2)*width/h
      val y = -(2*(j + 0.5)/h - 1)*math.tan(camera.fov/2)
      val dir = Vec3d.normalized(x, y, -1)

      val ray = Ray(camera.pos, dir)
      val color: Int = castRay(ray).asColorInt
      //framebuffer[i+j*width] = color
      img.bi.setRGB(i, j, color)
    }))


    println("✓ Finish raytracing")
    img
  }

  private def castRay(ray: Ray, depth: Int = 0): Vec3d = {
    val intersected = scene.getIntersections(ray)
    if (depth >= maxDepth || intersected.isEmpty)
      Vec3d.zero
    else {
      //intersected.map(vo => {

      val vo = intersected.head
      val hit = ray.start + ray.dir * vo._1
      val surfaceNormal = vo._2.surfaceNormal(hit)
      val material = vo._2.material

      val reflectDir = reflect(ray.dir, surfaceNormal).normalized
      val refractDir = refract(ray.dir, surfaceNormal, material.refractiveIndex).normalized

      // offset the original point to avoid occlusion by the object itself
      val reflectOrigin = if (reflectDir * surfaceNormal < 0) hit - surfaceNormal*1e-3 else hit + surfaceNormal*1e-3
      val refractOrigin = if (refractDir * surfaceNormal < 0) hit - surfaceNormal*1e-3 else hit + surfaceNormal*1e-3

      val reflectColor: Vec3d = castRay(Ray(reflectOrigin, reflectDir), depth+1)
      val refractColor: Vec3d = castRay(Ray(refractOrigin, refractDir), depth+1)


      val (diffuseFullColor: Iterable[Vec3d], specularFullColor: Iterable[Vec3d]) = scene.pointLights.map(pl => {
        val lightDir = (pl.pos - hit).normalized

        def checkShadow: Boolean = {
          val lightDistance = (pl.pos - hit).len
          // checking if the point lies in the shadow of the lights[i]
          val shadowOrigin = if (lightDir * surfaceNormal < 0) hit - surfaceNormal*1e-3 else hit + surfaceNormal*1e-3

          val shadowIntersected = scene.getIntersections(Ray(shadowOrigin, lightDir))
          if (shadowIntersected.nonEmpty) {
            val shadowHit = shadowOrigin + lightDir * shadowIntersected.head._1
            (shadowHit-shadowOrigin).len < lightDistance
          } else
            false
        }

        if (checkShadow)
          (Vec3d.zero, Vec3d.zero)
        else {
          val diffuse: Vec3d = pl.color * math.max(0.0, lightDir*surfaceNormal)
          val specular: Vec3d = calculateSpectacular(lightDir, surfaceNormal, ray.dir, material, pl)
          (diffuse, specular)
        }

      }).unzip

      val (dR, dG, dB) = diffuseFullColor.map(c => (c.x, c.y, c.z)) unzip3
      val (sR, sG, sB) = specularFullColor.map(c => (c.x, c.y, c.z)) unzip3

      def calcClr(ambientComp: Double, diffuseComp: Double, reflectComp: Double, refractComp: Double,
                  diffuseAlbedoComp: Double, specularAlbedoComp: Double, reflectAlbedoComp: Double, refractAlbedoComp: Double,
                  diffuseInstances: Iterable[Double],
                  specularInstances: Iterable[Double]): Double = {

        val ambient = ambientComp * diffuseComp * diffuseAlbedoComp
        val diffuse = diffuseInstances.sum * diffuseComp * diffuseAlbedoComp
        val specular = specularInstances.sum * specularAlbedoComp
        val reflect = reflectComp * reflectAlbedoComp
        val refract = refractComp * refractAlbedoComp
        ambient + diffuse + specular + reflect + refract
      }

      val pixel = Vec3d(
        calcClr(scene.ambientLight.clr.x, material.diffuseColor.x, reflectColor.x, refractColor.x,
          material.diffuseAlbedo.x, material.specularAlbedo.x, material.reflectAlbedo.x, material.refractAlbedo.x,
          dR, sR),
        calcClr(scene.ambientLight.clr.y, material.diffuseColor.y, reflectColor.y, refractColor.y,
          material.diffuseAlbedo.y, material.specularAlbedo.y, material.reflectAlbedo.y, material.refractAlbedo.y,
          dG, sG),
        calcClr(scene.ambientLight.clr.z, material.diffuseColor.z, reflectColor.z, refractColor.z,
          material.diffuseAlbedo.z, material.specularAlbedo.z, material.reflectAlbedo.z, material.refractAlbedo.z,
          dB, sB)
      )
      pixel

      //})

    }
  }

  private def calculateSpectacular(lightDir: Vec3d, surfaceNormal: Vec3d, rayDirection: Vec3d, material: Material, pl: PointLight) = {

    val spectacularAngularCoeff: Double =
      math.max(0, -reflect(-lightDir, surfaceNormal)*rayDirection)

    def calcIntensityPerComponent(lightIntensity: Double): Double =
      math.pow(spectacularAngularCoeff, material.specularExponent) * lightIntensity

    Vec3d(
      calcIntensityPerComponent(pl.color.x),
      calcIntensityPerComponent(pl.color.y),
      calcIntensityPerComponent(pl.color.z)
    )

  }


  private def reflect(I: Vec3d, surfaceNormal: Vec3d): Vec3d =
    I - surfaceNormal * 2 * (I * surfaceNormal)

  /**
    * Refraction according to Snell's law
    */
  private def refract(I: Vec3d, surfaceNormal: Vec3d, refractiveIndex: Double): Vec3d = {
    val cosI = - math.max(-1, math.min(1, I*surfaceNormal))

    val (effectiveCosI: Double, etaI: Double, etaT: Double, n: Vec3d) = if (cosI < 0) {
      // the ray is inside the object - swap the indices and invert the normal to get the correct result
      (-cosI, refractiveIndex, 1.0, -surfaceNormal)
    } else {
      (cosI, 1.0, refractiveIndex, surfaceNormal)
    }

    val eta = etaI / etaT
    val k = 1 - eta*eta*(1 - effectiveCosI*effectiveCosI)
    if (k < 0)
      Vec3d.zero
    else
      I*eta + n*(eta * effectiveCosI - math.sqrt(k))
  }

}
