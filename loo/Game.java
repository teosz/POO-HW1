package loo;
import loo.reducers.Combined;
import statecontainer.Store;
import statecontainer.Action;
import statecontainer.Payload;
import java.util.Dictionary;
import java.util.Hashtable;

public class Game {
  final Store store;
  final HeroFactory heroFactory;
  public Game(int n, int m) {
    this.store = new Store(new Combined(), new StateCell[20]);
    this.heroFactory = new HeroFactory();
  }

  public void addHero(char heroSymbol, Point position) {
    Hero hero = this.heroFactory.createHero(heroSymbol);
    Dictionary payload = new Hashtable();
    payload.put("hero", hero);
    payload.put("position", position);
    this.store.dispatch(new Action(
      Actions.ADD_HERO,
      payload));
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
