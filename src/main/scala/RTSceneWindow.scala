import image.BitmapImage
import raytracer.Raytracer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView, WritableImage}
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._

object RTSceneWindow extends JFXApp {
  val rt = new Raytracer(640, 480)
  val img: WritableImage = rt.gen().asFxImage

  stage = new PrimaryStage {
    title = "Let's raytrace!"
    scene = new Scene {
      fill = LightGreen
      content = new HBox {
        children = Seq(
          new ImageView {
            preserveRatio = true
            image = img
          }
        )
      }
    }
  }
}
