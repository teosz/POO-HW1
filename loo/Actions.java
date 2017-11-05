package loo;

public final class Actions {
  private Actions() {

  }
  private static final String APPLY_SPELL_PREFIX = "APPLY_SPELL_";
  public static final String INIT_GAME = "INIT_GAME";
  public static final String ADD_HERO = "ADD_HERO";
  public static final String START_BATTLE = "START_BATTLE";
  public static final String END_BATTLE = "END_BATTLE";
  public static final String START_ROUND = "START_ROUND";
  public static final String END_ROUND = "END_ROUND";

  public static final String applySpell(String spellName) {
    return APPLY_SPELL_PREFIX + spellName.toUpperCase();
  }

  public static final boolean isApplySpellAction(String actionType) {
    return actionType.startsWith(APPLY_SPELL_PREFIX);
  }
}
