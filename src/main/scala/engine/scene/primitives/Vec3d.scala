package engine.scene.primitives

/**
  * Basic three-dimensional vector
  * @param x abscissa
  * @param y ordinate
  * @param z applicate
  * @param len vector length
  */
case class Vec3d private(x: Double, y: Double, z: Double, len: Double) {

  def -(that: Vec3d): Vec3d = Vec3d(x - that.x, y - that.y, z - that.z)
  def +(that: Vec3d): Vec3d = Vec3d(x + that.x, y + that.y, z + that.z)

  def *(that: Vec3d): Double = x * that.x + y * that.y + z * that.z
}

object Vec3d {

  /**
    * Create basic vector
    */
  def apply(x: Double, y: Double, z: Double): Vec3d = Vec3d(x,y,z, calcLen(x,y,z))

  /**
    * Create basic vector and normalize it
    */
  def normalized(x: Double, y: Double, z: Double): Vec3d = {
    val l = calcLen(x,y,z)
    Vec3d(x/l, y/l, z/l, 1.0)
  }

  private def calcLen(x: Double, y: Double, z: Double): Double = math.sqrt(x*x + y*y + z*z)
}