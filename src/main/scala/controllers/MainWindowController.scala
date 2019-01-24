package controllers

import engine.Raytracer
import engine.image.BitmapImage
import javafx.scene.control.Button
import samples.SomeSpheres
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, HBox}
import scalafxml.core.macros.sfxml

@sfxml
class MainWindowController(
  val btnRender: Button,
  val viewport: AnchorPane
) {
  val scene3d = new SomeSpheres
  //val img: WritableImage = rt.gen(scene.cam).asFxImage
  val img: WritableImage = BitmapImage.genBlack(320, 240).asFxImage


  private def boxWithImage(w: Int, h: Int): HBox = new HBox {
    val rt = new Raytracer(w, h, scene3d.scene)
    val img: WritableImage = rt.gen(scene3d.cam).asFxImage
    children = Seq(
      new ImageView {
        preserveRatio = true
        image = img
      }
    )
  }

  def renderClick(event: MouseEvent): Unit = {
    viewport.children = boxWithImage(viewport.getWidth.toInt, viewport.getHeight.toInt)
  }
}
