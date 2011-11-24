package com.github.hexx

import scala.swing._
import scala.swing.event._

import javax.swing.SwingUtilities
import java.awt.Toolkit

object TimeType extends Enumeration {
  val Work = Value("作業")
  val Break = Value("休憩")
}

class PomodoroFrame(val timeType: TimeType.Value, time: Int) extends MainFrame {
  title = timeType + "時間(Pomodoro Timer)"
  val okButton = new Button {
    text = "OK"
  }
  val cancelButton = new Button {
    text = "Cancel"
  }
  val textField = new TextField(time.toString, 5) {
    horizontalAlignment = Alignment.Right
  }
  contents = new BoxPanel(Orientation.Vertical) {
    contents += new Label(timeType + "時間です")
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += textField
      contents += new Label("分")
    }
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += okButton
      contents += cancelButton
    }
  }
  listenTo(textField.keys, okButton, okButton.keys, cancelButton, cancelButton.keys)
  resizable = false
  centerOnScreen
}

object PomodoroTimer extends SwingApplication {
  val workWindow = new PomodoroFrame(TimeType.Work, 25)
  val breakWindow = new PomodoroFrame(TimeType.Break, 5)
  val reactor: Reactions.Reaction = {
    case ButtonClicked(button) if button == currentWindow.okButton => toggleWindow
    case ButtonClicked(_) => quit
    case KeyPressed(component, Key.Enter, _, _) if component == currentWindow.cancelButton => quit
    case KeyPressed(_, Key.Enter, _, _) => toggleWindow
    case KeyPressed(_, Key.Escape, _, _) => quit
  }
  workWindow.reactions += reactor
  breakWindow.reactions += reactor
  var currentWindow = workWindow
  var nextWindow = breakWindow
  def startup(args: Array[String]) {
    currentWindow.visible = true
  }
  def toggleWindow {
    val tempWindow = nextWindow
    currentWindow.visible = false
    val sleepTime = currentWindow.textField.text.toInt
    println(currentWindow.timeType + "時間: " + sleepTime + "分")
    Thread.sleep(1000 * 60 * sleepTime)
    nextWindow = currentWindow
    currentWindow = tempWindow
    currentWindow.visible = true
  }
}

class PomodoroTimerApp extends xsbti.AppMain {
  def run(config: xsbti.AppConfiguration) = {
    PomodoroTimer.main(config.arguments)
    Thread.currentThread.join
    Exit(0)
  }
}

case class Exit(val code: Int) extends xsbti.Exit
