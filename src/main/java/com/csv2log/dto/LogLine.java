package com.csv2log.dto;

import com.opencsv.bean.CsvBindByName;

public class LogLine implements Comparable<LogLine> {
	@CsvBindByName(column = "timestamp")
	private String timestamp;
	@CsvBindByName(column = "traceId")
	private String traceId;
	@CsvBindByName(column = "app_name")
	private String appName;
	@CsvBindByName(column = "level_name")
	private String levelName;
	@CsvBindByName(column = "source_class_name")
	private String sourceClass;
	@CsvBindByName(column = "full_message")
	private String fullMessage;
	@CsvBindByName(column = "hostname")
	private String hostname;

	public String getTimestamp() {
		return timestamp;
	}

	public String getTraceId() {
		return traceId;
	}

	public String getAppName() {
		return appName;
	}

	public String getLevelName() {
		return levelName;
	}

	public String getSourceClass() {
		return sourceClass;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public String getHostname() {
		return hostname;
	}

	@Override
	public String toString() {
		return String.join(" ",
				timestamp, format(levelName),
				format(maskTraceId()), format(appName),
				maskSourcePath(),
				fullMessage);
	}

	private String maskSourcePath() {
		String[] sourcePath = sourceClass.split("\\.");
		for (int i = 0; i < sourcePath.length - 1; i++) {
			String subPath = sourcePath[i];
			if (subPath.length() > 1) {
				sourcePath[i] = subPath.substring(0, 1);
			}
		}
		return String.join(".", sourcePath);
	}

	private String format(String value) {
		return "[" + value + "]";
	}

	private String maskTraceId() {
		if (traceId == null || traceId.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 16; i++) {
				sb.append("*");
			}
			return sb.toString();
		}
		return traceId;
	}

	@Override
	public int compareTo(LogLine second) {
		return timestamp.compareTo(second.timestamp);
	}
}
