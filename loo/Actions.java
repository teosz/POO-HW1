package loo;


/**
  * This class consists exclusively of static methods that operate on or return
  * an action type and action types String CONSTANTS.
  // * @see Action
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

  /**
   * Returns an String constant which symbolize the type of action.
   *
   * @param  spellName  name of the Spell
  //  * @see   Spell
   * @return contenation of the apply spell action prefix and the spellName.
   */
  public static String getSpellActionType(final String spellName) {
    return APPLY_SPELL_PREFIX + spellName.toUpperCase();
  }

  /**
   * Verify if actionType belongs to spell action types.
   *
   * @param  actionType  type of the Action
  //  * @see   loo.Action
  //  * @see   reducers.SpellManagement
   * @return boolean symbolizing if the condition is meet or not.
   */
  public static boolean isApplySpellAction(final String actionType) {
    return actionType.startsWith(APPLY_SPELL_PREFIX);
  }

  /**
   * Verify if actionType belongs to round action types.
   *
   * @param  actionType  type of the Action
  //  * @see   Action
  //  * @see   RoundManagement
   * @return boolean symbolizing if the condition is meet or not.
   */
  public static boolean isRoundAction(final String actionType) {
    return actionType == START_ROUND || actionType == END_ROUND;
  }

  /**
   * Verify if actionType belongs to map action types.
   *
   * @param  actionType  type of the Action
  //  * @see   Action
  //  * @see   MapManagement
   * @return boolean symbolizing if the condition is meet or not.
   */
  public static boolean isMapAction(final String actionType) {
    return actionType == MOVE_BY_SEQUENCE || actionType == ADD_HERO;
  }
}
