# CTRL+ZBRA - Palestra: Spring Boot

Palestra de Spring Boot

Palestrante: Caio Maia

--

# Intruções para rodar o projeto

```
cd springboot-stomp/
./gradlew clean build 	// Ou gradlew.bat clean build
cd deploy/
docker build -t zbra/springboot-palestra .
docker run -p 8080:8080 -t zbra/springboot-palestra
```