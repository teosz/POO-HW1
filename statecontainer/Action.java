package statecontainer;

public class Action {
  private final String type;
  private final Payload payload;

  public Action(String type, Payload payload) {
    this.type = type;
    this.payload = payload;
  }

  public String getType() {
    return this.type;
  }

  public Payload getPayload() {
    return this.payload;
  }
}
