package loo;

import statecontainer.Action;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

/**
  * Utility to create aciton from state data.
  */
public final class ActionCreator {
  private ActionCreator() {

  }
  /**
    * @param hero hero
    * @param position hero position
    * @return addHero action
    */
  public static Action addHero(final Hero hero, final Point position) {
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("hero", hero);
    payload.put("position", position);
    return new Action(
      Actions.ADD_HERO,
      payload);
  }


  /**
    * @param spell spell which is used
    * @param current author of the spell
    * @param opponent hero which is attacked
    * @param terrain the terrain where the battle take place
    * @return spell action
    */
  public static Action spell(final Spell spell, final Hero current,
    final Hero opponent, final Character terrain) {
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("current", current);
    payload.put("opponent", opponent);
    payload.put("spell", spell);
    payload.put("terrain", terrain);
    return new Action(
      Actions.getSpellActionType(spell.getName()),
      payload
    );
  }

  /**
    * Given a hero generate the actions for all hero's spells.
    * @param current author of the spell
    * @param opponent hero which is attacked
    * @param terrain the terrain where the battle take place
    * @return spell actions as Stream
    */
  public static Stream<Action> spellFromHero(final Hero current,
    final Hero opponent, final Character terrain) {
    return current.getSpells().stream().map(spell ->
      spell(spell, current, opponent, terrain)
    );
  }

  /**
    * Generate all the possible actions of a list of heros.
    * @param heros list of heros
    * @param terrain the terrain where the battle take place
    * @return spell actions
    */
  public static List<Action> spellFromHeros(final List<Hero> heros, final Character terrain) {
    return heros.stream()
      .filter(x -> !x.isDead())
      .flatMap(x -> heros.stream()
            .filter(y -> !y.equals(x))
            .filter(y -> !y.isDead())
            .flatMap(y ->
              spellFromHero(x, y, terrain)
      )
    ).collect(Collectors.toList());
  }

  /**
    * @return start round action
    */
  public static Action startRound() {
    return new Action(Actions.START_ROUND);
  }

  /**
    * @return end round action
    */
  public static Action endRound() {
    return new Action(Actions.END_ROUND);
  }

  /**
    * @return start battle action
    */
  public static Action startBattle() {
    return new Action(Actions.START_BATTLE);
  }

  /**
    * @return end battle action
    */
  public static Action endBattle() {
    return new Action(Actions.END_BATTLE);
  }

  /**
    * @param sequence sequence of movements
    * @return move by sequance action
    */
  public static Action moveBySequence(final String sequence) {
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("sequence", sequence);
    return new Action(
      Actions.MOVE_BY_SEQUENCE,
      payload
    );
  }
}
