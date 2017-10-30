package statecontainer;
public interface Reducer<State> {
  State reduce(State state, Action action);
}
