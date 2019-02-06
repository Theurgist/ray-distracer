package cc.theurgist.engine.image

import java.awt.Color
import java.awt.image.BufferedImage

import scalafx.embed.swing.SwingFXUtils
import scalafx.scene.image.WritableImage

/**
  * Wrapped BufferedImage ready for JavaFX
  */
class BitmapImage(val bi: BufferedImage) {

  def this(width: Int, height: Int, background: Option[Color]) = this {
    val bi = new BufferedImage(width,height, BufferedImage.TYPE_3BYTE_BGR)
    background match {
      case Some(clr) =>
        bi.setRGB(0,0, width, height,Array.fill[Int](width*height)(clr.getRGB),0, 0)
      case None =>
    }
    bi
  }

  /**
    * Get image object for JavaFX
    * @return
    */
  def asFxImage: WritableImage = SwingFXUtils.toFXImage(bi, null)
}


object BitmapImage {

  def genBlack(width: Int, height: Int): BitmapImage = {
    new BitmapImage(width, height, Option(new Color(0, 0, 0)))
  }

  def genWhite(width: Int, height: Int): BitmapImage = {
    new BitmapImage(width, height, Option(new Color(255, 255, 255)))
  }

  def genGradient(width: Int, height: Int): BitmapImage = {
    val img = new BitmapImage(width, height, None)
    val h = height.toDouble
    val w = width.toDouble

    (0 until width).foreach(x => (0 until height).foreach(y => {
      val clr = new Color((y / h * 255).toInt, (x / w * 255).toInt , 0)
      img.bi.setRGB(x, y, clr.getRGB)
    }))
    img
  }
}