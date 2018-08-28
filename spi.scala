import scala.collection.mutable
import scala.util.Try

val integer_const = "INTEGER_CONST"
val real_const = "REAL_CONST"
val plus = "PLUS"
val minus = "MINUS"
val multiply = "MULTIPLY"
val div = "DIV"
val lparen = "LPAREN"
val rparen = "RPAREN"
val program = "PROGRAM"
val variable = "VARIABLE"
val colon = "COLON"
val comma = "COMMA"
val procedure = "PROCEDURE"
val integer = "INTEGER"
val real = "REAL"
val id = "ID"
val assign = "ASSIGN"
val begin = "BEGIN"
val end = "END"
val semi = "SEMI"
val dot = "DOT"
val space = "SPACE"
val eof = "EOF"

case class Token(tokenType: String,
                 tokenValue: String) {
  override def toString: String = s"Token($tokenType, $tokenValue)"
}

val reserved_keywords = Map(
  "PROGRAM" -> Token(program, "PROGRAM"),
  "PROCEDURE" -> Token(procedure, "PROCEDURE"),
  "VARIABLE" -> Token(variable, "VARIABLE"),
  "INTEGER" -> Token(integer, "INTEGER"),
  "REAL" -> Token(real, "REAL"),
  "BEGIN" -> Token(begin, "BEGIN"),
  "END" -> Token(end, "END"),
  "DIV" -> Token(div, "/")
)

class Lexer(val text: String) {
  var pos = 0
  var current_char = text(pos).toString

  def error(): Unit = {
    throw new Exception("Invalid character")
  }

  def peek(): String = {
    val peek_pos = pos + 1
    if (peek_pos >= text.length) {
      ""
    }
    else {
      text(pos).toString
    }
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

  def skip_whitespace(): Unit = {
    while (current_char == " ") advance()
  }

  def skip_comment(): Unit = {
    while (current_char != "}") advance()
    advance()
  }

  def number(): Token = {
    val digit_string = new mutable.StringBuilder()
    while (current_char != "" && Try(current_char.toInt).isSuccess) {
      digit_string.append(current_char)
      advance()
    }
    if (current_char == ".") {
      digit_string.append(current_char)
      advance()

      while (current_char != "" && Try(current_char.toInt).isSuccess) {
        digit_string.append(current_char)
        advance()
      }

      Token(real_const, digit_string.toString)
    }
    else {
      Token(integer_const, digit_string.toString)
    }
  }

  def _id(): Token = {
    val id_string = new mutable.StringBuilder()
    if (current_char == "_") {
      id_string.append("_")
      advance()
    }
    while (current_char.matches("[a-zA-Z0-9]")) {
      id_string.append(current_char)
      advance()
    }
    reserved_keywords.getOrElse(id_string.toString.toUpperCase, Token(id, id_string.toString.toLowerCase))
  }

  def get_next_token(): Token = {
    if (current_char == " ") skip_whitespace()

    if (current_char == "{") {
      skip_comment()
      skip_whitespace()
    }

    if (current_char.matches("[a-zA-Z]")) _id()
    else if (Try(current_char.toInt).isSuccess) number()
    else if (current_char == ":") {
      if (peek() == "=") {
        advance()
        advance()
        Token(assign, ":=")
      }
      else {
        advance()
        Token(colon, ":")
      }
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
      Token(div, "/")
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

trait AST

class VarDecl (val var_node: Var, val type_node: Type) extends AST
class Var (val token: Token) extends AST {
  val value = token.tokenValue
}
class Type(val token: Token) extends AST {
  val value = token.tokenValue
}
class UnaryOp(val token: Token, val expr: AST) extends AST
class BinOp(val left: AST, val token: Token, val right: AST) extends AST
class Num(val token: Token) extends AST {
  val value = token.tokenValue
}
class NoOp extends AST

class Parser(val lexer: Lexer) {
  var current_token: Token = lexer.get_next_token()

  def error(): Unit = throw new Exception("Invalid syntax")

  def eat(token_type: String): Unit = {
    if (current_token.tokenType == token_type) {
      current_token = lexer.get_next_token()
    }
    else error()
  }

  def factor(): AST = {
    val token = current_token
    if (token.tokenType == plus) {
      eat(plus)
      new UnaryOp(token, factor())
    }
    else if (token.tokenType == minus) {
      eat(minus)
      new UnaryOp(token, factor())
    }
    else if (token.tokenType == integer_const) {
      eat(integer_const)
      new Num(token)
    }
    else if (token.tokenType == real_const) {
      eat(real_const)
      new Num(token)
    }
    else if (token.tokenType == lparen) {
      eat(lparen)
      val node = expr()
      eat(rparen)
      node
    }
    else {
      variable()
    }
  }

  def term(): AST = {
    var node = factor()
    while (current_token.tokenType == multiply || current_token.tokenType == div) {
      val token = current_token
      if (token.tokenType == multiply) {
        eat(multiply)
      }
      else {
        eat(div)
      }
      node = new BinOp(left=node, token=token, right=factor())
    }
    node
  }

  def expr(): AST = {
    var node = term()
    while (current_token.tokenType == plus || current_token.tokenType == minus) {
      val token = current_token
      if (token.tokenType == plus) {
        eat(plus)
      }
      else {
        eat(minus)
      }
      node = new BinOp(left=node, token=token, right=term())
    }
    node
  }

  def variable(): AST = {
    val node = new Var(current_token)
    eat(id)
    node
  }
}

class NodeVisitor

trait Symbol {
  def name: String
  def symbolType: Option[String]
}
