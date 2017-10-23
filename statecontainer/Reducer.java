package statecontainer;
import java.util.HashMap;

class Reducer {
  State reduce(State state, Action action) {
    switch(action.getType()) {
      case Actions.INIT_GAME:
        Hero hero = (((InitGamePayload)action.getPayload()).hero);
        Point position = (((InitGamePayload)action.getPayload()).position);
        HashMap<Point, Hero> newHash = new HashMap<Point, Hero>(state.getHeroMap());
        newHash.put(position, hero);
        return new State();
      default:
        return new State();
    }
  }
}
