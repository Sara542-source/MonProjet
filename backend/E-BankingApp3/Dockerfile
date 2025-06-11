# Étape 1 : Build du WAR avec Maven
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

# Copier pom.xml et sources
COPY pom.xml .
COPY src ./src

# Build du WAR sans tests
RUN mvn clean package -DskipTests

# Étape 2 : Image Tomcat 10 avec Java 21
FROM tomcat:10.1.15-jdk21-temurin

# Supprimer les applications par défaut de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copier le WAR buildé dans Tomcat (en ROOT.war)
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Modifier le port HTTP dans server.xml de 8080 à 9097 (optionnel, adapte si tu veux un autre port)
RUN sed -i 's/port="8080"/port="9097"/' /usr/local/tomcat/conf/server.xml

# Exposer le port HTTP (9097) et le port JMX (1095)
EXPOSE 9097 1095

# Variables d'environnement JAVA_OPTS pour activer JMX (ajuste si besoin)
ENV JAVA_OPTS="-Dcom.sun.management.jmxremote \
               -Dcom.sun.management.jmxremote.port=1095 \
               -Dcom.sun.management.jmxremote.local.only=false \
               -Dcom.sun.management.jmxremote.authenticate=false \
               -Dcom.sun.management.jmxremote.ssl=false"

# Commande de démarrage Tomcat avec options JMX
CMD ["catalina.sh", "run"]
