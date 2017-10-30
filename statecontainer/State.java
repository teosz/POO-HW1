package statecontainer;
import java.util.HashMap;
public interface State<T, Q> {
  HashMap<T, Q> getHeroMap();
}
