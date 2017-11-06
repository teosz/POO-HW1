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
    // System.out.println(action);
    // System.out.println(state);

    switch (action.getType()) {
      case Actions.START_ROUND: {
        state.stream()
          .map(StateCell::getHero)
          .filter(Hero::isFrozen)
          .forEach(hero -> hero.decreseFrozenRounds());
        state.stream()
          .map(StateCell::getHero)
          .filter(hero -> !hero.isDead())
          .filter(hero -> hero.getDelayedHits().size() > 0)
          .forEach(hero -> hero.hit(hero.popDelayedHit()));
        state.stream()
          .map(StateCell::getHero)
          .filter(hero -> !hero.isDead())
          .forEach(hero -> hero.clearPlainHits());
        return state;
      }
      case Actions.END_ROUND:
        state.stream()
          .map(StateCell::getHero)
          .filter(Hero::isDead)
          .filter(Hero::hasKiller)
          .peek(hero -> hero.getKiller().increaseXP(hero.getLevel()))
          .peek(hero -> hero.getKiller().levelUP())
          .forEach(hero -> hero.removeKiller());
        return state;
      default:
        return state;
    }
  }
}
