#README

This is a warm-up tokenizer for Core, which identifies

* Lowercase words
* Special symbols (4 special symbols): `; = || ==`
* Integers (unsigned, possibly with leading zeros)
* Identifiers: start with uppercase letter, followed by zero or more uppercase letters and ending with zero or more digits.

##Files

* README.txt: overall introduction and instructions on how to run the tokenizer.
* Tokenizer.java: Contains the well-defined interface provided by Dr.Heym.
* TokenKind.java: Defines Token kinds needed for Part 1 of the Core Interpreter project, an enum class.
* MyTokenizer.java: Implements the Tokenizer interface, implementing the finite automaton and other main tokenizer behaviors.
* TokenizerTest.java: Contains main class.
* makefile: Used to compile the source files
* Runfile.txt: Contains a single line of text that shows how to run your program from the command line.
* doc.md: A description of the overall design of the tokenizer.

##How-to-Run
1. Open a command line terminal, change working directory to the same as the source files'
2. input `make` to compile
3. input `java core/TokenizerTest <input-file-path>` to get the tokenizer running

