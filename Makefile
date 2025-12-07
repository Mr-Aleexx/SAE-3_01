java_files := src/*.java

build:
	javac $(java_files) -d bin

run: build
	java -cp bin Controller

clean:
	rm -rf bin/class

