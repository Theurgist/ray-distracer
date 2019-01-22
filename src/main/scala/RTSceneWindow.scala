import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._

object RTSceneWindow extends JFXApp {
  stage = new PrimaryStage {
    title = "Let's raytrace!"
    scene = new Scene {
      fill = LightGreen
      content = new HBox {
        children = Seq(
          new ImageView {
            fitWidth = 100
            preserveRatio = true

          }

        )
      }
    }
  }
}
