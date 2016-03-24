package org.mtrupkin.console

import org.mtrupkin.console.RGB._

case class ConsoleChar (c: Char, fg: RGB = White, bg: RGB = Black)

object ConsoleChar {
  implicit def toString(consoleChar: ConsoleChar): String = consoleChar.c.toString
}
