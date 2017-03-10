#Documentation of Lab2


##Design & Description of MyTokenizer Class

### MyTokenizer Class

In this class, I implement the `Tokenizer` interface and  also introduced several other private member variables. During the tokenizing process, the input file is analyzed character by character. The member variable `TokenKind curTokenKind` keeps track of the type of the current token and acts as the return value when `getToken()` is called. `skipToken()` changes the current state of the tokenizer, find the next token and make changes to some affected member variables.

###The Finite State Automaton

The main function of the tokenizer, taking a stream of input and identify all tokens/errors in it, is defined by the finite state automaton. Specifically, I employ the automaton Dr.Heym provided in class and implemented it in the `skipToken()` method.

###Skipping Whitespaces

The whitespace is actually regarded as a token. Skipping whitespaces is implemented in getToken(), by skipping the whitespaces token until the next token is not a whitespace.

### `skipToken()`

In `skipToken()`, the automaton is simulated, and the `curTokenKind` instance variable is changed after `skipToken()` is called.

### `getToken()`
`getToken()` simply returns the `curTokenKind`, skipping the current token if it's a whitespace.

###Store the Value of Integer/Identifier Tokens

`intVal()` and `idName()` are required to implement to return the value of the token if it's an integer/identifier. It just returns the private variable which stores the value, and the private variables, `int_token_value` and `id_token_name`, are assigned in the automaton if the token is identified as an integer/identifier.



##User Manual

* To use the tokenizer, a `MyTokenizer` object is needed. Since the constructor is private, one can call `create()` to create a `MyTokenizer object`. The `create(Scanner in)` is a static function, which takes a `Scanner` object `in` as its input and returns a MyTokenizer object.
* To get the current token, call `getToken()`. If the current token is an integer, `intVal()` can be called to get the integer value. Or if the current token is an identifier, `idName()` can be called to get the name of the identifier, which is a `String`.
* To skip the current token and move on to next token, call `skipToken()` so the next call of `getToken()` will return the next legal token encountered.

##Testing and Bugs

I tested the tokenizer over the 10 test files given and compared my outputs with the expected outputs.

Currently, no known bug remains.



