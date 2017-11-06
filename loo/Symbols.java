package loo;

public final class Symbols {
  private Symbols() {

  }
  public static String getTerrain(final Character symbol) {
    switch (symbol) {
      case 'L':
        return "Land";
      case 'V':
        return "Volcanic";
      case 'D':
        return "Desert";
      case 'W':
        return "Woods";
      default:
        return "";
    }
  }

  public static Point getMoveFrom(final Character s) {
    switch (s) {
      case 'L':
        return new Point(0, -1);
      case 'R':
        return new Point(0, 1);
      case 'U':
        return new Point(-1, 0);
      case 'D':
        return new Point(1, 0);
      default:
        return new Point(0, 0);
    }
  }

  public static char getHeroSymbolFromType(final String type) {
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

  public static String getHeroTypeFromSymbol(final Character heroSymbol) {
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
