package loo;
import loo.reducers.Combined;
import statecontainer.Store;
import statecontainer.Action;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import util.FileManager;

/**
  * Class providing the functionality to manange games.
  */
public final class Game {
  private static final String CONFIG_PATH = "loo/config.json";

  private final Store<List<StateCell>> store;
  private final HeroFactory heroFactory;
  private final JsonObject config;
  private final Map<Point, Character> terrianType;

  /**
    * Initiate a new game.
    * @param mapWidth the total width of the map (m).
    * @param mapHeight the total width of the map (n).
    * @param terrianMatrix List of string defining the type of each Point on the map.
    */
  public Game(final int mapWidth, final int mapHeight, final List<String> terrianMatrix) {
    this.store = this.createStore();
    this.config = this.createConfig();
    this.terrianType = this.createTerrain(mapWidth, mapHeight, terrianMatrix);
    this.heroFactory = new HeroFactory(this.config);

  }


  /**
    * Create a new store (statecontainer).
    * @return Store store
    */
  private Store<List<StateCell>> createStore() {
    List<StateCell> state = new ArrayList<StateCell>();
    return new Store<List<StateCell>>(new Combined(), state);
  }


  /**
    * Create JsonObject defening all the heros and their specifications.
    * @return JsonObject config
    */
  private JsonObject createConfig() {
    String content = FileManager.getContent(CONFIG_PATH);
    return new Gson().fromJson(content, JsonObject.class);
  }

  /**
    * Create a mapping between all the Points and thier type.
    * @return Map terrian
    */
  private Map<Point, Character> createTerrain(final int mapWidth,
    final int mapHeight, final List<String> terrianMatrix) {
    Map<Point, Character> terrian = new HashMap<Point, Character>();
    for (int i = 0; i < mapHeight; i++) {
      for (int j = 0; j < mapWidth; j++) {
        terrian.put(
          new Point(i, j),
          terrianMatrix.get(i).charAt(j)
        );
      }
    }
    return terrian;
  }

  /**
    * get the game current state.
    * we will have to supress the warrning because java cannot know
    * the type of a templeta at runtime and it is impossible to
    * List<T>.class.cast. In can be reflected by java 8 reflection
    * api is unstable.
    * @return State
    */
  @SuppressWarnings("unchecked")
  private List<StateCell> getState() {
    return (List<StateCell>) this.store.getState();
  }

  /**
    * get all the hero on the map.
    * @param cells current state
    * @return all the heros on map
    */
  private List<Hero> getHeros(final List<StateCell> cells) {
    return cells.stream()
      .map(StateCell::getHero)
      .collect(Collectors.toList());
  }

  /**
    * Wraps a list of actions between other actions.
    * @param start first action
    * @param mids list of actions
    * @param end last action
    * @return list composed by [start, ...mids, end]
    */
  private List<Action> wrapActions(final Action start, final List<Action> mids, final Action end) {
    List<Action> actions = new ArrayList<Action>();
    actions.add(start);
    actions.addAll(mids);
    actions.add(end);
    return actions;
  }

  /**
    * Prioritize a list of actions.
    * Deflect action is dependent on other heros actions it will be always at the end.
    * @return list of actions ending with defelect if exists.
    */
  private List<Action> prioritizeActions(final List<Action> actions) {
    List<Action> deflectActions = actions
      .stream()
      .filter(x -> x.getType().equals("APPLY_SPELL_DEFLECT"))
      .collect(Collectors.toList());
    if (deflectActions.size() > 0) {
      Action deflectAction = deflectActions.get(0);
      int deflectActionIndex = actions.indexOf(deflectAction);
      actions.add(actions.remove(deflectActionIndex));
    }
    return actions;
  }

  /**
    * Return all the action made by a list of heros if they are placed at a given postion.
    * @param position the position of the heros
    * @param heros list of heros
    * @return the list of actions
    */
  private List<Action> getBattleActions(final Point position, final List<Hero> heros) {
    Character terrian = this.terrianType.get(position);
    List<Action> actions = ActionCreator.spellFromHeros(heros, terrian);

    return wrapActions(
      ActionCreator.startBattle(),
      this.prioritizeActions(actions),
      ActionCreator.endBattle()
    );
  }


  /**
    * Return all the actions for a round made by all the heros.
    * @param state game state
    * @return the list of actions
    */
  private List<Action> getRoundActions(final List<StateCell> state) {
    return state.stream()
      .collect(Collectors.groupingBy(StateCell::getPosition))
      .entrySet()
      .stream()
      .filter(entry -> (entry.getValue().size() > 1))
      .flatMap(entry ->
        getBattleActions(
          entry.getKey(),
          this.getHeros(entry.getValue())
        ).stream()
      )
      .collect(Collectors.toList());
  }

  /**
    * Given a hero symbol and position create the Hero instance and it to the state.
    * @param heroSymbol  hero's symbol
    * @param position hero' position
    */
  public void addHero(final char heroSymbol, final Point position) {
    Hero hero = this.heroFactory.create(heroSymbol);
    Action action = ActionCreator.addHero(hero, position);
    this.store.dispatch(action);
  }

  /**
    * Given a sequance of moves dispatch all the actions in the current round.
    * @param moveSequence sequance describing the moves
    */
  public void startRound(final String moveSequence) {
    List<StateCell> state = this.getState();
    this.store.dispatch(ActionCreator.moveBySequence(moveSequence));
    this.store.dispatch(ActionCreator.startRound());
    this.store.dispatch(this.getRoundActions(state));
    this.store.dispatch(ActionCreator.endRound());
  }


/**
  * @return all the stats for the current state.
  */
  public String getGameStats() {
    return this.getState()
    .stream()
    .map(x -> x.toString())
    .collect(Collectors.joining("\n"));
  }
}
