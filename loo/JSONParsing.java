package loo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public final class JSONParsing {

    private ScriptEngine engine;

    public JSONParsing() {
        ScriptEngineManager sem = new ScriptEngineManager();
        this.engine = sem.getEngineByName("javascript");
    }

    public Map parseJson() throws IOException, ScriptException {
        String json = new String(Files.readAllBytes(Paths.get("loo/config.json")));
        String script = "Java.asJSONCompatible(" + json + ")";
        Object result = this.engine.eval(script);
        return (Map) result;
    }
}
