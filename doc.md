#Documentation of Lab1


##Design & Description of MyTokenizer Class

###MyTokenizer Class

In this class, I implement the `Tokenizer` interface and  also introduced several other private member variables. During the tokenizing process, the input file is analyzed line by line. A `String` variable `line` stores the line which is currently being tokenized. To guarantee that `getToken()` can be called more than one time while returning the same token, I keep the entry position (`private int entry`) and the end position (`private int end`), indicating the position of the current token within the current line of input. Two other private variables `int int_token_value` and `String id_token_name` are used to save the value of token if it's a integer/identifier token.

###The Finite State Automaton

The main function of the tokenizer, taking a stream of input and identify all tokens/errors in it, is defined by the finite state automaton. Specifically, I employ the automaton Dr.Heym provided in class and implemented it in the `getToken()` method.

###Skipping Whitespaces

One important thing to do is to enable the tokenizer to recognize all the whitespaces between 2 legal tokens and ignore them. Here whitespaces are skipped before the automaton's work begins. If the current entry is a whitespace, I just move the `entry` to the next position which is not a whitespace. During this process, we may encountered with `EOF` as well as the end of the line. The program deals with these issues well.

### `skipToken()`

Since `entry` and `end` are hired to keep track of the position of the current token, it is easy to implement this method. In this method, I just move `entry` to `end`, also handling with possible `EOF` and line changes.

###Store the Value of Integer/Identifier Tokens

`intVal()` and `idName()` are required to implement to return the value of the token if it's an integer/identifier. It just returns the private variable which stores the value, and the private variables, `int_token_value` and `id_token_name`, are assigned in the automaton if the token is identified as an integer/identifier.



##User Manual

* To use the tokenizer, a `MyTokenizer` object is needed. Since the constructor is private, one can call `create()` to create a `MyTokenizer object`. The `create(Scanner in)` is a static function, which takes a `Scanner` object `in` as its input and returns a MyTokenizer object.
* To get the current token, call `getToken()`. If the current token is an integer, `intVal()` can be called to get the integer value. Or if the current token is an identifier, `idName()` can be called to get the name of the identifier, which is a `String`.
* To skip the current token and move on to next token, call `skipToken()` so the next call of `getToken()` will return the next legal token encountered.

##Testing and Bugs

I tested the tokenizer over the 10 test files given and compared my outputs with the expected outputs.

Currently, no known bug remains.



