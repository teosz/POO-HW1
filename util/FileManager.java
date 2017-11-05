package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileManager {
    public static String getContent(String path) {
      try {
        return new String(Files.readAllBytes(Paths.get(path)));
      } catch (IOException e) {
        System.err.println("IOException: " + e.getMessage());
        return "";
    }
  }
}
