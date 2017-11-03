package loo;
import java.util.Map;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

final class HeroFactory {
  private Map builders;

  HeroFactory(Map builders) {
    this.builders = builders;
    System.out.println(builders.get("Pyromancer"));

  }

  public Hero create(final char heroSymbol) {
    String type = getTypeFromSymbol(heroSymbol);
    Map builder = (Map) this.builders.get(type);
    System.out.println((JSONListAdapter)builder.get("spells"));
    return new Hero(
      type,
      (int) builder.get("base_hp"),
      (int) builder.get("levelup_hp"),
      SpellFactory.batchCreate(
        (Map) builder.get("spells")
      )
    );
  }

  private static String getTypeFromSymbol(final char heroSymbol) {
    switch (heroSymbol) {
      case 'W':
        return "Wizard";
      case 'R':
        return "Rogue";
      default:
        return "";
    }
  }
}
