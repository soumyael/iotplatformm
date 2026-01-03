# Réponses aux Questions de Réflexion (Tâche 5.1)

### 1. Pourquoi avons-nous utilisé une interface `SensorReadingDAO` et une implémentation `InMemorySensorReadingDAO` ? Quel est l'avantage de cette approche pour une future intégration Big Data ?

**Réponse :**
L'utilisation d'une interface (Pattern DAO) permet de **découpler** la logique métier (Service) de la logique d'accès aux données.
actuellement, nous utilisons une implémentation en mémoire (`InMemorySensorReadingDAO`) pour simplifier le développement et les tests initiaux.
L'avantage pour le Big Data est que nous pouvons remplacer cette implémentation par une autre (ex: `CassandraSensorReadingDAO`, `HBaseSensorReadingDAO`, ou `MongoDBSensorReadingDAO`) sans changer une seule ligne de code dans la couche Service. Cela respecte le principe d'ouverture/fermeture (Open/Closed Principle) et facilite l'évolution vers des bases de données NoSQL scalables.

### 2. Quel est le rôle de CDI dans cette application ? Comment facilite-t-il le développement et la testabilité ?

**Réponse :**
CDI (Contexts and Dependency Injection) gère le cycle de vie des objets (beans) et leurs dépendances.
- **Développement :** Il réduit le code "glu" (instanciation manuelle, passage de paramètres). On utilise `@Inject` pour obtenir les instances nécessaires.
- **Testabilité :** Il facilite les tests unitaires via l'injection de "Mocks". Par exemple, on peut injecter un DAO factice dans le Service pour tester la logique métier sans dépendre de la base de données réelle.

### 3. Si nous devions gérer des millions de lectures par seconde, quelles seraient les limites de notre `InMemorySensorReadingDAO` ? Quelles technologies Big Data (vues dans le syllabus) pourraient la remplacer ?

**Réponse :**
- **Limites :** La mémoire RAM du serveur est limitée (`OutOfMemoryError`). La `ConcurrentHashMap` est performante mais stockée sur une seule machine (pas de distribution ni de réplication). Perte des données au redémarrage serveur.
- **Technologies de remplacement :**
    - **Apache Kafka :** Pour l'ingestion massive et le buffering des données en temps réel.
    - **Apache Cassandra / HBase :** Pour le stockage distribué très performant en écriture.
    - **Apache Spark Streaming / Flink :** Pour le traitement en temps réel avant stockage.

### 4. Comment cette architecture simple pourrait-elle être adaptée pour gérer un très grand volume de données IoT ?

**Réponse :**
L'architecture évoluerait vers une architecture microservices ou Lambda Architecture :
1.  **Ingestion :** Remplacer le Servlet par un point d'entrée plus léger (ex: API Gateway) qui pousse les données vers **Kafka**.
2.  **Traitement :** Des consommateurs Kafka (Spark Streaming) traitent/agrègent les données.
3.  **Stockage :** Les données chaudes (récentes) dans une base NoSQL rapide (Cassandra), les données froides (historique) dans **HDFS** ou S3.
4.  **Visualisation :** Le Dashboard interroge une vue agrégée ou indexée (ex: Elasticsearch) plutôt que la base brute.

### 5. Quels sont les avantages de conteneuriser cette application avec Docker, notamment dans un contexte de déploiement de microservices IoT ?

**Réponse :**
- **Portabilité :** "Build once, run anywhere". L'environnement (OS, libs, serveur app) est figé dans l'image.
- **Isolation :** Chaque service tourne dans son conteneur ne gêne pas les autres.
- **Scalabilité :** On peut instancier 10, 100 conteneurs de la même application en quelques secondes (via Kubernetes) pour absorber une charge soudaine de capteurs.
- **DevOps :** Facilite le CI/CD. Les mises à jour sont atomiques (on remplace l'image).
