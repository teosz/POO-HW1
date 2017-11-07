package statecontainer;

/**
  * Interface defining how a reducer should look like.
  */
public interface Reducer<State> {
  State reduce(State state, Action action);
}
