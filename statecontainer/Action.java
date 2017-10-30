package statecontainer;

public class Action {
  private final String type;
  private final Payload payload;

  public Action(final String type, final Payload payload) {
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
