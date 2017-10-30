package statecontainer;

public class Store {
  private State state;
  private final Reducer reducer;

  public Store(final Reducer reducer, final State initialState) {
    this.state = initialState;
    this.reducer = reducer;
  }

  public void dispatch(final Action action) {
    this.state = this.reducer.reduce(state, action);
  }
}
