import scala.util.Try
import scala.collection.mutable.StringBuilder

val (integer, plus, minus, eof) = ("INTEGER", "PLUS", "MINUS", "EOF")

case class Token(tokenType: String,
                 tokenValue: String) {

  override def toString: String = {
    s"Token($tokenType, $tokenValue)"
  }

}

class Interpreter(val text: String) {

  var pos = 0
  var current_char = text(pos).toString
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
    while (current_char != "") {
      if (Try(current_char.toInt).isSuccess) {
        Token(integer, makeInteger())
      }

      else if (Try(current_char == "+").isSuccess) {
        advance()
        Token(plus, "+")
      }

      else if (Try(current_char == "-").isSuccess) {
        advance()
        Token(minus, "-")
      }

      else error()

    }
    Token(eof, "")
  }

  def eat(token_type: String): Unit = {
    if (current_token.tokenType == token_type) {
      current_token = lexer()
    }
    else error()
  }

  def parser(): Int = {
    eat("INTEGER")
    while (current_token.tokenType == "PLUS" || current_token.tokenType == "MINUS") {

      val op = current_token
      if (op.tokenType == "PLUS") {
        eat("PLUS")
      }
      else if (op.tokenType == "MINUS") {
        eat("MINUS")
      }

      val integerToken = current_token
      eat("INTEGER")

      if (op.tokenType == "PLUS") {
        result += integerToken.tokenValue.toInt
      }
      else if (op.tokenType == "MINUS") {
        result -= integerToken.tokenValue.toInt
      }

    }
    result
  }

}