import scala.util.Try
import scala.collection.mutable.StringBuilder

val (integer, plus, minus, space, eof) = ("INTEGER", "PLUS", "MINUS", "SPACE", "EOF")

case class Token(tokenType: String,
                 tokenValue: String) {

  override def toString: String = s"Token($tokenType, $tokenValue)"
}

class Interpreter(val text: String) {

  var pos = 0
  var current_char = text(pos).toString
  while (current_char == " ") advance()
  var current_token: Token = Token(integer, makeInteger())
  var result = current_token.tokenValue.toInt

  def error(): Unit = {
    throw new Exception("Error parsing input")
  }

  def advance(): Unit = {
    pos += 1
    if (pos >= text.length) {
      current_char = ""
    }
    else {
      current_char = text(pos).toString
    }
  }

  def makeInteger(): String = {
    val digit_string = new StringBuilder
    while (current_char != "" && Try(current_char.toInt).isSuccess) {
      digit_string.append(current_char)
      advance()
    }
    digit_string.toString
  }

  def lexer(): Token = {
    if (Try(current_char.toInt).isSuccess) {
      Token(integer, makeInteger())
    }
    else if (current_char == "+") {
      advance()
      Token(plus, "+")
    }
    else if (current_char == "-") {
      advance()
      Token(minus, "-")
    }
    else if (current_char == " ") {
      advance()
      Token(space, " ")
    }
    else Token(eof, "")
  }

  def eat(token_type: String): Unit = {
    if (current_token.tokenType == token_type) {
      current_token = lexer()
      while(current_token.tokenType == space) {
        eat(space)
      }
    }
    else error()
  }

  def parser(): Int = {
    eat(integer)
    while (current_token.tokenType == plus | current_token.tokenType == minus) {
      val opToken = current_token

      if (opToken.tokenType == plus) eat(plus)
      else eat(minus)

      val integerToken = current_token
      eat(integer)

      if (opToken.tokenType == plus) {
        result += integerToken.tokenValue.toInt
      }
      else {
        result -= integerToken.tokenValue.toInt
      }
    }
    result
  }
}
