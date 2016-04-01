package controller

import javafx.fxml.FXML
import javafx.scene.control.{Label, Slider, TextArea}
import javafx.scene.layout.Pane

import model.{World, WorldBuilder}
import org.mtrupkin.control.ConsoleFx
import org.mtrupkin.math.Point
import org.mtrupkin.math.Vect

import scalafx.Includes._
import scalafx.beans.property.DoubleProperty
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyCode._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl}


/**
 * Created by mtrupkin on 12/15/2014.
 */
trait Game { self: Controller =>
  class GameController(var world: World) extends ControllerState {
    val name = "Game"

    @FXML var consolePane: Pane = _

    @FXML var slider1: Slider = _

    val value1 = DoubleProperty(0)
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

      new sfxc.Slider(slider1) {
        value.onChange(updateWorld())
      }

      consolePane.getChildren.clear()
      consolePane.getChildren.add(console)

      consolePane.setFocusTraversable(true)
    }

    protected def updateWorld(): Unit = {
      val persistence = slider1.getValue
      println(s"$persistence")
      world = WorldBuilder.apply(persistence)
    }

    override def update(elapsed: Int): Unit = {
      console.draw(world)
    }

    def handleMouseMove(mouseEvent: sfxi.MouseEvent): Unit = {
      console.updateCursor(mouseEvent.x, mouseEvent.y)
    }

    def handleMouseClicked(event: sfxi.MouseEvent): Unit = {
    }

    def handleMouseExit(event: sfxi.MouseEvent): Unit = {
    }


    def handleKeyPressed(event: sfxi.KeyEvent): Unit = {
      event.consume()
      val code = event.code
      val direction = getDirection(code)
      code match {
        case ESCAPE => exit()
        case _ =>
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


