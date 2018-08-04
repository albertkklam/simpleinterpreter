from spi import Lexer, Parser, NodeVisitor

INTEGER, PLUS, MINUS, MULTIPLY, DIVIDE, LPAREN, RPAREN, EOF = \
    "INTEGER", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "LPAREN", "RPAREN", "EOF"


class Interpreter(NodeVisitor):
    def __init__(self, parser):
        self.parser = parser

    def visit_BinOp(self, node):
        if node.op.type == PLUS:
            return "(+ %s %s)" % (self.visit(node.left), self.visit(node.right))
        elif node.op.type == MINUS:
            return "(- %s %s)" % (self.visit(node.left), self.visit(node.right))
        elif node.op.type == MULTIPLY:
            return "(* %s %s)" % (self.visit(node.left), self.visit(node.right))
        elif node.op.type == DIVIDE:
            return "(/ %s %s)" % (self.visit(node.left), self.visit(node.right))

    def visit_Num(self, node):
        return node.value

    def interpret(self):
        tree = self.parser.parse()
        return self.visit(tree)


def main():
    while True:
        try:
            text = input('spi> ')
        except EOFError:
            break
        if not text:
            continue

        lexer = Lexer(text)
        parser = Parser(lexer)
        interpreter = Interpreter(parser)
        result = interpreter.interpret()
        print(result)


if __name__ == '__main__':
    main()