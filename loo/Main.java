package loo;
import statecontainer.Store;
import statecontainer.Action;
import statecontainer.Actions;
import statecontainer.Payload;

final class Main {
  private Main() {

  }
  public static void main(final String[] args) {
    Store store = new Store(new AttackReducer(), new GameState());
    store.dispatch(new Action(Actions.ADD_HERO, new InitGamePayload(new Hero(), new Point(0, 0))));

  }
}

class InitGamePayload extends Payload {
  private final  Hero hero;
  private final  Point position;
  InitGamePayload(final Hero hero, final Point position) {
    this.hero = hero;
    this.position = position;
  }
}
