package com.dataStructure;

import com.Component.Sprite;
import com.Component.SpriteSheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {

    static Map<String, Sprite> sprites = new HashMap<>();
    static Map<String, SpriteSheet> spriteSheets = new HashMap<>();

    public static boolean hasSprite(String pictureFile) {
        File tmp = new File(pictureFile);
        return AssetPool.sprites.containsKey(tmp.getAbsolutePath());
    }

    public static boolean hasSpriteSheet(String pictureFile){
        File tmp = new File(pictureFile);
        return AssetPool.spriteSheets.containsKey(tmp.getAbsolutePath());
    }

    public static Sprite getSprite(String pictureFile){
        File file = new File(pictureFile);
        if (AssetPool.hasSprite(file.getAbsolutePath())){
            return AssetPool.sprites.get(file.getAbsolutePath());
        }
        else {
            Sprite sprite = new Sprite(pictureFile);
            AssetPool.addSprite(pictureFile, sprite);
            return AssetPool.sprites.get(file.getAbsolutePath());
        }
    }

    public static SpriteSheet getSpriteSheet(String pictureFile){
        File file = new File(pictureFile);
        if (AssetPool.hasSpriteSheet(file.getAbsolutePath())){
            return AssetPool.spriteSheets.get(file.getAbsolutePath());
        }
        else {
            System.out.println("SpriteSheet " + pictureFile + " does not exist.");
            System.exit(-1);
        }
        return null;
    }

    public static void addSprite(String pictureFile, Sprite sprite){
        File file = new File(pictureFile);
        if (!AssetPool.hasSprite(file.getAbsolutePath())){
            AssetPool.sprites.put(file.getAbsolutePath(), sprite);
        }
        else {
            System.out.println("Asset Pool already has Asset: " + file.getAbsolutePath());
            System.exit(-1);
        }
    }

    public static void addSpriteSheet(String pictureFile, int tileWidth, int tileHeight, int spacing, int columns, int size){
        File file = new File(pictureFile);

        if (!AssetPool.hasSpriteSheet(file.getAbsolutePath())){
            SpriteSheet spriteSheet = new SpriteSheet(pictureFile, tileWidth, tileHeight, spacing, columns, size);

            AssetPool.spriteSheets.put(file.getAbsolutePath(), spriteSheet);
        }
    }
}
