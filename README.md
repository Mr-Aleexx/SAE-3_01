# Générateur UML Automatique

> Analyse automatique de code Java et génération de diagrammes de classes UML interactifs

![Version](https://img.shields.io/badge/version-1.0-blue.svg)
![Java](https://img.shields.io/badge/Java-11+-orange.svg)

---

## Description

Le **Générateur UML** est une application Java Swing qui analyse automatiquement des fichiers sources Java (`.java`) et génère des diagrammes de classes UML complets et interactifs. L'outil détecte automatiquement les classes, attributs, méthodes, associations, héritages et implémentations d'interfaces.

### Fonctionnalités principales

- **Analyse automatique** de code Java
- **Détection des relations** : associations (uni/bidirectionnelles), héritage, interfaces
- **Support complet** : classes, interfaces, records, enums, classes abstraites
- **Interface interactive** : déplacement des classes, zoom sur détails
- **Export** : image PNG pour documentation
- **Sauvegarde** : format XML pour reprendre le travail
- **Calcul automatique** des multiplicités (1..1, 0..*, etc.)

---

## Démarrage rapide

### Prérequis

- Java JDK 11 ou supérieur
- IDE Java (Eclipse, IntelliJ, NetBeans) ou ligne de commande

### Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/Mr-Aleexx/SAE-3_01.git
cd SAE-3_01
```

2. Lancer le projet 

Sur Linux
```bash
./start.sh
```
Sur windows
```bash
./start.bat
```

3. Si vous voulez regenerer la javadoc
```bash
./generateJavaDoc.sh
```

### Utilisation basique

1. **Ouvrir un dossier** : Menu `Fichier > Ouvrir`, sélectionner un dossier contenant des fichiers `.java`
2. **Observer le diagramme** généré automatiquement
3. **Interagir** : 
   - Clic sur une classe pour la sélectionner
   - Glisser-déposer pour déplacer
   - Clic droit pour voir les détails
4. **Exporter** : Menu `Fichier > Exporter en Image`
5. **Sauvegarder** : Menu `Fichier > Sauvegarder` pour conserver les positions

---

## Structure du projet

```
retroconception/
├── Controleur.java              # Point d'entrée et coordination MVC
│
├── metier/                      # Couche métier (modèle)
│   ├── Classe.java             # Représentation d'une classe Java
│   ├── Attribut.java           # Représentation d'un attribut
│   ├── Methode.java            # Représentation d'une méthode
│   ├── Parametre.java          # Représentation d'un paramètre
│   ├── Association.java        # Relation entre classes
│   ├── PositionClasse.java     # Coordonnées et dimensions
│   ├── Retroconception.java    # Logique d'analyse et gestion
│   ├── Erreur.java             # Gestion des erreurs
│   └── lecture/                # Sous-package d'analyse
│       ├── AnalyseurJava.java      # Parsing des fichiers Java
│       ├── NettoyerFichier.java    # Prétraitement des fichiers
│       └── GereStereotype.java     # Gestion records/interfaces
│
└── ihm/                         # Couche interface (vue)
    ├── FrameUML.java           # Fenêtre principale
    ├── PanelUML.java           # Zone de dessin du diagramme
    ├── PanelClasses.java       # Liste des classes (sidebar)
    ├── PanelInfo.java          # Détails d'une classe
    ├── PanelAjout.java         # Ajout de rôles aux associations
    ├── FrameInfo.java          # Fenêtre de détails
    ├── FrameAjout.java         # Fenêtre d'ajout de rôle
    ├── BarreMenu.java          # Barre de menu
    └── IHMCUI.java             # Interface console (alternative)
```

---

## Fonctionnalités détaillées

### Détection automatique

| Élément | Support | Description |
|---------|---------|-------------|
| **Classes** | Classes normales, abstraites, statiques, final |
| **Interfaces** | Avec méthodes abstraites |
| **Records** | Génération auto des getters, equals, hashCode, toString |
| **Attributs** | Visibilité, type, modificateurs, valeurs constantes |
| **Méthodes** | Visibilité, paramètres, type retour, modificateurs |
| **Associations** | Unidirectionnelles et bidirectionnelles |
| **Multiplicités** | 1..1, 0..*, détection automatique |
| **Héritage** | Classe mère (extends) |
| **Interfaces** | Implémentations (implements) |

### Symboles UML utilisés

- **Visibilité** :
  - `+` : public
  - `-` : private
  - `#` : protected
  - `~` : package (default)
  
- **Modificateurs** :
  - <u>Souligné</u> : static
  - `{Gelé}` : final
  - `{abstract}` : abstract

- **Liens** :
  - Ligne simple avec flèche : association unidirectionnelle
  - Ligne simple sans flèche : association bidirectionnelle
  - Ligne avec triangle plein : héritage
  - Ligne pointillée avec triangle : implémentation d'interface

## Format de sauvegarde

Les projets sont sauvegardés en XML :

```xml
<ihm>
    <classes>
        <bloc>
            <nom>MaClasse</nom>
            <visibilite>public</visibilite>
            <statique>false</statique>
            <!-- ... attributs, méthodes ... -->
            <position>
                <centreX>100</centreX>
                <centreYClasse>75</centreYClasse>
                <!-- ... dimensions ... -->
            </position>
        </bloc>
    </classes>
    <lien>
        <typeAsso>bidirectionnelle</typeAsso>
        <multiplicite1>1..1</multiplicite1>
        <multiplicite2>0..*</multiplicite2>
        <!-- ... classes liées ... -->
    </lien>
</ihm>
```

---

## 🐛 Limitations connues

- **Classes internes** : Affichées comme classes séparées
- **Génériques complexes** : Certains types génériques très imbriqués peuvent ne pas être analysés correctement
- **Annotations** : Détectées mais non affichées dans le diagramme
- **Packages** : Pas de regroupement par package dans l'affichage
- **Dépendances externes** : Seules les classes du projet sont analysées (pas les librairies)

---

## Évolutions futures

- Filtres pour masquer certaines classes
- Export SVG et PDF
- Regroupement par packages


## 👥 Auteurs

- **HAZET Alex**
- **LUCAS Alexandre**
- **FRERET Alexandre**
- **AZENHA NASCIMENTO Martha**
- **CONSTANTIN Alexis**

