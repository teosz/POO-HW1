package statecontainer;

class Store {
  private final State state = new State();
  private final Reducer reducer;

  Store(Reducer reducer) {
    this.reducer = reducer;
  }

  public void dispatch(Action action) {
    this.reducer.handle(action);
  }
}
