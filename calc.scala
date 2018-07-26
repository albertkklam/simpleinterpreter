class Token(val tokenType: String,
            val tokenValue: AnyVal) {
  override def toString: String = {
    s"Token($tokenType, $tokenValue)"
  }
}

class Interpreter(val text: String,
                  val pos: Int = 0,
                  val current_token: Option[Token] = None)
                 (val current_char: Option[Char] = Option(text(pos))) {
}