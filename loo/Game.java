package loo;
import loo.reducers.Combined;
import statecontainer.Store;
import statecontainer.Action;
import statecontainer.Payload;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class Game {
  private final Store store;
  private final HeroFactory heroFactory;
  public Game(final int n, final int m) {
    this.store = new Store(new Combined(), new ArrayList<StateCell>());
    this.heroFactory = new HeroFactory();
  }

  public void addHero(final char heroSymbol, final Point position) {
    Hero hero = this.heroFactory.createHero(heroSymbol);
    Dictionary payload = new Hashtable();
    payload.put("hero", hero);
    payload.put("position", position);
    this.store.dispatch(new Action(
      Actions.ADD_HERO,
      payload));
  }

  private void computeActions(final List<Hero> list) {
    // System.out.println(list);
    for (Hero current : list) {
      for (Hero opponent : list) {
        if (current != opponent) {
          System.out.println(current);
          System.out.println(opponent);
        }
    }
  }
    // return list;
  }
  public void startRound(final char move) {
    // this.store.dispatch(newActions.START_ROUND);
    // List<StateCell> state = ;
    (List<StateCell>) this.store.getState()
        .stream()
        .collect(Collectors.groupingBy(StateCell::getPosition))
        .forEach((key, list) -> computeActions(
          list.stream()
              .map(StateCell::getHero)
              .collect(Collectors.toList())
        ));
      // for(player : ) {
        // for(opponent : this.store.getState()) {
          // Spell.computeAttacks(player.hero).each(dispatch(Actions.START_ATTACK));
      // }
      // this.store.dispatch(Actions.MOVE_ALL_HEROS, move);
    // }
  }
}

class InitGamePayload extends Payload {
  private final Hero hero;
  private final Point position;
  InitGamePayload(final Hero hero, final Point position) {
    this.hero = hero;
    this.position = position;
  }
}
