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

    Sphere(Vec3d(-3,0,-15), 2, SomeSpheresMaterials.mMateRed),
    Sphere(Vec3d(-3,3,-30), 2, SomeSpheresMaterials.mGreenMud),
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

  val mMateRed = Material(Vec3d(1.0, .3, .0), 10, Vec3d(.2, .3, .0))
  
}
