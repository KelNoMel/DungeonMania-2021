package dungeonmania.testhelper;

import java.io.File;

import dungeonmania.util.FileLoader;

public class OtherHelp {
	public static void removeSaves() {
		File saveDir = FileLoader.getSavePath().toFile();
		for (File f : saveDir.listFiles()) {
			if (!f.isDirectory()) {
				f.delete();
			}
		}
	}
}
