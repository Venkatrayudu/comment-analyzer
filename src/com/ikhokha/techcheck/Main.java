package com.ikhokha.techcheck;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Map<String, Integer> totalResults = new ConcurrentHashMap<>();
				
		File docPath = new File("docs");
		File[] commentFiles = docPath.listFiles((d, n) -> n.endsWith(".txt"));
		final int THREAD_POOL_SIZE = Math.min(commentFiles.length, 10);

		Map<String, Integer> resultsMap = new ConcurrentHashMap<>();
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

		List<Task> taskList = new ArrayList<>();

		for (File commentFile : commentFiles) {
			Task task = new Task(commentFile, resultsMap);
			taskList.add(task);
		}

		List<Future<Map<String, Integer>>> resultList = null;

		try {
			resultList = executor.invokeAll((List)taskList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		executor.shutdown();
//		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

		resultList.forEach(mapFuture -> {
			try {
				Map<String, Integer> fileResults = mapFuture.get();
				addReportResults(fileResults, totalResults);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});

		System.out.println("RESULTS\n=======");
		totalResults.forEach((k,v) -> System.out.println(k + " : " + v));
	}

	/**
	 * This method adds the result counts from a source map to the target map
	 * @param source the source map
	 * @param target the target map
	 */
	private static void addReportResults(Map<String, Integer> source, Map<String, Integer> target) {

		for (Map.Entry<String, Integer> entry : source.entrySet()) {
			target.put(entry.getKey(), entry.getValue());
		}

	}

}
