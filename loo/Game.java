package loo;
import loo.reducers.Combined;
import statecontainer.Store;
import statecontainer.Action;
import statecontainer.Payload;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import util.FileManager;

public final class Game {
  private final Store<List<StateCell>> store;
  private final HeroFactory heroFactory;
  private final JsonObject config;
  private final Map<Point, Character> terrianType;

  public Game(int mapWidth, int mapHeight, List<String> terrianMatrix) {
    this.store = new Store<List<StateCell>>(new Combined(), new ArrayList<StateCell>());
    this.terrianType = new HashMap<Point, Character>();
    this.config = new Gson().fromJson(FileManager.getContent("loo/config.json"), JsonObject.class);
    this.heroFactory = new HeroFactory(config);
    for(int i = 0; i<mapHeight;i++)
      for(int j = 0; j<mapWidth;j++) {

        terrianType.put(new Point(i,j), terrianMatrix.get(i).charAt(j));
      }
    System.out.println(terrianType);
  }

  public void addHero(final char heroSymbol, final Point position) {
    Hero hero = this.heroFactory.create(heroSymbol);
    this.store.dispatch(ActionsCreator.createAddHeroAction(hero, position));
  }
  private List<Action> computeAction(final List<StateCell> list) {
    System.out.println(list);
    List<Action> actions = new ArrayList<Action>();
    for (StateCell current : list) {
      for (StateCell opponent : list) {
          Hero currentHero = current.getHero();
          Hero opponentHero = opponent.getHero();
          if(currentHero != opponentHero) {
          Character terrian = this.terrianType.get(current.getPosition());
          actions.addAll(
            currentHero.getSpells()
            .stream()
            .map(x ->
            ActionsCreator.createApplySpellAction(x, currentHero, opponentHero, terrian)
            ).collect(Collectors.toList())
          );

        }
      }
    }
    //add start battle
    Action action = actions.stream().filter(x -> x.getType().equals("APPLY_SPELL_DEFLECT")).collect(Collectors.toList()).get(0);
    actions.add(actions.remove(actions.indexOf(action)));
    //add end battle
    return actions;
  }

  private List<Action> computeActions(List<StateCell> state) {
    return state.stream()
      .collect(Collectors.groupingBy(StateCell::getPosition))
      .entrySet()
      .stream()
      .filter(entry -> (entry.getValue().size() > 1))
      .flatMap(entry -> computeAction(entry.getValue()).stream())
      .collect(Collectors.toList());
  }

  @SuppressWarnings("unchecked")
  private List<StateCell> getState() {
    return (List<StateCell>) this.store.getState();
  }
  public void startRound(final char move) {
    List<StateCell> state = this.getState();
    List<Action> actions = this.computeActions(state);
    this.store.dispatch(actions);
  }
}

class ActionsCreator {
  public static Action createAddHeroAction(Hero hero, Point position) {
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("hero", hero);
    payload.put("position", position);
    return new Action(
      Actions.ADD_HERO,
      payload);
  }

  public static Action createApplySpellAction(Spell spell, Hero current, Hero opponent, Character terrian) {
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("current", current);
    payload.put("opponent", opponent);
    payload.put("spell", spell);
    payload.put("terrian", terrian);
    return new Action(
      "APPLY_SPELL_"+spell.getName().toUpperCase(),
      payload);
  }
}
