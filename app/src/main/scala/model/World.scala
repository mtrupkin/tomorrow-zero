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
    world.size.foreach(p => {
      world(p) = new Cell {
        def move: Boolean = ???
        val char: ConsoleChar = {
          val width: Double = world.size.width
          val height: Double = world.size.height
          val x = ((p.x / width) * 2) - 1
          val y = ((p.y / height) * 2) - 1
          val perlin = Perlin.noise(x, y)
          val noise = ((perlin * 128) + 128).toInt
          println(s"$x $y $perlin $noise")
          ConsoleChar('.', RGB(noise, noise, noise))
          //ConsoleChar('.')
        }
      }
    })
    world
  }
}
