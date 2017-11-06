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
  private static int backStabCounter = 0;
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
    Map options = spell.getOptions();
    float terrainModifier = new Float(1.0);
    int baseDamage = spell.getBaseDamage();
    float spellModifier = 1 + spell.getModifier(opponent);
    if(current.getAbilityTerrain().equals(terrain))
      terrainModifier += current.getAbilityModifier();
    float totalModifier = spellModifier * terrainModifier;
    int modifiedDamage = Math.round(baseDamage*totalModifier);
    int plainDamage = Math.round(baseDamage*terrainModifier);
    float percentage = (float) baseDamage / 100;
    switch (action.getType()) {
      case "APPLY_SPELL_DRAIN": {
        float hp = Math.min(new Float(0.3) * opponent.getBaseHP(), opponent.getCurrentHP());
        opponent.hit(
          current,
          Math.round(totalModifier*percentage*hp),
          Math.round(spellModifier*percentage*hp)
        );
        return state;
      }

      case "APPLY_SPELL_DEFLECT": {
        if(spellModifier != 1.0) {
          int sum = current.getPlainHits().stream().mapToInt(Integer::intValue).sum();
          System.out.println(current.getPlainHits());
          opponent.hit(current, Math.round(percentage*sum*totalModifier));
        }
        return state;
      }

      case "APPLY_SPELL_BACKSTAB": {
        int chargeRounds = ((Double) options.get("chargeRounds")).intValue();
        if(backStabCounter % chargeRounds == 0) {
          if(options.get("terrain").equals(terrain)) {
            float scale = new Float((Double)options.get("scale"));
            modifiedDamage = Math.round(scale*modifiedDamage);
            plainDamage = Math.round(scale*plainDamage);
          } else {
            backStabCounter = 0;
          }
        }
        opponent.hit(current, modifiedDamage, plainDamage);
        this.backStabCounter++;
        return state;
      }

      case "APPLY_SPELL_PARALYSIS": {
        String spellTerrainType = options.get("terrainType").toString();
        int rounds = 0;
        if(spellTerrainType.equals(terrain)) {
          rounds = ((Double) options.get("terrainValue")).intValue();
        } else {
          rounds = ((Double) options.get("rounds")).intValue();
        }

        opponent.hit(current, modifiedDamage, plainDamage);
        opponent.freeze(rounds);
        for(int i=0;i<rounds;i++) {
          opponent.hitWithDelay(current, modifiedDamage);
        }
        return state;
      }

      case "APPLY_SPELL_EXECUTE": {
        int limit = 0;
        int currentHP = opponent.getCurrentHP();
        if(currentHP < 0.2*opponent.getBaseHP()) {
          opponent.hit(current, currentHP, currentHP);
        } else {
          opponent.hit(current, modifiedDamage, plainDamage);
        }
        return state;
      }

      case "APPLY_SPELL_SLAM": {
        int rounds = ((Double) options.get("rounds")).intValue();
        opponent.hit(current,  modifiedDamage, plainDamage);
        opponent.freeze(rounds);
        return state;
      }

      case "APPLY_SPELL_FIREBLAST": {
        opponent.hit(current,  modifiedDamage, plainDamage);
        return state;
      }

      case "APPLY_SPELL_IGNITE": {
        opponent.hit(current,  modifiedDamage, plainDamage);
        for(int i=0;i<=1;i++) {
          opponent.hitWithDelay(current, Math.round(50*totalModifier));
        }
        return state;
      }



      default:
        return state;
    }
  }
}
