package loo.reducers;

import loo.StateCell;
import loo.Spell;
import loo.Hero;
import loo.Symbols;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;
import java.util.Map;

public final class SpellManagement implements Reducer<List<StateCell>> {
  private Map payload;
  private Spell spell;
  private Hero current;
  private Hero opponent;
  private Map options;
  private String terrain;
  private float spellModifier;
  private float terrainModifier;
  private float totalModifier;
  private float percentage;
  private int baseDamage;
  private int plainDamage;
  private int modifiedDamage;

  private final Character getTerrainSymbol() {
    return Character.class.cast(this.payload.get("terrain"));
  }

  private final String getTerrain() {
     return Symbols.getTerrain(this.getTerrainSymbol());
  }

  private final float getTerrainModifier() {
     if (this.current.getAbilityTerrain().equals(this.terrain)) {
       return 1.0f + current.getAbilityModifier();
     }
     return 1.0f;
  }

  private final void computeModifier() {
    this.spellModifier = 1 + this.spell.getModifier(opponent);
    this.terrainModifier = this.getTerrainModifier();
    this.totalModifier = spellModifier * terrainModifier;
  }

  private final void computeDamage() {
    this.baseDamage = spell.getBaseDamage();
    this.plainDamage = Math.round(baseDamage * terrainModifier);
    this.modifiedDamage = Math.round(baseDamage * totalModifier);
    this.percentage = (float) baseDamage / 100;

  }

  private final void extractPayload(Action action) {
    this.payload = action.getPayload();
    this.spell = Spell.fromObject(payload.get("spell"));
    this.current = Hero.fromObject(payload.get("current"));
    this.opponent = Hero.fromObject(payload.get("opponent"));
    this.options = spell.getOptions();
    this.terrain = this.getTerrain();
    this.computeModifier();
    this.computeDamage();
  }

  private boolean canApplySpell() {
    return spellModifier != 1.0;
  }

  private int getPlainHits() {
    return current.getPlainHits()
      .stream()
      .mapToInt(Integer::intValue)
      .sum();
  }

  private float getProportionateHP() {
    return Math.min(
      0.3f * opponent.getBaseHP(),
      opponent.getCurrentHP()
    );
  }
  private void applyCritical() {
    if(options.get("terrain").equals(terrain)) {
      float scale = new Float((Double) options.get("scale"));
      modifiedDamage = Math.round(scale * modifiedDamage);
      plainDamage = Math.round(scale * plainDamage);
    } else {
      current.resetCounter();
    }
  }
  
  private int getRoundsOnTerrain() {
    String spellTerrainType = options.get("terrainType").toString();
    if (spellTerrainType.equals(terrain)) {
      return ((Double) options.get("terrainValue")).intValue();
    } else {
      return ((Double) options.get("rounds")).intValue();
    }
  }

  private int getRounds() {
    return ((Double) options.get("rounds")).intValue();
  }

  private float getBaseLimit() {
    return ((Double) options.get("baseLimit")).intValue() / 100;
  }

  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    this.extractPayload(action);
    switch (action.getType()) {
      case "APPLY_SPELL_DRAIN":
        float hp = this.getProportionateHP();
        opponent.hit(
          current,
          Math.round(totalModifier * percentage * hp),
          Math.round(spellModifier * percentage * hp)
        );
        return state;

      case "APPLY_SPELL_DEFLECT":
        if (canApplySpell()) {
          int sum = this.getPlainHits();
          opponent.hit(current, Math.round(percentage * sum * totalModifier));
        }
        return state;

      case "APPLY_SPELL_BACKSTAB":
        if (current.getCounter() % this.getRounds() == 0) {
          this.applyCritical();
        }
        opponent.hit(current, modifiedDamage, plainDamage);
        current.increaseCounter();
        return state;

      case "APPLY_SPELL_PARALYSIS":
        int rounds = this.getRoundsOnTerrain();
        opponent.hit(current, modifiedDamage, plainDamage);
        opponent.freeze(rounds);
        for (int i = 0; i < rounds; i++) {
          opponent.hitWithDelay(modifiedDamage);
        }
        return state;

      case "APPLY_SPELL_EXECUTE":
        int currentHP = opponent.getCurrentHP();
        if (currentHP < this.getBaseLimit() * opponent.getBaseHP()) {
          opponent.hit(current, currentHP, currentHP);
        } else {
          opponent.hit(current, modifiedDamage, plainDamage);
        }
        return state;

      case "APPLY_SPELL_SLAM":
        opponent.hit(current,  modifiedDamage, plainDamage);
        opponent.freeze(this.getRounds());
        return state;

      case "APPLY_SPELL_FIREBLAST":
        opponent.hit(current,  modifiedDamage, plainDamage);
        return state;

      case "APPLY_SPELL_IGNITE":
        opponent.hit(current,  modifiedDamage, plainDamage);
        int attack = ((Double) options.get("damage")).intValue();
        int roundsD = this.getRounds();
        for (int i = 0; i < roundsD ; i++) {
          opponent.hitWithDelay(Math.round(attack * totalModifier));
        }
        return state;

      default:
        return state;
    }
  }
}
