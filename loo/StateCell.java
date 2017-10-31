package loo;
import java.util.ArrayList;
public class StateCell {
  private Hero hero;
  private Point position;
  public StateCell(Hero hero, Point position) {
    this.hero = hero;
    this.position = position;
  }

  public Point getPosition() {
    return position;
  }

  public Hero getHero() {
    return hero;
  }

  public String toString() {
    return String.format("%s at %s", this.hero, this.position);
  }
}
