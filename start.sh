#!/bin/bash

javac @compile.list -d bin
if [ $? -ne 0 ]; then
    echo "La compilation a échoué."
    exit 1
fi

java -cp bin Controleur
if [ $? -ne 0 ]; then
    echo "L'exécution a échoué."
    exit 1
fi
echo "L'exécution s'est terminée avec succès."