INTEGER, PLUS, MINUS, MULTIPLY, DIVIDE, EOF = "INTEGER", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "EOF"


class Token:
    def __init__(self, type, value):
        self.type = type
        self.value = value

    def __str__(self):
        return "Token({type}, {value})".format(type=self.type, value=repr(self.value))

    def __repr__(self):
        return self.__str__()


class Lexer(object):
    def __init__(self, text):
        self.text = text
        self.pos = 0
        self.current_char = text[self.pos]

    def error(self):
        raise Exception("Invalid character")

    def advance(self):
        self.pos += 1
        if self.pos >= len(self.text):
            self.current_char = None
        else:
            self.current_char = self.text[self.pos]

    def skip_whitespace(self):
        if self.current_char is not None and self.current_char.isspace():
            self.advance()

    def integer(self):
        digit_string = ""
        while self.current_char is not None and self.current_char.isdigit():
            digit_string += self.current_char
            self.advance()
        return int(digit_string)

    def get_next_token(self):
        while self.current_char is not None:

            if self.current_char.isspace():
                self.skip_whitespace()
                continue

            if self.current_char.isdigit():
                return Token(INTEGER, self.integer())

            if self.current_char == "+":
                self.advance()
                return Token(PLUS, "+")

            if self.current_char == "-":
                self.advance()
                return Token(MINUS, "-")

            if self.current_char == "*":
                self.advance()
                return Token(MULTIPLY, "*")

            if self.current_char == "/":
                self.advance()
                return Token(DIVIDE, "/")

            self.error()

        return Token(EOF, None)


class Interpreter(object):
    def __init__(self, lexer):
        self.lexer = lexer
        self.current_token = self.lexer.get_next_token()

    def error(self):
        raise Exception("Invalid syntax")

    def eat(self, token_type):
        if self.current_token.type == token_type:
            self.current_token = self.lexer.get_next_token()
        else:
            self.error()

    def expr(self):
        result = self.current_token.value
        self.eat(INTEGER)
        while self.current_token.type in (PLUS, MINUS):
            op = self.current_token
            if op.type == PLUS:
                self.eat(PLUS)
            elif op.type == MINUS:
                self.eat(MINUS)

            integer = self.current_token
            self.eat(INTEGER)

            if op.type == PLUS:
                result += integer.value
            elif op.type == MINUS:
                result -= integer.value

        while self.current_token.type in (MULTIPLY, DIVIDE):
            op = self.current_token
            if op.type == MULTIPLY:
                self.eat(MULTIPLY)
            elif op.type == DIVIDE:
                self.eat(DIVIDE)

            integer = self.current_token
            self.eat(INTEGER)

            if op.type == MULTIPLY:
                result *= integer.value
            elif op.type == DIVIDE:
                result /= integer.value

        return result


def main():
    while True:
        try:
            text = input("calc> ")
        except EOFError:
            break
        if not text:
            continue
        lexer = Lexer(text)
        interpreter = Interpreter(lexer)
        result = interpreter.expr()
        print(result)


if __name__ == "__main__":
    main()
