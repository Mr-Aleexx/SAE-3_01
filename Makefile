SOURCES := $(shell find src -name "*.java")

build:
	javac $(SOURCES) -d class

run: build
	java -cp class src.Controleur

clean:
	rm -rf class/*
