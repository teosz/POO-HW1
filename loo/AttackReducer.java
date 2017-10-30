package loo;

import statecontainer.Reducer;
import statecontainer.Action;
import java.util.HashMap;

public class AttackReducer implements Reducer<StateCell[][]> {
  @Override
  public StateCell[][] reduce(final StateCell[][] state, final Action action) {
    switch (action.getType()) {
      case Actions.ADD_HERO:
        // Hero hero = (((InitGamePayload) action.getPayload()).hero);
        // Point position = (((InitGamePayload) action.getPayload()).position);
        // System.out.println("Hereaaa");
        return state;
      default:
        return state;
    }
  }
}
