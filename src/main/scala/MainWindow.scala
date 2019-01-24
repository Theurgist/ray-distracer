import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafxml.core.{FXMLView, NoDependencyResolver}

object MainWindow extends JFXApp {
  println("Application started")
  println

  val fxml: jfxs.Parent = FXMLView(getClass.getResource("mainWindow.fxml"), NoDependencyResolver)

  stage = new PrimaryStage {
    title = "Let's raytrace!"
    scene = new Scene {
      fill = LightGreen
      root = fxml
    }
  }

  println("\uD83D\uDDBA JavaFX scene constructed")
}
