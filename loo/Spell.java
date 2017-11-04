package loo;
import java.util.List;
import java.util.HashMap;
;

public class Spell {
  private String name;
  private int baseDamage;
  private int levelupDamage;
  private List<Behavoir> behavoirs;
  private HashMap modifiers;

  public String getName() {
    return this.name;
  }
  public String toString() {
    return String.format("%s %d %d", this.name, this.baseDamage, this.levelupDamage);
  }
}

class Behavoir {
  public String type;
  public HashMap options;

}
