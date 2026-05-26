package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp{
    public static void main(String[] args){
        if(args.length != 3){
            throw new IllegalArgumentException("Usage: Regex rootPath outPath");
        }

        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try{
            javaGrepLambdaImp.process();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void process() throws IOException {
        writeToFile(listFiles(getRootPath()).stream()
                .flatMap(x -> readLines(x).stream())
                .filter(x -> containsPattern(x))
                .collect(Collectors.toList()));
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File dir = new File(rootDir);

        return Arrays.stream(dir.listFiles()).flatMap(x -> {
            if(x.isDirectory()){
                return(listFiles(rootDir + "/" + x.getName()).stream());
            }
                return(Stream.of(x));
        }).filter(x -> !x.isDirectory()).collect(Collectors.toList());

    }

    @Override
    public List<String> readLines(File inputFile) {
        try(Stream<String> files = Files.lines(inputFile.toPath()) ){
            return files.collect(Collectors.toList());
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {

        lines.forEach(x -> {
                try {
                    Files.write(
                            Paths.get(getOutFile()),
                            (x + "\n").getBytes(),
                            StandardOpenOption.APPEND,
                            StandardOpenOption.CREATE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

}

