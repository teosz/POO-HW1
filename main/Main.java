package main;

import loo.Game;

final class Main {
  private Main() {

  }

  public static void main(final String[] args) {
    System.out.println(args[0]);
    Game game = new Game(2,2);
    game.addHero('R', 0, 0);
    System.out.println(args[1]);
  }
}
