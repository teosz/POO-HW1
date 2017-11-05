package loo.reducers;

import loo.StateCell;
import loo.Spell;
import loo.Hero;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public final class Attack implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    System.out.println(action);
    Map payload = action.getPayload();
    Spell spell = Spell.fromObject(payload.get("spell"));
    Hero opponent = Hero.fromObject(payload.get("opponent"));
    Hero current = Hero.fromObject(payload.get("current"));
    switch (action.getType()) {
      case "APPLY_SPELL_DRAIN": {
        float percentage = (float) spell.getBaseDamage() / 100;
        float hp = Math.min(new Float(0.3) * opponent.getBaseHP(), opponent.getCurrentHP());
        float modifier = 1 + spell.getModifier(opponent);
        opponent.hit(spell, Math.round(modifier*percentage*hp));
        return state;
      }

      case "APPLY_SPELL_DEFLECT": {
        float percentage = (float) spell.getBaseDamage() / 100;
        int sum = current.getHits().stream().mapToInt(x -> x.getBaseDamage()).sum();
        float modifier = 1 + spell.getModifier(opponent);
        opponent.hit(spell, Math.round(percentage*sum*modifier));
        System.out.println(state);
        return state;
      }


      case "APPLY_SPELL_BACKSTAB": {
        int damage = spell.getBaseDamage();
        float modifier = 1 + spell.getModifier(opponent);
        opponent.hit(spell, Math.round(damage*modifier));
        return state;
      }

      case "APPLY_SPELL_PARALYSIS": {
        int damage = spell.getBaseDamage();
        float modifier = 1 + spell.getModifier(opponent);
        opponent.hit(spell, Math.round(damage*modifier));
        return state;
      }

      default:
        return state;
    }
  }
}
