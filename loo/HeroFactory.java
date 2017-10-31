package loo;
import java.util.Map;
import javax.script.ScriptException;
import java.io.IOException;

final class HeroFactory {
  private Map config;
  HeroFactory() {
    JSONParsing parser = new JSONParsing();
    try {
      config = parser.parseJson();
    } catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
    } catch (ScriptException e) {
      System.err.println("ScriptException: " + e.getMessage());
    }
  }
  public Hero createHero(final char heroSymbol) {
    String type = getTypeFromSymbol(heroSymbol);
    Map heroConfig = (Map) config.get(type);
    return new Hero(
      type,
      (int) heroConfig.get("base_hp"),
      (int) heroConfig.get("levelup_hp")
    );
  }
  private static String getTypeFromSymbol(final char heroSymbol) {
    switch(heroSymbol) {
      case 'W':
        return "Wizard";
      case 'R':
        return "Rogue";
    }
    return "";
  }
}
