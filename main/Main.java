package main;

import loo.Game;
import loo.Point;
import fileio.FileSystem;
import java.util.List;
import java.util.ArrayList;

final class Main {
  private Main() {

  }

  public static void main(final String[] args) throws Exception {
    String inputPath = args[0];
    String outputPath = args[1];
    FileSystem file = new FileSystem(inputPath, outputPath);
    int n = file.nextInt();
    int m = file.nextInt();
    List<String> terrainType = new ArrayList<String>();
    for (int i = 0; i < n; i++) {
      terrainType.add(file.nextWord());
    }
    Game game = new Game(n, m, terrainType);
    int p = file.nextInt();
    for (int i = 0; i < p; i++) {
      char typeSymbol = file.nextWord().charAt(0);
      int x = file.nextInt();
      int y = file.nextInt();
      game.addHero(typeSymbol, new Point(x, y));
    }
    int rounds = file.nextInt();
    for (int i = 0; i < rounds; i++) {
      game.startRound(file.nextWord());
    }
    file.writeWord(game.getGameStats());
    file.close();
  }
}
