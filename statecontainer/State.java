package statecontainer;
import java.util.Map;
public interface State<T, Q> {
  public HashMap<T, Q> getHeroMap();
}
