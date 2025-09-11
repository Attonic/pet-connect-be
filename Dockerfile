# --- FASE 1: Build (Construção) ---
    FROM eclipse-temurin:17-jdk-alpine as builder

    WORKDIR /app
    
    COPY .mvn/ .mvn
    COPY mvnw pom.xml ./
    RUN ./mvnw dependency:go-offline
    
    COPY src ./src
    
    RUN ./mvnw package -DskipTests
    
    
    # --- FASE 2: Run (Execução) ---
    FROM eclipse-temurin:17-jre-alpine
    

    RUN apk update && apk add curl
    
    WORKDIR /app
    
    COPY --from=builder /app/target/*.jar app.jar
    
    EXPOSE 8080
    
    ENTRYPOINT ["java", "-jar", "app.jar"]