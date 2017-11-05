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

public final class Game {
  private final Store<List<StateCell>> store;
  private final HeroFactory heroFactory;
  private final JsonObject config;
  public Game(final int n, final int m) {
    this.store = new Store<List<StateCell>>(new Combined(), new ArrayList<StateCell>());
    this.config = new Gson().fromJson(FileManager.getContent(), JsonObject.class);
    this.heroFactory = new HeroFactory(config);
  }

  public void addHero(final char heroSymbol, final Point position) {
    Hero hero = this.heroFactory.create(heroSymbol);
    this.store.dispatch(ActionsCreator.createAddHeroAction(hero, position));
  }
  private List<Action> computeAction(final List<Hero> list) {
    List<Action> actions = new ArrayList<Action>();
    for (Hero current : list) {
      for (Hero opponent : list) {
        if (current != opponent) {
          actions.addAll(
            current.getSpells()
             .stream()
             .map(x ->
              ActionsCreator.createApplySpellAction(x, current, opponent)
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
      .flatMap(entry -> computeAction(
        entry.getValue().stream()
          .map(StateCell::getHero)
          .collect(Collectors.toList())
      ).stream())
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

  public static Action createApplySpellAction(Spell spell, Hero current, Hero opponent) {
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("current", current);
    payload.put("opponent", opponent);
    payload.put("spell", spell);
    return new Action(
      "APPLY_SPELL_"+spell.getName().toUpperCase(),
      payload);
  }
}
