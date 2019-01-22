import image.BitmapImage
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._

object RTSceneWindow extends JFXApp {
  val img = BitmapImage.createWhiteCanvas(640, 480).asFxImage

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
