package statecontainer;
import java.util.List;

public final class Store<T> {
  private T state;
  private final Reducer<T> reducer;

  public Store(final Reducer<T> reducer, final T initialState) {
    this.state = initialState;
    this.reducer = reducer;
  }

  public void dispatch(final Action action) {
    this.state = this.reducer.reduce(state, action);
  }

  public void dispatch(final List<Action> actions) {
    actions.forEach(action -> this.dispatch(action));
  }

  public T getState() {
    return this.state;
  }

  // public Class<T> getMyType() {
  //   return this.type;
  // }

}
