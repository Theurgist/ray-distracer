import engine.Raytracer
import engine.image.BitmapImage
import javafx.scene.layout
import samples.{SomeSpheres, SphereAndWall}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Node, Scene}
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.layout._
import scalafx.scene.paint.Color._

import scala.collection.JavaConverters._

object RTSceneWindow extends JFXApp {
  println("Application started")
  println

  val scene = new SomeSpheres

  //val rt = new Raytracer(320, 240, scene.scene)
  //val rt = new Raytracer(640, 480, scene.scene)
  val rt = new Raytracer(1280, 960, scene.scene)
  val img: WritableImage = rt.gen(scene.cam).asFxImage
  //val img: WritableImage = BitmapImage.genBlack(320, 240).asFxImage


  val grid: GridPane = new GridPane {
    columnConstraints = Seq(
      new ColumnConstraints {
        percentWidth = 10
        fillWidth = true
      },
      new ColumnConstraints {
        percentWidth = 2
        fillWidth = true
      },
      new ColumnConstraints {
        percentWidth = 88
        fillWidth = true
      },
    )

    rowConstraints = Seq(
      new RowConstraints {
        vgrow = Priority.Always
      }
    )
    gridLinesVisible = true

  }

  //grid.addRow(0, boxWithImage)
  val regs: Seq[layout.Region] = Seq("red", "green", "blue").map(s => {
    val r = new javafx.scene.layout.Region()
    r.setStyle("-fx-background-color:"+s)
    grid.addRow(0, r)
    r
  })

  stage = new PrimaryStage {
    title = "Let's raytrace!"
    scene = new Scene {
      fill = LightGreen
      content = boxWithImage
      //content = grid
    }
  }

  private def boxWithImage: HBox = new HBox {
    children = Seq(
      new ImageView {
        //preserveRatio = true
        image = img
      }
    )
  }

  println("\uD83D\uDDBA JavaFX scene constructed")
}
