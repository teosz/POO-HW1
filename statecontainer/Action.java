package statecontainer;
import java.util.Map;
import java.util.HashMap;
public final class Action {

  /**
  * Class provide the functionality to manage Actions.
  */
  private final String type;
  private final Map payload;

  /**
    * Initiate new action.
    * @param type the type of the action
    * @param payload the payload of the action
    */
  public Action(final String type, final Map<String, Object> payload) {
    this.type = type;
    this.payload = payload;
  }


  /**
    * Initiate new action without payload.
    * @param type the type of the action
    */
  public Action(final String type) {
    this.type = type;
    this.payload = new HashMap<String, Object>();
  }

  /**
    * @return the action type
    */
  public String getType() {
    return this.type;
  }

  /**
    * @return the action payload
    */
  public Map getPayload() {
    return this.payload;
  }

  public String toString() {
    return String.format("%s", this.type);
  }
}
