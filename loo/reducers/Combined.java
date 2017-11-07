package loo.reducers;

import loo.StateCell;
import loo.Actions;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;

/**
  * Reducer which combine all the apllication Reducers.
  */
public final class Combined implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    if (Actions.isApplySpellAction(action.getType())) {
      return new SpellManagement().reduce(state, action);
    }

    if (Actions.isRoundAction(action.getType())) {
      return new RoundManagement().reduce(state, action);
    }

    if (Actions.isMapAction(action.getType())) {
      return new MapManagement().reduce(state, action);
    }

    return state;
  }
}
