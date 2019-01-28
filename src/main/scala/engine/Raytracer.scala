package engine

import java.awt.image.{BufferedImage, WritableRaster}

import com.typesafe.scalalogging.StrictLogging
import engine.image.BitmapImage
import engine.materials.Material
import engine.scene.Scene3D
import engine.scene.entities.{Camera, PointLight}
import engine.scene.primitives.{Ray, Vec3d}
import engine.system.PerformanceTimer

import scala.language.postfixOps

/**
  * Raytracing engine
  * @param w canvas width
  * @param h canvas height
  * @param scene renderable scene
  */
class Raytracer(w: Double, h: Double, scene: Scene3D, rs: RenderingSettings) extends StrictLogging {
  private val framebuffer: Array[Int] = Array.ofDim[Int](w.toInt * h.toInt * 4)


  def gen(camera: Camera): BitmapImage = {
    logger.info("Start raytracing")
    val pt = new PerformanceTimer(s"Raytrace ${w.toInt}*${h.toInt}=${w*h/10e3} Kpixels; settings: $rs")

    pt.startLap()
    val img = if (rs.buffered) {
      genRays(w,h,camera,framebuffer)
      genImageFromArray(framebuffer, w.toInt, h.toInt)
    } else {
      val img = BitmapImage.genBlack(w.toInt, h.toInt)
      //TODO camera's code does not work with position properly
      (0 until w.toInt).foreach(i => (0 until h.toInt).foreach(j => {
        val x =  (2*(i + 0.5)/w  - 1)*math.tan(camera.fov/2)*w/h
        val y = -(2*(j + 0.5)/h - 1)*math.tan(camera.fov/2)
        val dir = Vec3d.normalized(x, y, -1)

        val ray = Ray(camera.pos, dir)
        val color: Int = castRay(ray).asColorInt
        //framebuffer[i+j*width] = color
        img.bi.setRGB(i, j, color)
      }))
      img
    }
    pt.finishLap("Whole process")
    pt.finish()

    logger.info("Finish raytracing")
    img
  }

  private def genRays(w: Double, h: Double, camera: Camera, framebuffer: Array[Int]): Unit = {
    (0 until w.toInt).foreach(i => (0 until h.toInt).foreach(j => {
      val x =  (2*(i + 0.5)/w  - 1)*math.tan(camera.fov/2)*w/h
      val y = -(2*(j + 0.5)/h - 1)*math.tan(camera.fov/2)
      val dir = Vec3d.normalized(x, y, -1)

      val ray = Ray(camera.pos, dir)
      val color: Int = castRay(ray).asColorInt
      framebuffer(i + j*w.toInt) = color
    }))
  }

  private def genImageFromArray(pixels: Array[Int], w: Int, h: Int): BitmapImage = {
    val img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
    val raster = img.getData.asInstanceOf[WritableRaster]
    raster.setPixels(0,0, w,h, pixels)
    new BitmapImage(img)
  }

  private def castRay(ray: Ray, depth: Int = 0): Vec3d = {
    val intersected = scene.getIntersections(ray)
    if (depth >= rs.rayTracingDepth || intersected.isEmpty) {
      Vec3d.zero
      Vec3d(.3, .3, .3)
    } else {
      //intersected.map(vo => {

      val vo = intersected.head
      val hit = ray.start + ray.dir * vo._1
      val surfaceNormal = vo._2.surfaceNormal(hit)
      val material = vo._2.material

      val reflectColor: Vec3d = {
        val reflectDir = reflect(ray.dir, surfaceNormal).normalized
        // offset the original point to avoid occlusion by the object itself
        val reflectOrigin = if (reflectDir * surfaceNormal < 0) hit - surfaceNormal*1e-3 else hit + surfaceNormal*1e-3

        castRay(Ray(reflectOrigin, reflectDir), depth+1)
      }

      val refractColor = {
        val refractDir = refract(ray.dir, surfaceNormal, material.refractiveIndex).normalized
        // offset the original point to avoid occlusion by the object itself
        val refractOrigin = if (refractDir * surfaceNormal < 0) hit - surfaceNormal*1e-3 else hit + surfaceNormal*1e-3

        castRay(Ray(refractOrigin, refractDir), depth+1)
      }


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

        if (rs.shadows && checkShadow)
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

        val ambient = if (rs.ambient) ambientComp * diffuseComp * diffuseAlbedoComp else 0
        val diffuse = if (rs.diffuse) diffuseInstances.sum * diffuseComp * diffuseAlbedoComp else 0
        val specular = if (rs.specular) specularInstances.sum * specularAlbedoComp else 0
        val reflect = if (rs.reflections != 0) reflectComp * reflectAlbedoComp else 0
        val refract = if (rs.refractions != 0) refractComp * refractAlbedoComp else 0
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
