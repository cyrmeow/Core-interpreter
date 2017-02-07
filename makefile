JFLAGS = -d
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) . $*.java

CLASSES = \
	TokenKind.java \
        Tokenizer.java \
	MyTokenizer.java \
        TokenizerTest.java 
        
       	 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

