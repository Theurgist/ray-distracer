package samples


import engine.materials.Material
import engine.scene.Scene3D
import engine.scene.entities.{Camera, DiffuseLight, PointLight}
import engine.scene.objects.Sphere
import engine.scene.primitives.Vec3d


/**
  * Just some spheres
  */
class SomeSpheres {

  private val objects = Iterable(
    Sphere(Vec3d(0,0,-40), 5, SomeSpheresMaterials.mBlue),

    Sphere(Vec3d(-3,0,-15), 2, SomeSpheresMaterials.mRed),
    Sphere(Vec3d(3,0,-30), 2, SomeSpheresMaterials.mGreen),
    Sphere(Vec3d(-3,3,-30), 2, SomeSpheresMaterials.mGreenMud),
  )

  private val pointLights = Iterable(
    PointLight(Vec3d(0, 50, 0), 1.0),
    PointLight(Vec3d(-30, 10, 0), 0.3),
  )

  private val diffuseLight = DiffuseLight(Vec3d(0.02, 0.1, 0.02))

  val cam = new Camera(Vec3d(0,0,0), Vec3d(1,0,0), math.Pi/2)
  val scene = new Scene3D(objects, pointLights, diffuseLight)
}

private object SomeSpheresMaterials {
  val mBlue = Material(Vec3d(0, 0.3, 1.0))
  val mRed = Material(Vec3d(1.0, 0.3, 0))
  val mGreen = Material(Vec3d(0.15, 1.0, 0.3))
  val mGreenMud = Material(Vec3d(0.15, 0.3, 0.3))
  
}
