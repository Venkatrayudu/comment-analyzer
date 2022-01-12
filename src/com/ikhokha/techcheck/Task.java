package com.ikhokha.techcheck;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;

public class Task implements Callable {

    private File currentFile;
    private Map<String, Integer> resultsMap;

    public Task(File currentFile, Map<String, Integer> resultsMap) {
        this.currentFile = currentFile;
        this.resultsMap = resultsMap;
    }

    @Override
    public Map<String, Integer> call() throws Exception
    {
//        System.out.println(this.currentFile+" is being parsed");
        CommentAnalyzer commentAnalyzer = new CommentAnalyzer(currentFile);
        Map<String, Integer> fileResults = commentAnalyzer.analyze(resultsMap);
//        System.out.println(this.currentFile+" parsing done");
        return fileResults;
    }

}
