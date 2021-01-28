package com.csv2log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.csv2log.dto.LogLine;
import com.opencsv.bean.CsvToBeanBuilder;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			throw new IllegalArgumentException("Input path should be define as first argument");
		}

		File input = new File(args[0]);
		if (!input.exists()) {
			throw new IllegalArgumentException("Input file does not exist");
		}
		boolean mergeToOne = true;
		if (args.length == 2) {
			mergeToOne = Boolean.getBoolean(args[1]);
		}
		if (mergeToOne) {
			try (FileWriter fileWriter = new FileWriter(input.getPath() + ".log"); PrintWriter printWriter = new PrintWriter(fileWriter)) {
				new CsvToBeanBuilder<LogLine>(new FileReader(input))
						.withType(LogLine.class)
						.build().stream().sorted().forEach(logLine -> {
					String line = logLine.toString();
					System.out.println(line);
					printWriter.println(line);
				});
			}
		} else {
			Map<String, WriteContext> appInstances = new HashMap<>();
			try {
				new CsvToBeanBuilder<LogLine>(new FileReader(input))
						.withType(LogLine.class)
						.build().stream().sorted().forEach(logLine -> {
					String logName = logLine.getAppName() + "_" + logLine.getHostname();
					try {
						String line = logLine.toString();
						System.out.println(logName + "-> " + line);
						WriteContext context = appInstances.get(logName);
						if (context == null) {
							context = new WriteContext(new FileWriter(input.getPath() + logName + ".log"));
							appInstances.put(logName, context);
						}
						context.write(line);
					}
					catch (IOException e) {
						throw new IllegalStateException("Can't create file to write");
					}
				});
			} finally {
				appInstances.forEach((key, value) -> {
					try {
						value.close();
					}
					catch (IOException e) {
						throw new IllegalStateException("Can't close file to write");
					}
				});
			}
		}
	}
}
