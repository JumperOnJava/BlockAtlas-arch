package io.github.jumperonjava.blockatlas.api;

import com.google.gson.*;
import net.minecraft.text.Text;

import java.lang.reflect.Type;

public class TextTypeAdapter implements JsonSerializer<Text>, JsonDeserializer<Text> {
    @Override
    public Text deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Text.Serializer.fromJson(jsonElement);
    }

    @Override
    public JsonElement serialize(Text text, Type type, JsonSerializationContext jsonSerializationContext) {
        return Text.Serializer.toJsonTree(text);
    }
}
