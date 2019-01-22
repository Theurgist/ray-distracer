package primitives

case class Vec3d(x: Double, y: Double, z: Double) {
  def -(that: Vec3d): Vec3d = Vec3d(x - that.x, y - that.y, z - that.z)
  def +(that: Vec3d): Vec3d = Vec3d(x + that.x, y + that.y, z + that.z)

  def *(that: Vec3d): Double = x * that.x + y * that.y + z * that.z
}
