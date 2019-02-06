package cc.theurgist.engine

case class RenderingSettings(
    rayTracingDepth: Int,
    reflections: Int,
    refractions: Int,

    ambient: Boolean,
    diffuse: Boolean,
    specular: Boolean,
    shadows: Boolean,

    buffered: Boolean
) {
  override def toString: String =
    s"Depth [rt: $rayTracingDepth rl: $reflections rr: $refractions] " +
    s"Lighting [a: $ambient d: $diffuse s: $specular] " +
    s"Mem [buffered: $buffered"
}
