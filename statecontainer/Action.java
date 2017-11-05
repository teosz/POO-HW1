package statecontainer;
import java.util.Map;
import java.util.HashMap;

public final class Action {
  private final String type;
  private final Map payload;

  public Action(final String type, final Map<String, Object> payload) {
    this.type = type;
    this.payload = payload;
  }

  public Action(final String type) {
    this.type = type;
    this.payload = new HashMap<String, Object>();
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
