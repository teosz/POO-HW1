package loo.reducers;

import loo.StateCell;
import loo.Spell;
import loo.Hero;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public final class SpellManagement implements Reducer<List<StateCell>> {
  private String getTerrain(final Character symbol) {
    switch(symbol) {
      case 'L':
        return "Land";
      case 'V':
        return "Volcanic";
      case 'D':
        return "Desert";
      case 'W':
        return "Woods";
      default:
        return "";
    }
  }

  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    Map payload = action.getPayload();
    Spell spell = Spell.fromObject(payload.get("spell"));
    Hero opponent = Hero.fromObject(payload.get("opponent"));
    Hero current = Hero.fromObject(payload.get("current"));
    String terrain = getTerrain(Character.class.cast(payload.get("terrain")));
    int baseDamage = spell.getBaseDamage();
    float modifier = 1 + spell.getModifier(opponent);
    Map options = spell.getOptions();
    switch (action.getType()) {
      case "APPLY_SPELL_DRAIN": {
        if(modifier != 0) {
          float hp = Math.min(new Float(0.3) * opponent.getBaseHP(), opponent.getCurrentHP());
          float percentage = (float) baseDamage / 100;
          opponent.hit(
            current,
            Math.round(modifier*percentage*hp),
            Math.round(percentage*hp)
          );
          return state;
        }
      }

      case "APPLY_SPELL_DEFLECT": {
        float percentage = (float) spell.getBaseDamage() / 100;
        int sum = current.getPlainHits().stream().mapToInt(Integer::intValue).sum();
        opponent.hit(current, Math.round(percentage*sum*modifier));
        return state;
      }

      case "APPLY_SPELL_BACKSTAB": {
        opponent.hit(current, Math.round(baseDamage*modifier), baseDamage);
        return state;
      }

      case "APPLY_SPELL_PARALYSIS": {
        int damage = Math.round(baseDamage * modifier);
        String spellTerrainType = options.get("terrainType").toString();
        int rounds = 0;
        if(spellTerrainType.equals(terrain)) {
          rounds = ((Double) options.get("terrainValue")).intValue();
        } else {
          rounds = ((Double) options.get("rounds")).intValue();
        }

        opponent.hit(current, damage, baseDamage);
        opponent.freeze(rounds);
        for(int i=0;i<rounds;i++) {
          opponent.hitWithDelay(current, damage);
        }
        return state;
      }

      default:
        return state;
    }
  }
}
