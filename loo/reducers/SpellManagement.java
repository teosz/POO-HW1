package loo.reducers;

import loo.StateCell;
import loo.Spell;
import loo.Hero;
import loo.Symbols;
import loo.Constants;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;
import java.util.Map;


/**
  * Reducer which all deals with all the actions affecting heros from spells.
  */
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

  /**
    * @return terrain symbol from payload.
    */
  private Character getTerrainSymbol() {
    return Character.class.cast(this.payload.get("terrain"));
  }


  /**
    * @return terrain type from symbol.
    */
  private String getTerrain() {
     return Symbols.getTerrain(this.getTerrainSymbol());
  }

  /**
    * @return terrain modifier from payload.
    */
  private float getTerrainModifier() {
     if (this.current.getAbilityTerrain().equals(this.terrain)) {
       return 1.0f + current.getAbilityModifier();
     }
     return 1.0f;
  }

  /**
    * computes all the modifiers from payload and store them
    * into current instance.
    */
  private void computeModifier() {
    this.spellModifier = 1 + this.spell.getModifier(opponent);
    this.terrainModifier = this.getTerrainModifier();
    this.totalModifier = spellModifier * terrainModifier;
  }

  /**
    * computes all the damage from payload and store
    * into current instance.
    */
  private void computeDamage() {
    this.baseDamage = spell.getBaseDamage();
    this.plainDamage = Math.round(baseDamage * terrainModifier);
    this.modifiedDamage = Math.round(baseDamage * totalModifier);
    this.percentage = (float) baseDamage / Constants.FROM_PROCENT;

  }

  /**
    * extract the payload into current instance.
    */
  private void extractPayload(final Action action) {
    this.payload = action.getPayload();
    this.spell = Spell.fromObject(payload.get("spell"));
    this.current = Hero.fromObject(payload.get("current"));
    this.opponent = Hero.fromObject(payload.get("opponent"));
    this.options = spell.getOptions();
    this.terrain = this.getTerrain();
    this.computeModifier();
    this.computeDamage();
  }

  /**
    * @return true if the spellModifier is applicabile.
    */
  private boolean spellModifierApplicable() {
    return spellModifier != 1.0;
  }

  /**
    * @return all the plain damage received by current hero.
    */
  private int getPlainHits() {
    return current.getPlainHits()
      .stream()
      .mapToInt(Integer::intValue)
      .sum();
  }


  /**
    * @return the proportionate health of the opponent.
    */
  private float getProportionateHP() {
    return Math.min(
      this.getScale() * opponent.getBaseHP(),
      opponent.getCurrentHP()
    );
  }

  /**
    * apply critical with the parameters from options.
    */
  private void applyCritical() {
    if (options.get("terrain").equals(terrain)) {
      float scale = new Float((Double) options.get("scale"));
      modifiedDamage = Math.round(scale * modifiedDamage);
      plainDamage = Math.round(scale * plainDamage);
    } else {
      current.resetCounter();
    }
  }


  /**
    * @return noOfRounds for DoT on the current terrain.
    */
  private int getRoundsOnTerrain() {
    String spellTerrainType = options.get("terrainType").toString();
    if (spellTerrainType.equals(terrain)) {
      return ((Double) options.get("terrainValue")).intValue();
    } else {
      return ((Double) options.get("rounds")).intValue();
    }
  }

  /**
    * @return number of rounds from spell options.
    */
  private int getRounds() {
    return ((Double) options.get("rounds")).intValue();
  }

  /**
    * @return number the damage base limit from spell options.
    */
  private float getBaseLimit() {
    return ((Double) options.get("baseLimit")).intValue() / Constants.FROM_PROCENT;
  }

  /**
    * @return the scale of the hit from spell options.
    */
  private float getScale() {
    return ((Double) options.get("scale")).intValue() / Constants.FROM_PROCENT;
  }

  /**
    * @return the spell damage from spell options.
    */
  private int getSpellDamage() {
    return ((Double) options.get("damage")).intValue();
  }

  /**
    * add to opponent delayed hit damage for a given number of rounds.
    * @param rounds noOfRounds
    * @param damage damge to be hit
    */
  private void hitNoOfRounds(final int rounds, final int damage) {
    for (int i = 0; i < rounds; i++) {
      opponent.hitWithDelay(damage);
    }

  }
  
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    this.extractPayload(action);
    switch (action.getType()) {
      case "APPLY_SPELL_SLAM":
        opponent.hit(current,  modifiedDamage, plainDamage);
        opponent.freeze(this.getRounds());
        return state;

      case "APPLY_SPELL_FIREBLAST":
        opponent.hit(current,  modifiedDamage, plainDamage);
        return state;

      case "APPLY_SPELL_IGNITE":
        opponent.hit(current,  modifiedDamage, plainDamage);
        this.hitNoOfRounds(
          this.getRounds(),
          Math.round(this.getSpellDamage() * totalModifier)
        );
        return state;

      case "APPLY_SPELL_DEFLECT":
        if (spellModifierApplicable()) {
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
        this.hitNoOfRounds(rounds, modifiedDamage);
        return state;

      case "APPLY_SPELL_EXECUTE":
        int currentHP = opponent.getCurrentHP();
        if (currentHP < this.getBaseLimit() * opponent.getBaseHP()) {
          opponent.hit(current, currentHP, currentHP);
        } else {
          opponent.hit(current, modifiedDamage, plainDamage);
        }
        return state;

      case "APPLY_SPELL_DRAIN":
        float hp = this.getProportionateHP();
        opponent.hit(
          current,
          Math.round(totalModifier * percentage * hp),
          Math.round(spellModifier * percentage * hp)
        );
        return state;


      default:
        return state;
    }
  }
}
