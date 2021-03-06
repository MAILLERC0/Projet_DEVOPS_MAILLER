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
ERR --> terraform apply ne fonctionne plus pour des raisons inconnues


3. Pipeline de CaC 
Une pipeline jenkins permettant la configuration de l’instance via ansible.
Le code ansible permet via l’utilisateur deploy créé dans la partie IaC :
- D’installer java 11
- De créer un user java_user pour le lancement de l’application java (jar)
- D’installer le jar
https://github.com/Ozz007/sb3t/releases/download/1.0/sb3t-ws-1.0.jar sur le serveur
- De lancer l'application java (jar) avec la configuration suivante:
- Taille de la jvm 128MB
INFO : Le plugin Ansible à été ajouté dans Jenkins pour permettre son utilisation et l'outil à été configuré pour être utilisé (path jusqu'à l'ansible téléchargé dans le dockerfile)

--> Lien repo GIT : https://github.com/MAILLERC0/Projet_DEVOPS_MAILLER 