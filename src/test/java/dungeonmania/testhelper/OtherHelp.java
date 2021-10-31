package dungeonmania.testhelper;

import java.io.File;
import java.net.URISyntaxException;

import dungeonmania.util.FileLoader;

public class OtherHelp {
	public static void removeSaves() {
		try {
			File saveDir = FileLoader.getSavePath().toFile();
			for (File f : saveDir.listFiles()) {
				if (!f.isDirectory()) {
					f.delete();
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
