package statecontainer;
import java.util.Map;

public final class Action {
  private final String type;
  private final Map payload;

  public Action(final String type, final Map<String, Object> payload) {
    this.type = type;
    this.payload = payload;
  }

  public String getType() {
    return this.type;
  }

  public Map getPayload() {
    return this.payload;
  }

  public String toString() {
    return String.format("%s", this.type);
  }
}
