package app

import com.typesafe.scalalogging.StrictLogging
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafxml.core.{FXMLView, NoDependencyResolver}


object Client extends JFXApp with StrictLogging {
  Thread.currentThread().setName("JFX")
  logger.info("Application started")

  val fxml: jfxs.Parent = FXMLView(getClass.getResource("/jfxui/mainWindow.fxml"), NoDependencyResolver)

  stage = new PrimaryStage {
    title = "Let's raytrace!"

    minWidth = 400
    minHeight = 300

    scene = new Scene {
      fill = LightGreen
      root = fxml
    }
  }

  logger.info("JavaFX scene constructed")
}
