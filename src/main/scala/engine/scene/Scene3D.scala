package engine.scene

import engine.scene.entities.{DiffuseLight, PointLight}
import engine.scene.objects.VisibleObj
import engine.scene.primitives.Ray

/**
  * Renderable scene representation
  * @param objects collection of visible objects
  */
class Scene3D(
    val objects: Iterable[VisibleObj],
    val pointLights: Iterable[PointLight],
    val diffuseLight: DiffuseLight
) {

  /**
    * Calculate all scene objects intersections
    * @param ray casting ray
    * @return collection of intersected objects, sorted by distance (asc)
    */
  def getIntersections(ray: Ray): Iterable[(Double, VisibleObj)] = {
    objects.map(o => (o.intersect(ray), o))
      .filter(_._1.isDefined)
      .map(p =>(p._1.get, p._2))
      .toList
      .sortWith(_._1 < _._1)
  }
}
