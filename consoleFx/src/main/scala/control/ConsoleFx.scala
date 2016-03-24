package org.mtrupkin.control

import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.Font

import org.mtrupkin.cell.CellMap
import org.mtrupkin.console.{ConsoleChar, RGB}
import org.mtrupkin.math.Size
import org.mtrupkin.math.{Matrix, Point}

/**
  * Created by mtrupkin on 3/22/2016.
  */
class ConsoleFx(val size: Size = Size(40, 20)) extends Pane {
  val fontSize: Int = 29
  val is = getClass.getResourceAsStream("/fonts/RobotoMono-Regular.ttf")
  val font = Font.loadFont(getClass.getResource("/fonts/RobotoMono-Regular.ttf").toExternalForm, fontSize)
  val cellSize = ConsoleFx.charBounds(font)

  val labels = new Matrix[Label](size)
  var cursor: Option[Point] = None

  val (sizeX, sizeY) = cellToPixel(Point(size.width, size.height))
  setMinSize(sizeX, sizeY)

  size.foreach(p => {
    val l = new Label()
    l.setTextFill(Color.WHITE)
    l.setFont(font)
    //l.setStyle("-fx-border-color: blue;")
    labels(p) = l

    val (px, py) = cellToPixel(p)
    l.relocate(px, py)
    getChildren.add(l)
  })

  def draw(view: CellMap): Unit = {
    def toColor(rgb: RGB): Color = Color.rgb(rgb.r, rgb.g, rgb.b)

    def toHex(rgb: RGB): String = {
      def toHex(x: Int): String = "%02X".format(x)

      val r0 = toHex(rgb.r)
      val g0 = toHex(rgb.g)
      val b0 = toHex(rgb.b)

      s"#$r0$g0$b0"
    }

    def draw(l: Label, c: ConsoleChar): Unit = {
      l.setTextFill(toColor(c.fg))
      l.setStyle(s"-fx-background-color: ${toHex(c.bg)};")
      l.setText(c)
    }

    size.foreach(p => {
      view(p).map(cell => draw(labels(p), cell.char))
    })
  }



  def updateCursor(x: Double, y: Double): Unit = {
    cursor = pixelToCell(x, y)
  }

  private def pixelToCell(x: Double, y: Double): Option[Point] = {
    def floor(d: Double): Int = { Math.floor(d).toInt }

    val (width, height) = cellSize
    val c = Point(floor(x / width), floor(y / height))
    if (size.in(c)) Some(c) else None
  }

  private def cellToPixel(p: Point): (Double, Double) = {
    val (width, height) = cellSize
    val (borderWidth, borderHeight) = (2, 2)

    (p.x * width + borderWidth, p.y * height + borderHeight)
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
