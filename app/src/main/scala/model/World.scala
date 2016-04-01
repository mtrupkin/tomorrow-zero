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

  def apply(persistence: Double): World = {
    val world = new World
    var sum = 0
    var count = 0
    world.size.foreach(p => {
      world(p) = new Cell {
        def move: Boolean = ???
        val char: ConsoleChar = {
          val width: Double = world.size.width
          val height: Double = world.size.height
          val x = (p.x / width) * 1
          val y = (p.y / height) * 1
          val perlin = Perlin.fractalSum(x, y)//, f = Math.abs)
          val noise = ( perlin + 1 ) / 2
//          val noise = (perlin.noise(x, y) + 1)/2
          //val color = if (noise > 0.25) (noise * 256).toInt  else 0
          val color = (noise * 255).toInt
          sum += color
          count += 1
//          println(s"$x $y $noise $color")
          ConsoleChar(' ', RGB.Black, RGB(color, color, color))
        }
      }
    })
    println(s"avg: ${sum/count}")
    world
  }
}
