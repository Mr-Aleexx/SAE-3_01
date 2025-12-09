SOURCES := $(shell find src -name "*.java")

build:
	javac $(SOURCES) -d bin

run: build
	java -cp bin Controller

clean:
	rm -rf bin/*
