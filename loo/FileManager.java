package loo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileManager {
    public static String getContent() {
      try {
        return new String(Files.readAllBytes(Paths.get("loo/config.json")));
      } catch (IOException e) {
        System.err.println("IOException: " + e.getMessage());
        return "";
    }
  }
}
