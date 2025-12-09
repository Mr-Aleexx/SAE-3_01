SOURCES := $(shell find src -name "*.java")

build:
	javac $(SOURCES) -d bin

run: build
	java -cp bin Controleur

clean:
	rm -rf bin/*
