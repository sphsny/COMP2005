# base image including gradle and jdk 25
FROM gradle:9-jdk25
# copy project files, build jar, run spring boot application
COPY . /usr/src/app
WORKDIR /usr/src/app
# include if permission denied:
# RUN chmod +x gradlew
# run code and build executable JAR file
RUN ./gradlew clean bootJar
# run app JAR upon container start
CMD ["java", "-jar", "build/libs/comp2005-0.0.1-SNAPSHOT.jar"]