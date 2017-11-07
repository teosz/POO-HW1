package loo;
/**
  * Class providing the functionality to manange symbols.
  */
public final class Symbols {
  private Symbols() {
  }

  /**
    * @param symbol terrian symbol
    * @return terrian string
    */
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

  /**
  * @param move the movement which will be made
  * @return point symbolizing the movement
  */
  public static Point getMoveFrom(final Character move) {
    switch (move) {
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

  /**
  * @param type the type of the hero
  * @return character with the symbol
  */
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

  /**
  * @param heroSymbol with the symbol
  * @return hero the type of the hero
  */
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
