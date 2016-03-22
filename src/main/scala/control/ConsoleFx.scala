package control

import javafx.scene.layout.Pane
import javafx.scene.text.Font

import model.World

import org.mtrupkin.math.Size
import org.mtrupkin.math.Point

/**
  * Created by mtrupkin on 3/22/2016.
  */
class ConsoleFx(val size: Size = Size(40, 20)) extends Pane {
  val fontSize: Int = 35 // 32
  val is = getClass.getResourceAsStream("/fonts/RobotoMono-Regular.ttf")
  val font = Font.loadFont(getClass.getResource("/fonts/RobotoMono-Regular.ttf").toExternalForm, fontSize)
  val cellSize = ConsoleFx.charBounds(font)

  var cursor: Option[Point] = None

  def draw(world: World): Unit = {}

  def pixelToCell(x: Double, y: Double): Option[Point] = {
    def floor(d: Double): Int = { Math.floor(d).toInt }

    val (width, height) = cellSize
    val c = Point(floor(x / width), floor(y / height))
    if (size.in(c)) Some(c) else None
  }
}

object ConsoleFx {
  def charBounds(f: Font): (Double, Double) = {
    val fl = com.sun.javafx.tk.Toolkit.getToolkit.getFontLoader

    val fontWidth = fl.computeStringWidth(" ", f)
    val metrics = fl.getFontMetrics(f)

    (Math.floor(fontWidth + 2), Math.floor(metrics.getLineHeight + 3))
  }
}
