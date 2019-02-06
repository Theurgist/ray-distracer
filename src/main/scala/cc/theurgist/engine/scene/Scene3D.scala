package cc.theurgist.engine.scene

import cc.theurgist.engine.scene.entities.{AmbientLight, PointLight}
import cc.theurgist.engine.scene.objects.VisibleObj
import cc.theurgist.engine.scene.primitives.Ray

/**
  * Renderable scene representation
  * @param objects collection of visible objects
  */
class Scene3D(
    val objects: Iterable[VisibleObj],
    val pointLights: Iterable[PointLight],
    val ambientLight: AmbientLight
) {

  /**
    * Calculate all scene objects intersections
    * @param ray casting ray
    * @return collection of (intersectionDistance, intersected object), sorted by distance (asc)
    */
  def getIntersections(ray: Ray): Iterable[(Double, VisibleObj)] = {
    objects.map(o => (o.intersect(ray), o))
      .filter(_._1.isDefined)
      .map(p =>(p._1.get, p._2))
      .toList
      .sortWith(_._1 < _._1)
  }
}
