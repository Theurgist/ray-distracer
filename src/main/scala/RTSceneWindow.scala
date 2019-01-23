import engine.Raytracer
import samples.{SomeSpheres, SphereAndWall}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._

object RTSceneWindow extends JFXApp {
  println("Application started")
  println

  val scene = new SomeSpheres

  //val rt = new Raytracer(640, 480, scene.scene)
  val rt = new Raytracer(1280, 960, scene.scene)
  val img: WritableImage = rt.gen(scene.cam).asFxImage

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

  println("\uD83D\uDDBA JavaFX scene constructed")
}
