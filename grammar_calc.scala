import scala.util.Try
import scala.collection.mutable.StringBuilder

val (integer, plus, minus, multiply, divide, lparen, rparen, space, eof) =
  ("INTEGER", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "LPAREN", "RPAREN", "SPACE", "EOF")

case class Token(tokenType: String,
                 tokenValue: String) {

  override def toString: String = s"Token($tokenType, $tokenValue)"
}

class Lexer(val text: String) {
  var pos = 0
  var current_char = text(pos).toString

  def error(): Unit = {
    throw new Exception("Invalid character")
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

  def get_next_token(): Token = {
    while (current_char == " ") advance()

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
    else if (current_char == "*") {
      advance()
      Token(multiply, "*")
    }
    else if (current_char == "/") {
      advance()
      Token(divide, "/")
    }
    else if (current_char == "(") {
      advance()
      Token(lparen, "(")
    }
    else if (current_char == ")") {
      advance()
      Token(rparen, ")")
    }
    else Token(eof, "")
  }
}

class Interpreter(val lexer: Lexer) {
  var current_token: Token = lexer.get_next_token()

  def error(): Unit = {
    throw new Exception("Invalid syntax")
  }

  def eat(token_type: String): Unit = {
    if (current_token.tokenType == token_type) {
      current_token = lexer.get_next_token()
    }
    else error()
  }

  def factor(): Int = {
    val token = current_token
    if (token.tokenType == lparen) {
      eat(lparen)
      val result = expr()
      eat(rparen)
      result
    }
    else {
      eat(integer)
      token.tokenValue.toInt
    }
  }

  def term(): Int = {
    var result = factor()
    while (current_token.tokenType == multiply | current_token.tokenType == divide) {
      val token = current_token
      if (token.tokenType == multiply) {
        eat(multiply)
        result *= factor()
      }
      else {
        eat(divide)
        result /= factor()
      }
    }
    result
  }

  def expr(): Int = {
    var result = term()
    while (current_token.tokenType == plus | current_token.tokenType == minus) {
      val token = current_token
      if (token.tokenType == plus) {
        eat(plus)
        result += term()
      }
      else {
        eat(minus)
        result -= term
      }
    }
    result
  }
}