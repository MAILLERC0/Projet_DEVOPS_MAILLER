# Projet_DEVOPS_MAILLER #
Le but de ce projet est de réaliser un environnement de simulation pour une application java (fournie).
Pour avoir un rendu git correcte, le projet (monorepo) devra respecter le git flow vu en cours 
et il devra suivre l'Arborescence suivante : 
Projet_DevOps
├── README.md
├── ansible
├── jenkins
└── terraform 
Format attendu dans un zip: Projet_DevOps_MAILLER-Corentin.zip

## MIS EN PLACE ##
INFO : Pour permettre l'enchainement des pipelines, passer le nombre d'exécuteurs à 3 dans les réglages Jenkins (Administrer Jenkins/Configurer le système)

1. Pipeline de build et de CI --> TERMINE
Une pipeline jenkins permet de :
- Créer un jar à partir du repository Git: https://github.com/Ozz007/sb3t
- Lancer les tests unitaires et tests d’intégrations
- Déposer le jar dans le workspace jenkins
La pipeline devra à les paramètres suivants :
- BRANCH: String pour sélectionner la branche du repo Github
- SKIP_TESTS: un booléen pour contrôler l'exécution des tests
- VERSION_TYPE: SNAPSHOT ou RELEASE (renommer le jar)
- VERSION: un string pour la version du jar (ex: SB3T-1.0-SNAPSHOT)

2. Pipeline de IaC --> TERMINE
Une pipeline jenkins permettant de générer une instance aws créée et explicitée via
Terraform.
Le code Terraform permet de déclarer :
- Une instance aws.
- Une clef ssh qui sera automatiquement rapatriée dans l’instance.
- Un security group permettant l’ouverture au protocol ssh et aux ports 22/8080 de
notre application en entrée et vers tout le monde en sortie.
- Cloud init pour la création d’un user deploy et l’installation du paquet python si
nécessaire
INFO : Le plugin Terraform à été ajouté dans Jenkins pour permettre son utilisation et l'outil à été configuré pour être utilisé (terraform-11)

3. Pipeline de CaC --> En Cours