package statecontainer;
import java.util.Dictionary;

public final class Action {
  private final String type;
  private final Dictionary payload;

  public Action(final String type, final Dictionary payload) {
    this.type = type;
    this.payload = payload;
  }

  public String getType() {
    return this.type;
  }

  public Dictionary getPayload() {
    return this.payload;
  }
}
