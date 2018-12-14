# Java compiler
JAVAC = javac

# Output folder
OUTPUT = classes

# Creating a .class file
COMPILE = $(JAVAC) -d $(OUTPUT)

# Source files
SRCS = \
src/Restaurant/Util/*.java \
src/Restaurant/Food/FoodMachine/*.java \
src/Restaurant/Food/*.java \
src/Restaurant/*.java \
src/Customer/*.java \
src/*.java

all:
	$(COMPILE) $(SRCS) 

run:
	java -cp $(OUTPUT) Main

clean: 
	rm -r $(OUTPUT)/*



