package loo.reducers;

import loo.StateCell;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;

public final class Attack implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    switch (action.getType()) {
      case "APPLY_SPELL_DRAIN":
        System.out.println(action.getPayload());
      default:
        return state;
    }
  }
}
