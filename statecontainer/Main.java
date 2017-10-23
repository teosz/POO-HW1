package statecontainer;
class Main {
  public static void main(String[] args) {
    Store store = new Store(new Reducer());
    store.dispatch(new Action(Actions.INIT_GAME, new InitGamePayload(new Hero(), new Point(0,0))));

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
