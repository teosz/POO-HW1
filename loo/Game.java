package loo;
import loo.reducers.Combined;
import statecontainer.Store;
import statecontainer.Action;
import statecontainer.Payload;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    // System.out.println(terrianType);
  }

  public void addHero(final char heroSymbol, final Point position) {
    Hero hero = this.heroFactory.create(heroSymbol);
    Action action = ActionCreator.addHero(hero, position);
    this.store.dispatch(action);
  }

  @SuppressWarnings("unchecked")
  private List<StateCell> getState() {
    return (List<StateCell>) this.store.getState();
  }

  private List<Hero> getHeros(final List<StateCell> cells) {
    return cells.stream()
      .map(StateCell::getHero)
      .collect(Collectors.toList());
  }


  private List<Action> getBattleActions(final Point position, final List<StateCell> list) {
    List<Hero> heros = this.getHeros(list);
    Character terrian = this.terrianType.get(position);
    List<Action> actions = ActionCreator.spellFromHeros(heros, terrian);
    Action action = actions.stream().filter(x -> x.getType().equals("APPLY_SPELL_DEFLECT")).collect(Collectors.toList()).get(0);
    actions.add(actions.remove(actions.indexOf(action)));
    return wrapActions(
      ActionCreator.startBattle(),
      actions,
      ActionCreator.endBattle()
    );
  }

  private List<Action> getRoundActions(List<StateCell> state) {
    return state.stream()
      .collect(Collectors.groupingBy(StateCell::getPosition))
      .entrySet()
      .stream()
      .filter(entry -> (entry.getValue().size() > 1))
      .flatMap(entry ->
        getBattleActions(
          entry.getKey(),
          entry.getValue()
        ).stream()
      )
      .collect(Collectors.toList());
  }

  private List<Action> wrapActions(Action start, List<Action> mids, Action end) {
    List<Action> actions = new ArrayList<Action>();
    actions.add(start);
    actions.addAll(mids);
    actions.add(end);
    return actions;

  }

  public void startRound(final char move) {
    List<StateCell> state = this.getState();
    List<Action> actions = wrapActions(
      ActionCreator.startRound(),
      this.getRoundActions(state),
      ActionCreator.endRound()
    );
    this.store.dispatch(actions);
  }
}
