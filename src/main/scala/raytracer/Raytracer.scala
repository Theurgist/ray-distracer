package raytracer

import java.awt.Color

import image.BitmapImage

class Raytracer(width: Int, height: Int) {
  def gen(): BitmapImage = {
    val img = BitmapImage.genBlack(width, height)
    val h = height.toDouble
    val w = width.toDouble


    (0 until width).foreach(x => (0 until height).foreach(y => {
      val clr = new Color((y / h * 255).toInt, (x / w * 255).toInt , 0)
      img.bi.setRGB(x, y, clr.getRGB)
    }))
    img
  }

}
