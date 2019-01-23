import engine.Raytracer
import engine.image.BitmapImage
import javafx.fxml.FXMLLoader
import javafx.scene.layout
import samples.SomeSpheres
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafxml.core.{FXMLView, NoDependencyResolver}

object MainWindow extends JFXApp {
  println("Application started")
  println



  import scalafx.Includes._
  import javafx.{scene => jfxs}
  val fxml: jfxs.Parent = FXMLView(getClass.getResource("mainWindow.fxml"), NoDependencyResolver)

  //val pane = fxml.lookup("#Content")
  //pane.asInstanceOf[Pane].children.add(boxWithImage)

  stage = new PrimaryStage {
    title = "Let's raytrace!"
    scene = new Scene {
      fill = LightGreen
      //getChildren.add(fxml)
      content = fxml
      //content = grid
    }
  }

  println("\uD83D\uDDBA JavaFX scene constructed")
}
