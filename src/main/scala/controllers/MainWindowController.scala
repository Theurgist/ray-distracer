package controllers

import engine.Raytracer
import engine.image.BitmapImage
import javafx.scene.control.Button
import samples.SomeSpheres
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, HBox, Priority}
import scalafxml.core.macros.sfxml

@sfxml
class MainWindowController(
  val btnRender: Button,
  val viewport: AnchorPane
) {
  val scene3d = new SomeSpheres
  //val img: WritableImage = rt.gen(scene.cam).asFxImage
  val img: WritableImage = BitmapImage.genBlack(320, 240).asFxImage


  private def boxWithImage(w: Int, h: Int): ImageView = new ImageView {
    val rt = new Raytracer(w, h, scene3d.scene)
    val img: WritableImage = rt.gen(scene3d.cam).asFxImage

    //new ImageView {
      vgrow = Priority.Always
      hgrow = Priority.Always
      //fitWidth = true
      preserveRatio = true
      image = img
    //}

//    vgrow = Priority.Always
//    hgrow = Priority.Always
//
//    children = Seq(
//      new ImageView {
//        vgrow = Priority.Always
//        hgrow = Priority.Always
//        fitWidth = true
//        preserveRatio = true
//        image = img
//      }
//    )
  }

  def renderClick(event: MouseEvent): Unit = {
    val imgBox = boxWithImage(viewport.getWidth.toInt, viewport.getHeight.toInt)
    imgBox.fitWidth.bind(viewport.width)
    imgBox.fitHeight.bind(viewport.height)
    viewport.children = imgBox
  }
}
