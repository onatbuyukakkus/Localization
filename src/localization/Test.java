package localization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) throws Exception {
		File dir = new File(".");
		long startTime = System.currentTimeMillis();
		List<File> l = ClassFinder.getFiles("C:/Cybersoft/ADS/eclipse/workspace/ConsumerLoans");

		FileOperation fileOp = new FileOperation();
		int i = 0;
		for (File file : l) {
//			fileOp.readFile2(file);
			fileOp.fileOperation(file);
			i++;
		}

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\nElapsed Time(sn) : " + totalTime / 1000 + " - # of Classes Scanned : " + i);
	}
}
