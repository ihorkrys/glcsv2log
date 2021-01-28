package com.csv2log;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteContext implements Closeable {
	private final FileWriter fileWriter;
	private final PrintWriter printWriter;

	public WriteContext(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
		this.printWriter = new PrintWriter(fileWriter);
	}

	public void write(String line) {
		printWriter.println(line);
	}

	@Override
	public void close() throws IOException {
		fileWriter.close();
		printWriter.close();
	}
}