package samples


import engine.materials.Material
import engine.scene.Scene3D
import engine.scene.entities.{Camera, AmbientLight, PointLight}
import engine.scene.objects.Sphere
import engine.scene.primitives.Vec3d


/**
  * Just some spheres
  */
class SomeSpheres {

  private val objects = Iterable(
    Sphere(Vec3d(0,0,-40), 5, SomeSpheresMaterials.mBlue),
    Sphere(Vec3d(3,0,-30), 2, SomeSpheresMaterials.mGreen),

    Sphere(Vec3d(-3,0,-25), 3, SomeSpheresMaterials.mGlass),
    Sphere(Vec3d(-3,3,-30), 2, SomeSpheresMaterials.mGreenMud),

    Sphere(Vec3d(5,7,-30), 4, SomeSpheresMaterials.mDirtyMirror),
    Sphere(Vec3d(-5,8,-30), 3, SomeSpheresMaterials.mDirtyMirror),
    Sphere(Vec3d(0,-8,-20), 5, SomeSpheresMaterials.mMirror),
  )

  private val pointLights = Iterable(
    //PointLight(Vec3d(0, 50, 0), Vec3d(.6, .1, .6)),
    //PointLight(Vec3d(-10, 10, 0), Vec3d(.2, .9, .2)),

    PointLight(Vec3d(10, 40, -20), Vec3d(.3, .5, .3)),
    PointLight(Vec3d(10, 0, 0), Vec3d(.7, .2, .7)),
    PointLight(Vec3d(-3, -1, 0), Vec3d(.3, .3, .3)),
  )

  private val ambientLight = AmbientLight(Vec3d(.3, .3, .3))

  val cam = new Camera(Vec3d(0,0,0), Vec3d(1,0,0), math.Pi/4)
  val scene = new Scene3D(objects, pointLights, ambientLight)
}

private object SomeSpheresMaterials {
  val mBlue = Material(Vec3d(.0, .3, 1.0))
  val mRed = Material(Vec3d(1.0, .3, .0))
  val mGreen = Material(Vec3d(.15, 1.0, .3), 50)
  val mGreenMud = Material(Vec3d(.15, .3, .3))

  val mMateRed = Material(Vec3d(1.0, .3, .0), 10, 1.0, Vec3d(.2, .3, .0))

  val mDirtyMirror = Material(Vec3d(.2, .2, .2), 1000, 1.0, Vec3d(.1, .1, .1), Vec3d.unit, Vec3d.unit)
  val mMirror = Material(Vec3d(.9, .9, .9), 1000, 1.0, Vec3d(.1, .1, .1), Vec3d(.02, .02, .02), Vec3d.unit)
  val mGlass = Material(Vec3d(.9, .9, .9), 1000, 1.5, Vec3d.unit, Vec3d(.05, .05, .05), Vec3d(.1, .1, .1), Vec3d(.9, .9, .9))
  
}
