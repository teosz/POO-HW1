package loo;

public final class StateCell {
  private Hero hero;
  private Point position;
  public StateCell(final Hero hero, final Point position) {
    this.hero = hero;
    this.position = position;
  }


  public Point getPosition() {
    return position;
  }

  public Hero getHero() {
    return hero;
  }
  public void move(Point move) {
      this.position.add(move);
  }
  public String toString() {
    if(this.hero.isDead())
      return String.format("%s", this.hero);
    return String.format("%s %s", this.hero, this.position);
  }
}
