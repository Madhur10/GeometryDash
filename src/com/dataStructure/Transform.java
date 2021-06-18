package com.dataStructure;

import com.file.Parser;
import com.file.Serialize;
import com.util.Vector2;

public class Transform extends Serialize {

    public Vector2 position;
    public Vector2 scale;
    public float rotation;

    public Transform(Vector2 position){
        this.position = position;
        this.scale = new Vector2(1.0f, 1.0f);
        this.rotation = 0.0f;
    }

    public Transform copy(){
        Transform transform = new Transform(this.position.copy());
        transform.scale = this.scale.copy();
        transform.rotation = this.rotation;
        return transform;
    }

    @Override
    public String toString(){
        return "Position (" + position.x + ", " + position.y + ")";
    }

    @Override
    public String serialize(int tabsize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Transform", tabsize));

        builder.append(beginObjectProperty("Position", tabsize + 1));
        builder.append(position.serialize(tabsize + 2));
        builder.append(closeObjectProperty(tabsize + 1));
        builder.append(addEnding(true, true));

        builder.append(beginObjectProperty("Scale", tabsize + 1));
        builder.append(scale.serialize(tabsize + 2));
        builder.append(closeObjectProperty(tabsize + 1));
        builder.append(addEnding(true, true));

        builder.append(addFloatProperty("rotation", rotation, tabsize + 1, true, false));
        builder.append(closeObjectProperty(tabsize));

        return builder.toString();
    }

    public static Transform deserialize(){
        Parser.consumeBeginObjectProperty("Transform");
        Parser.consumeBeginObjectProperty("Position");

        Vector2 position = Vector2.deserialize();
        Parser.consumeEndObjectProperty();

        Parser.consume(',');
        Parser.consumeBeginObjectProperty("Scale");
        Vector2 scale = Vector2.deserialize();
        Parser.consumeEndObjectProperty();

        Parser.consume(',');
        float rotation = Parser.consumeFloatProperty("rotation");
        Parser.consumeEndObjectProperty();

        Transform t = new Transform(position);
        t.scale = scale;
        t.rotation = rotation;

        return t;
    }
}