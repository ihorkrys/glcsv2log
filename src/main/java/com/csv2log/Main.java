package com.csv2log;

import java.io.File;

public class Main {
	public static void main(String[] args) {
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
		LogWriter.doWrite(input, mergeToOne);
	}
}
