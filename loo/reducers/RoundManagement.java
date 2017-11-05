package loo.reducers;

import loo.StateCell;
import loo.Spell;
import loo.Hero;
import loo.Actions;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.Math;

public final class RoundManagement implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    switch (action.getType()) {
      case Actions.START_ROUND:
        List<Hero> heros = state.stream()
          .map(StateCell::getHero)
          .filter(hero -> hero.getDelayedHits().size() > 0)
          .collect(Collectors.toList());
        heros.forEach(hero -> hero.hit(hero.popDelayedHit()));
        heros.forEach(hero -> hero.clearPlainHits());
        return state;
      case Actions.END_ROUND:
        return state;
      default:
        return state;
    }
  }
}
