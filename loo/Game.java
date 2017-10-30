package loo;
import statecontainer.Store;
import statecontainer.Action;
import statecontainer.Payload;

public class Game {
  final Store store;
  public Game() {
    this.store = new Store(new AttackReducer(), new GameState());
  }

  public void addHero(char heroSymbol, int x, int y) {
    System.out.println(HeroFactory.createHero(heroSymbol));
    // this.store.dispatch(new Action(
      // Actions.ADD_HERO,
      // new InitGamePayload(, new Point(x, y))));
  }
}

class InitGamePayload extends Payload {
  public final Hero hero;
  public final Point position;
  InitGamePayload(final Hero hero, final Point position) {
    this.hero = hero;
    this.position = position;
  }
}
