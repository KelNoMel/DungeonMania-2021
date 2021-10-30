package dungeonmania.testhelper;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import dungeonmania.util.FileLoader;

public class OtherHelp {
	public static void removeSaves() {
		try {
			String dungeonFolder = FileLoader.getFolderPath("/dungeons").toString();
			dungeonFolder = dungeonFolder.substring(0, dungeonFolder.lastIndexOf("\\"));
			File saveDir = Paths.get(dungeonFolder, "dungeonSaves").toFile();
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
