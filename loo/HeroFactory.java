package loo;
import java.util.Map;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

final class HeroFactory {
  private JsonObject builders;

  HeroFactory(JsonObject builders) {
    this.builders = builders;
  }

  public Hero create(final char heroSymbol) {
    return new Gson().fromJson(
      getBuilderFromType(getTypeFromSymbol(heroSymbol)),
      Hero.class
    );
  }

  private JsonObject getBuilderFromType(String type) {
    JsonObject builder = new Gson().fromJson(
      this.builders.get(type),
      JsonObject.class
    );
    builder.addProperty("type", type);
    return builder;

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
