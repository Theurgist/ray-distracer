package engine

case class RenderingSettings(
    rayTracingDepth: Int,
    reflections: Int,
    refractions: Int,

    ambient: Boolean,
    diffuse: Boolean,
    specular: Boolean
)
