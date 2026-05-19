# Introduction
The Java Grep application is a lightweight clone of the Linux grep utility. It recursively scans all folders within a specified directory, look through every file line by line to match a specific text pattern, and writes the matching lines to an output file. Built using Java features like lambdas and streams, the project also incorporates native logging, JUnit for testing, and Maven for dependency management and build automation. Additionally, the application is dockerized for easy deployment.

# Quick Start
1. Pull the docker image from https://hub.docker.com/repository/docker/ronaldsin/grep/general
```
docker pull ronaldsin/grep
```
1. Or build the dockerfile included in this project
```
docker build -t ronaldsin/grep .
```
2. run the docker image
``` 
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log \
ronaldsin/grep .*Romeo.*Juliet.* /data /log/grep.out
```

#Implemenation
## Pseudocode
```
for all files in {root directory}
    for all line in each file
        if line matches {pattern} add line to array

write array to {output file}
```

## Performance Issue
If a file is too large, our program will fail to run because it currently attempts to load the entire contents of the file into memory before analyzing it. To solve this, we can use either a BufferedReader or Java Streams to read and process the file line-by-line.

# Test
Testing was done with Junit testing using manually created test cases.

# Deployment
The application was Dockerized by first packaging it using Maven. A Dockerfile was then created using eclipse-temurin:8-jdk-alpine as the base image. Next, the necessary compiled components from the local target/ directory were copied into the container's application folder. Finally, the ENTRYPOINT was configured to execute the grep.jar file using Java whenever the container starts. The process concluded by building the final container image using the docker build command.

# Improvement
- add a gui
- add a loading bar to show users the app has not crashed in the case of very large files
- an out of memory issue could still occur if a given file has lines larger than the current memory. A potential fix for this would be to use a custom character buffer to chunk a line and processe it chunk by chunk. 