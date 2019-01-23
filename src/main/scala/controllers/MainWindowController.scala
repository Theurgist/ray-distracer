package controllers

import engine.Raytracer
import engine.image.BitmapImage
import javafx.fxml.FXML
import javafx.scene.control.Button
import samples.SomeSpheres
import scalafx.event.ActionEvent
import scalafx.scene.control.ScrollPane
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, HBox}
import scalafxml.core.macros.sfxml

@sfxml
class MainWindowController(
  val btnRender: Button,
  val workspace: ScrollPane
) {
  val scene3d = new SomeSpheres
  //val img: WritableImage = rt.gen(scene.cam).asFxImage
  val img: WritableImage = BitmapImage.genBlack(320, 240).asFxImage


  private def boxWithImage(w: Int, h: Int): HBox = new HBox {
    val rt = new Raytracer(w, h, scene3d.scene)
    val img: WritableImage = rt.gen(scene3d.cam).asFxImage
    children = Seq(
      new ImageView {
        //preserveRatio = true
        image = img
      }
    )
  }

  def renderClick(event: MouseEvent): Unit = {
    println("CLICK, BEATCH! " + event)
    workspace.content = boxWithImage(workspace.getWidth.toInt, workspace.getHeight.toInt)
  }
}
