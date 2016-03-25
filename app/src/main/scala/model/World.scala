package model

import org.mtrupkin.cell.{Cell, CellMap}
import org.mtrupkin.console.{ConsoleChar, RGB}
import org.mtrupkin.math.{Matrix, Point, Size}
import org.mtrupkin.random.Perlin

/**
  * Created by mtrupkin on 3/22/2016.
  */
class World extends CellMap {
  val size = Size(40, 20)
  val cells = new Matrix[Cell](size)

  def apply(p: Point): Option[Cell] = if(size.in(p)) Some(cells(p)) else None
  def update(p: Point, c: Cell): Unit = { cells(p) = c }
}

object WorldBuilder {
  def apply(): World = {
    val world = new World
    val perlin = Perlin(1)
    world.size.foreach(p => {
      world(p) = new Cell {
        def move: Boolean = ???
        val char: ConsoleChar = {
          val width: Double = world.size.width
          val height: Double = world.size.height
          val x = p.x / width
          val y = p.y / height
          val noise = perlin.noise(x, y)
          val color = (noise * 256).toInt
          println(s"$x $y $noise $color")
          ConsoleChar(' ', RGB.Black, RGB(color, color, color))
          //ConsoleChar('.')
        }
      }
    })
    world
  }
}
