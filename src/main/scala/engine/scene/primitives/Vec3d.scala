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

  def unary_-(): Vec3d = Vec3d(-x, -y, -z, len)

  /**
    * Dot product
    */
  def *(that: Vec3d): Double = x * that.x + y * that.y + z * that.z
  def *(scalar: Double): Vec3d = Vec3d(x * scalar, y * scalar, z * scalar)

  /**
    * Cross product
    */
  def ^(that: Vec3d): Vec3d = Vec3d(
    y * that.z - z * that.y,
    z * that.x - x * that.z,
    x * that.y - y * that.x
  )


  def normalized: Vec3d = Vec3d.normalized(x, y, z)

  /**
    * Per-component multiplication
    */
  def ##(that: Vec3d): Vec3d = Vec3d(x * that.x, y * that.y, z * that.z)

  /**
    * Convert to integer color value
    */
  def asColorInt: Int = asColorInt(1.0)
  /**
    * Convert to integer color value
    */
  def asColorInt(a: Double): Int = {
    ((cnv(a) & 0xFF) << 24) | ((cnv(x) & 0xFF) << 16) | ((cnv(y) & 0xFF) << 8) | ((cnv(z) & 0xFF) << 0)
  }

  /**
    * Map [0.0, 1.0] vector component value to [0-255]
    * @param v normalized value to convert
    * @return
    */
  private def cnv(v: Double): Int = (math.min(math.max(0.0, v), 1.0) * 255).toInt
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

  /**
    * Zero constant vector
    */
  val zero: Vec3d = Vec3d(0,0,0,0)
  /**
    * Unit constant vector
    */
  val unit: Vec3d = Vec3d(1,1,1)
}