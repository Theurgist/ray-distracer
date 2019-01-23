package samples

import engine.materials.Material
import engine.scene.Scene3D
import engine.scene.entities.{AmbientLight, Camera, PointLight}
import engine.scene.objects.{Sphere, Trigon}
import engine.scene.primitives.Vec3d


/**
  * Just Sphere And Wall
  */
class SphereAndWall {

  private val objects = Iterable(

    Sphere(Vec3d(5,7,-30), 4, SphereAndWall.mRed),
    Sphere(Vec3d(-5,8,-30), 4, SphereAndWall.mGreen),

    // Back wall
    Trigon(Array(Vec3d(-50, -10, -35), Vec3d(50, 10, -35),   Vec3d(-50,  10, -35)), SphereAndWall.mDirtyMirror),
    Trigon(Array(Vec3d(-50, -10, -35), Vec3d(50, -10, -35),  Vec3d( 50, 10, -35)), SphereAndWall.mDirtyMirror),
  )

  private val pointLights = Iterable(
    PointLight(Vec3d(10, 40, -20), Vec3d(.3, .5, .3)),
    PointLight(Vec3d(10, 0, 0), Vec3d(.7, .2, .7)),
    PointLight(Vec3d(-3, -1, 0), Vec3d(.3, .3, .3)),
  )

  private val ambientLight = AmbientLight(Vec3d(.3, .3, .3))

  val cam = new Camera(Vec3d(0,0,0), Vec3d(1,0,0), math.Pi/4)
  val scene = new Scene3D(objects, pointLights, ambientLight)
}

private object SphereAndWall {
  val mBlue = Material(Vec3d(.0, .3, 1.0))
  val mRed = Material(Vec3d(1.0, .3, .0))
  val mGreen = Material(Vec3d(.15, 1.0, .3), 50)
  val mGreenMud = Material(Vec3d(.15, .3, .3))

  val mMateRed = Material(Vec3d(1.0, .3, .0), 10, 1.0, Vec3d(.2, .3, .0))

  val mDirtyMirror = Material(Vec3d(.2, .2, .2), 1000, 1.0, Vec3d(.1, .1, .1), Vec3d.unit, Vec3d.unit)
  val mMirror = Material(Vec3d(.9, .9, .9), 1000, 1.0, Vec3d(.1, .1, .1), Vec3d(.02, .02, .02), Vec3d.unit)
  val mGlass = Material(Vec3d(.9, .9, .9), 1000, 1.5, Vec3d.unit, Vec3d(.05, .05, .05), Vec3d(.1, .1, .1), Vec3d(.9, .9, .9))
  
}
