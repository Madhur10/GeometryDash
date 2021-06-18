package com.Component;

import com.dataStructure.AssetPool;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    public List<Sprite> sprites;
    public int tileWidth;
    public int tileHeight;
    public int spacing;


    public SpriteSheet(String pictureFile, int tileWidth, int tileHeight, int spacing, int columns, int size){
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.spacing = spacing;

        Sprite parent = AssetPool.getSprite(pictureFile);

        sprites = new ArrayList<>();
        int row = 0;
        int count = 0;

        while (count < size){
            for (int column = 0; column < columns; column++){
                int imgX = (column * tileWidth) + (column * spacing);
                int imgY = (row * tileWidth) + (row * spacing);

                sprites.add(new Sprite(parent.image.getSubimage(imgX, imgY, tileWidth, tileHeight),
                        row, column, count, pictureFile));
                count++;
                if (count > size - 1){
                    break;
                }
            }
            row++;
        }
    }
}
