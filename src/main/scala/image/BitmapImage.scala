package image

import java.awt.Color
import java.awt.image.BufferedImage

import scalafx.embed.swing.SwingFXUtils
import scalafx.scene.image.WritableImage

/**
  * Wrapped BufferedImage ready for JavaFX
  * @param width
  * @param height
  * @param background
  */
class BitmapImage(width: Int, height: Int, background: Color) {

  val bi: BufferedImage = {
    val bi = new BufferedImage(width,height, BufferedImage.TYPE_3BYTE_BGR)
    bi.setRGB(
      0,0, width, height,
      Array.fill[Int](width*height)(background.getRGB),
      0, 0)
    bi
  }

  def asFxImage: WritableImage = SwingFXUtils.toFXImage(bi, null)
}


object BitmapImage {
  def createBlackCanvas(width: Int, height: Int): BitmapImage = {
    new BitmapImage(width, height, new Color(0, 0, 0))
  }

  def createWhiteCanvas(width: Int, height: Int): BitmapImage = {
    new BitmapImage(width, height, new Color(255, 255, 255))
  }
}