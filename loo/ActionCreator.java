package loo;

import statecontainer.Action;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

class ActionCreator {
  public static Action addHero(Hero hero, Point position) {
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("hero", hero);
    payload.put("position", position);
    return new Action(
      Actions.ADD_HERO,
      payload);
  }

  public static Action spell(final Spell spell, final Hero current, final Hero opponent, final Character terrain) {
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

  public static Stream<Action> spellFromHero(final Hero current, final Hero opponent, final Character terrain) {
    return current.getSpells().stream().map(spell ->
      spell(spell, current, opponent, terrain)
    );
  }

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

  public static Action startRound() {
    return new Action(Actions.START_ROUND);
  }

  public static Action endRound() {
    return new Action(Actions.END_ROUND);
  }

  public static Action startBattle() {
    return new Action(Actions.START_BATTLE);
  }

  public static Action endBattle() {
    return new Action(Actions.END_BATTLE);
  }

  public static Action moveBySequence(String sequence) {
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("sequence", sequence);
    return new Action(
      Actions.MOVE_BY_SEQUENCE,
      payload
    );
  }
}
