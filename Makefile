SOURCES := $(shell find retroconception -name "*.java")

build:
	javac $(SOURCES) -d class

run: build
	java -cp class retroconception.Controleur

clean:
	rm -rf class/*
