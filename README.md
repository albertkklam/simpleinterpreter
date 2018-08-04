# simpleinterpreter

> "If you don’t know how compilers work, then you don’t know how computers work." -- Steve Yegge

This respository is based off the [Let's Build A Simple Interpreter](https://ruslanspivak.com/lsbasi-part1/) series by [Ruslan Spivak](https://github.com/rspivak). 

## Changelog

Follow along on what's happening:

### Part 1
- [x] Modify the code to allow multiple-digit integers in the input, for example “12+3”
- [x] Add a method that skips whitespace characters so that your calculator can handle inputs with whitespace characters like ” 12 + 3”
- [x] Modify the code and instead of ‘+’ handle ‘-‘ to evaluate subtractions like “7-5”

### Part 2
- [x] Extend the calculator to handle multiplication of two integers
- [x] Extend the calculator to handle division of two integers
- [x] Modify the code to interpret expressions containing an arbitrary number of additions and subtractions, for example “9 - 5 + 3 + 11”

### Part 3
- [x] Modify the source code of the calculator to interpret arithmetic expressions that contain only multiplication and division, for example “7 * 4 / 2 * 3”
- [x] Write an interpreter that handles arithmetic expressions like “7 - 3 + 2 - 1” from scratch. Use any programming language you’re comfortable with and write it off the top of your head without looking at the examples. When you do that, think about components involved: a lexer that takes an input and converts it into a stream of tokens, a parser that feeds off the stream of the tokens provided by the lexer and tries to recognize a structure in that stream, and an interpreter that generates results after the parser has successfully parsed (recognized) a valid arithmetic expression. String those pieces together. Spend some time translating the knowledge you’ve acquired into a working interpreter for arithmetic expressions

### Part 4
- [x] Using the grammar, write an interpreter that can evaluate arithmetic expressions containing any number of +, -, *, or / operators. Your interpreter should be able to handle expressions like “2 + 7 * 4”, “7 - 8 / 4”, “14 + 2 * 3 - 6 / 2”, and so on

### Part 5
- [x] Extend the interpreter to handle arithmetic expressions containing parentheses so that your interpreter could evaluate deeply nested arithmetic expressions like: 7 + 3 * (10 / (12 / (3 + 1) - 1))

### Part 6
- [x] Write your own version of the interpreter of arithmetic expressions as described in this article

### Part 7
- [x] Write a translator (hint: node visitor) that takes as input an arithmetic expression and prints it out in postfix notation, also known as Reverse Polish Notation (RPN). For example, if the input to the translator is the expression (5 + 3) * 12 / 3 than the output should be 5 3 + 12 * 3 /
- [ ] Write a translator (node visitor) that takes as input an arithmetic expression and prints it out in LISP style notation, that is 2 + 3 would become (+ 2 3) and (2 + 3 * 5) would become (+ 2 (* 3 5))