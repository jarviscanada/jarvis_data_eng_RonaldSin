package ca.jrvs.apps.grep;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep{

    final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args){
        if(args.length != 3){
            throw new IllegalArgumentException("Usage: Regex rootPath outPath");
        }

        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try{
            javaGrepImp.process();

        } catch (Exception ex){
            javaGrepImp.logger.error("Error: Unable to process");
        }
}

    @Override
    public void process() throws IOException {
        this.logger.info("Looking for files...");
        for (File file : listFiles(getRootPath())){
            ArrayList<String> l = new ArrayList<>();
            for (String line : readLines(file)){
                if(containsPattern(line)){
                    l.add(line);
                }

            }
            this.logger.info("Found " + l.size() + " matches");
            if(l.size() > 0) {
                writeToFile(l);
            }
        }
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File dir = new File(rootDir);

        ArrayList<File> files = new ArrayList<>(Arrays.asList(dir.listFiles()));

        if (files != null) {
            for (File file : files) {
                this.logger.info("Found: " + rootDir + "/" + file.getName());

                if(file.isDirectory()){
                    this.logger.info(rootDir + "/" + file.getName() + " is a directory");
                    this.logger.info("Searching " + rootDir + "/" + file.getName());
                    files.addAll(listFiles(rootDir + "/" + file.getName()));
                    files.remove(file);
                }
                else{
                    this.logger.info(rootDir + "/" + file.getName() + " is a file");
                }
            }
        }

        return files;
    }

    @Override
    public List<String> readLines(File inputFile) {
        ArrayList<String> result = new ArrayList<>();
        this.logger.info("Loading lines from: " + inputFile);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean containsPattern(String line) {
        Pattern pattern = Pattern.compile(getRegex());
        Matcher matcher = pattern.matcher(line);

        if(matcher.find()){
            this.logger.info("Found match: " + line);
            return(true);
        }
        return(false);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(getOutFile(), true));

        this.logger.info("Writing to file... ");
        for(String l : lines){
            writer.append('\n');
            writer.append(l);
        }
        writer.close();
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

}
