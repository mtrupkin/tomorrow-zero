package org.mtrupkin.cell

import org.mtrupkin.console.ConsoleChar
import org.mtrupkin.math.Point

/**
  * Created by mtrupkin on 3/23/2016.
  */
trait Cell {
  def move: Boolean
  def char: ConsoleChar
}

trait CellMap {
  def apply(p: Point): Option[Cell]
}