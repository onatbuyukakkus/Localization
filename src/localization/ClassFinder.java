package localization;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassFinder {

	public static List<File> getFiles(String str) {

		List<File> l = new ArrayList<File>();
		File folder = new File(str);
		if (folder != null) {

			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()
						&& listOfFiles[i].getName().endsWith(".java")) {
					l.add(listOfFiles[i]);
				} else if (listOfFiles[i].isDirectory()) {
					l.addAll(getFiles(listOfFiles[i].toString()));
				}
			}
		}
		return l;
	}
}
