package statecontainer;

public class Store {
  private State state;
  private final Reducer reducer;

  public Store(Reducer reducer, State initialState) {
    this.state = initialState;
    this.reducer = reducer;
  }

  public void dispatch(Action action) {
    this.state = this.reducer.reduce(state, action);
  }
}
