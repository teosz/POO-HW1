package loo;


/**
  * This class consists exclusively of static methods that operate on or return
  * an action type and action types String CONSTANTS.
  * @see Action
  */
public final class Actions {
  private Actions() {

  }
  private static final String APPLY_SPELL_PREFIX = "APPLY_SPELL_";
  public static final String ADD_HERO = "ADD_HERO";
  public static final String START_BATTLE = "START_BATTLE";
  public static final String END_BATTLE = "END_BATTLE";
  public static final String START_ROUND = "START_ROUND";
  public static final String END_ROUND = "END_ROUND";
  public static final String MOVE_BY_SEQUENCE = "MOVE_BY_SEQUENCE";

  public static final String applySpell(String spellName) {
    return APPLY_SPELL_PREFIX + spellName.toUpperCase();
  }

  public static final boolean isApplySpellAction(String actionType) {
    return actionType.startsWith(APPLY_SPELL_PREFIX);
  }

  public static final boolean isRoundAction(String actionType) {
    return actionType == START_ROUND || actionType == END_ROUND;
  }

  public static final boolean isMapAction(String actionType) {
    return actionType == MOVE_BY_SEQUENCE || actionType == ADD_HERO;
  }
}
