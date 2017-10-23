package statecontainer;

class Store {
  private State state;
  private final Reducer reducer;

  Store(Reducer reducer) {
    this.state = new State();
    this.reducer = reducer;
  }

  public void dispatch(Action action) {
    this.state = this.reducer.reduce(state, action);
  }
}
