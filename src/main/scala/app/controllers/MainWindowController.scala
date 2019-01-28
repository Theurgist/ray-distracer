package app.controllers

import app.samples.SomeSpheres
import engine.{Raytracer, RenderingSettings}
import engine.image.BitmapImage
import scalafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory
import scalafx.scene.control.{Button, CheckBox, Spinner, SpinnerValueFactory}
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, Priority}
import scalafxml.core.macros.sfxml

@sfxml
class MainWindowController(
  val btnRender: Button,
  val viewport: AnchorPane,
  val recursionDepth: Spinner[Int],

  val bAmbient: CheckBox,
  val bDiffuse: CheckBox,
  val bSpecular: CheckBox,
  val bShadows: CheckBox
) {
  val scene3d = new SomeSpheres
  //val img: WritableImage = rt.gen(scene.cam).asFxImage
  val img: WritableImage = BitmapImage.genBlack(320, 240).asFxImage

  val jvf = new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,1)
  recursionDepth.valueFactory = new IntegerSpinnerValueFactory(jvf).asInstanceOf[SpinnerValueFactory[Int]]
  recursionDepth.setOnScroll(event => {
    if (event.getDeltaY < 0)
      recursionDepth.decrement()
    else if (event.getDeltaY > 0)
      recursionDepth.increment()
  })

  private def boxWithImage(img: WritableImage): ImageView = new ImageView {
    vgrow = Priority.Always
    hgrow = Priority.Always
    preserveRatio = true
    image = img
  }

  def renderClick(event: MouseEvent): Unit = {



    val rs = RenderingSettings(
      recursionDepth.getValue,5,5,

      bAmbient.selected.value,
      bDiffuse.selected.value,
      bSpecular.selected.value,
      bShadows.selected.value,

      buffered = false,
    )

    val rt = new Raytracer(viewport.getWidth.toInt, viewport.getHeight.toInt, scene3d.scene, rs)
    val img: WritableImage = rt.gen(scene3d.cam).asFxImage

    val imgBox = boxWithImage(img)
    imgBox.fitWidth.bind(viewport.width)
    imgBox.fitHeight.bind(viewport.height)
    viewport.children = imgBox
  }
}
