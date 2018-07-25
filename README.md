# simpleinterpreter

> "If you don’t know how compilers work, then you don’t know how computers work." -- Steve Yegge

Let’s build a simple interpreter! This respository is based off the [Let's Build A Simple Interpreter](https://ruslanspivak.com/lsbasi-part1/) series by Ruslan Spivak. 

Follow along on what's happening:
- [x] Modify the code to allow multiple-digit integers in the input, for example “12+3”
- [x] Add a method that skips whitespace characters so that your calculator can handle inputs with whitespace characters like ” 12 + 3”
- [x] Modify the code and instead of ‘+’ handle ‘-‘ to evaluate subtractions like “7-5”
- [x] Extend the calculator to handle multiplication of two integers
- [x] Extend the calculator to handle division of two integers
- [x] Modify the code to interpret expressions containing an arbitrary number of additions and subtractions, for example “9 - 5 + 3 + 11”
- [x] Modify the source code of the calculator to interpret arithmetic expressions that contain only multiplication and division, for example “7 * 4 / 2 * 3”
- [ ] Write an interpreter that handles arithmetic expressions like “7 - 3 + 2 - 1” from scratch. Use any programming language you’re comfortable with and write it off the top of your head without looking at the examples. When you do that, think about components involved: a lexer that takes an input and converts it into a stream of tokens, a parser that feeds off the stream of the tokens provided by the lexer and tries to recognize a structure in that stream, and an interpreter that generates results after the parser has successfully parsed (recognized) a valid arithmetic expression. String those pieces together. Spend some time translating the knowledge you’ve acquired into a working interpreter for arithmetic expressions