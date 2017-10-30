package loo;
import statecontainer.Store;
import statecontainer.Action;
import statecontainer.Actions;
import statecontainer.Payload;

class Main {
  public static void main(String[] args) {
    Store store = new Store(new AttackReducer(), new GameState());
    store.dispatch(new Action(Actions.ADD_HERO, new InitGamePayload(new Hero(), new Point(0,0))));

  }
}

class InitGamePayload extends Payload {
  Hero hero;
  Point position;
  InitGamePayload(Hero hero, Point position) {
    this.hero = hero;
    this.position = position;
  }
}
