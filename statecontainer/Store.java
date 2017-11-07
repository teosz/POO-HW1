package statecontainer;
import java.util.List;
/**
  * The store is the place where data is stored and mutated.
  */
public final class Store<T> {
  private T state;
  private final Reducer<T> reducer;

  /**
    * Initiate a new store.
    * @param reducer reducer which will be called when data is mutated
    * @param initialState the initialState
    */
  public Store(final Reducer<T> reducer, final T initialState) {
    this.state = initialState;
    this.reducer = reducer;
  }

  /**
    * Modiy the state by a given action.
    * @param action action
    */
  public void dispatch(final Action action) {
    this.state = this.reducer.reduce(state, action);
  }

  /**
    * Modiy the state by a given list of actions.
    * @param actions list of actions
    */
  public void dispatch(final List<Action> actions) {
    actions.forEach(action -> this.dispatch(action));
  }

  /**
    * @return the state.
    */
  public T getState() {
    return this.state;
  }

}
