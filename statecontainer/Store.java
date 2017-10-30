package statecontainer;

public class Store<T> {
  private T state;
  private final Reducer<T> reducer;

  public Store(final Reducer<T> reducer, final T initialState) {
    this.state = initialState;
    this.reducer = reducer;
  }

  public void dispatch(final Action action) {
    this.state = this.reducer.reduce(state, action);
  }
}
