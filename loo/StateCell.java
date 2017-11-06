package loo;
/**
  * Element which computes the state.
  */
public final class StateCell {
  private Hero hero;
  private Point position;

  /**
    * Initiate a new StateCell.
    * @param hero the hero inside the cell
    * @param position the position of the cell
    */
  public StateCell(final Hero hero, final Point position) {
    this.hero = hero;
    this.position = position;
  }

  /**
    * @return the name position of the cell
    */
  public Point getPosition() {
    return position;
  }


  /**
    * @return the name hero of the cell
    */
  public Hero getHero() {
    return hero;
  }

  /**
    * move cell position.
    * @param move position movement
    */
  public void move(final Point move) {
      this.position.add(move);
  }

  public String toString() {
    if (this.hero.isDead()) {
      return String.format("%s", this.hero);
    }
    return String.format("%s %s", this.hero, this.position);
  }
}
