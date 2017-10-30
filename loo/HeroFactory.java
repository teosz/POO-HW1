package loo;
final class HeroFactory {
  private HeroFactory() {

  }
  public static Hero createHero(final char heroSymbol) {
    
    return new Hero();
  }
}
