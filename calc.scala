import scala.util.Try
import scala.collection.mutable.StringBuilder

val (integer, plus, minus, eof) = ("INTEGER", "PLUS", "MINUS", "eof")

case class Token(tokenType: String,
                 tokenValue: Option[Any]) {

  override def toString: String = {
    s"Token($tokenType, $tokenValue)"
  }

}

class Interpreter(val text: String) {

  var pos = 0
  val current_token: Option[Token] = None
  var current_char = text(pos).toString

  def advance(): Unit = {
    pos += 1
    if (pos >= text.length) {
      current_char = ""
    }
    else {
      current_char = text(pos).toString
    }
  }

  def makeInteger(): Int = {
    val digit_string = new StringBuilder
    while (current_char != "" && Try(current_char.toInt).isSuccess) {
      digit_string.append(current_char)
      advance()
    }
    digit_string.toInt
  }

  def lexer: Token = {

    if (Try(current_char.toInt).isSuccess) {
      Token(integer, Some(makeInteger()))
    }
    else if (Try(current_char == "+").isSuccess) {
      advance()
      Token(plus, Some("+"))
    }
    else if (Try(current_char == "-").isSuccess) {
      advance()
      Token(minus, Some("-"))
    }
    else Token(eof, None)
  }

}