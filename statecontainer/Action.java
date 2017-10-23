package statecontainer;

class Action {
  private final String type;
  private final Payload payload;

  Action(String type, Payload payload) {
    this.type = type;
    this.payload = payload;
  }

  String getType() {
    return this.type;
  }

  Payload getPayload() {
    return this.payload;
  }
}
