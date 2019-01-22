package engine

import engine.image.BitmapImage
import engine.scene.Scene3D
import engine.scene.entities.Camera
import engine.scene.primitives.{Ray, Vec3d}

/**
  * Raytracing engine
  * @param width canvas width
  * @param height canvas height
  * @param scene renderable scene
  */
class Raytracer(width: Int, height: Int, scene: Scene3D) {

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
      val color: Int = castRay(ray)
      //framebuffer[i+j*width] = color
      img.bi.setRGB(i, j, color)
    }))


    println("✓ Finish raytracing")
    img
  }

  private def castRay(ray: Ray): Int = {
    val intersected = scene.getIntersections(ray)
    if (intersected.isEmpty)
      0
    else {
      //intersected.map(vo => {

      val vo = intersected.head
      val hit = ray.start + ray.dir * vo._1
      val norm = (hit - vo._2.center).normalized
      val material = vo._2.material

      val accumulatedDiffuseIntensity = scene.pointLights.map(pl => {
        val lightDir = (pl.pos - hit).normalized
        val diffuseIntensity = pl.intensity * math.max(0.0, lightDir*norm)
        diffuseIntensity
      }).sum

      val pixelColor = scene.diffuseLight.clr ## material.clr + material.clr * accumulatedDiffuseIntensity
      pixelColor.asColorInt

      //})

    }
  }

}
