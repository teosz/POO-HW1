package main;

import loo.Game;
import loo.Point;

final class Main {
  private Main() {

  }

  public static void main(final String[] args) {
    Game game = new Game(2,2);
    game.addHero('W', new Point(0, 0));
    game.addHero('R', new Point(0, 0));
  }
}
