package controller

import javafx.fxml.FXML
import javafx.scene.control.{Label, TextArea}
import javafx.scene.layout.Pane

import control.ConsoleFx
import model.World
import org.mtrupkin.math.Point
import org.mtrupkin.math.Vect

import scalafx.Includes._
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyCode._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 12/15/2014.
 */
trait Game { self: Controller =>
  class GameController(world: World) extends ControllerState {
    val name = "Game"

    @FXML var consolePane: Pane = _

    val console = new ConsoleFx()

    def initialize(): Unit = {
      new sfxl.Pane(consolePane) {
        filterEvent(sfxi.KeyEvent.KeyPressed) {
          (event: sfxi.KeyEvent) => handleKeyPressed(event)
        }
      }

      new sfxl.Pane(console) {
        onMouseClicked = (e: sfxi.MouseEvent) => handleMouseClicked(e)
        onMouseMoved = (e: sfxi.MouseEvent) => handleMouseMove(e)
        onMouseExited = (e: sfxi.MouseEvent) => handleMouseExit(e)
      }

      consolePane.getChildren.clear()
      consolePane.getChildren.add(console)

      consolePane.setFocusTraversable(true)
    }

    override def update(elapsed: Int): Unit = {
      console.draw(world)
    }

    def handleMouseMove(event: sfxi.MouseEvent): Unit = {
      for (p <- mouseToPoint(event)) {
        console.cursor = Some(p)
      }
    }

    def handleMouseClicked(event: sfxi.MouseEvent): Unit = {
    }

    def handleMouseExit(event: sfxi.MouseEvent): Unit = {
    }

    def mouseToPoint(mouseEvent: sfxi.MouseEvent): Option[Point] = console.pixelToCell(mouseEvent.x, mouseEvent.y)

    def handleKeyPressed(event: sfxi.KeyEvent): Unit = {
      event.consume()
      val code = event.code
      val direction = getDirection(code)
      code match {
        case ESCAPE => exit()
        case _ => ???
      }
    }

    def getDirection(code: KeyCode): Option[Vect] = {
      import KeyCode._
      code match {
        case W | UP | NUMPAD8 => Option(Vect.Up)
        case S | DOWN | NUMPAD2 => Option(Vect.Down)
        case A | LEFT | NUMPAD4 => Option(Vect.Left)
        case D | RIGHT | NUMPAD6 => Option(Vect.Right)
        case NUMPAD1 | END => Option(Vect.LowerLeft)
        case NUMPAD3 | PAGE_DOWN => Option(Vect.LowerRight)
        case NUMPAD7 | HOME => Option(Vect.UpperLeft)
        case NUMPAD9 | PAGE_UP => Option(Vect.UpperRight)
        case _ => None
      }
    }
  }
}


