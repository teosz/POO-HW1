package loo;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

/**
  * Class providing the functionality to create new Heros.
  */
final public class HeroFactory {
  private final JsonObject builders;

  /**
    * Initialize new hero factory.
    * @param builders mapping of hero type to hero's builder
    */
  HeroFactory(final JsonObject builders) {
    this.builders = builders;
  }

  /**
    * Create new hero.
    * @param heroSymbol symbolizing the type of the Hero
    * @return hero
    */
  public Hero create(final char heroSymbol) {
    return new Gson().fromJson(
      getBuilderFromType(getTypeFromSymbol(heroSymbol)),
      Hero.class
    );
  }

  /**
    * Get hero builder from a specific type.
    * @param type hero type
    * @return JsonObject with the build options
    */
  private JsonObject getBuilderFromType(final String type) {
    JsonObject builder = new Gson().fromJson(
      this.builders.get(type),
      JsonObject.class
    );
    builder.addProperty("type", type);
    return builder;

  }

  public static char getSymbolFromType(final String type) {
    switch (type) {
      case "Wizard":
        return 'W';
      case "Rogue":
        return 'R';
      case "Knight":
        return 'K';
      case "Pyromancer":
        return 'P';
      default:
        return ' ';
    }
  }

  private static String getTypeFromSymbol(final char heroSymbol) {
    switch (heroSymbol) {
      case 'W':
        return "Wizard";
      case 'R':
        return "Rogue";
      case 'K':
        return "Knight";
      case 'P':
        return "Pyromancer";
      default:
        return "";
    }
  }
}
