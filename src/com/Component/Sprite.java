package com.Component;

import com.dataStructure.AssetPool;
import com.file.Parser;
import com.jade.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite extends Component {

    public BufferedImage image;
    public String pictureFile;
    public int width, height;

    public boolean isSubSprite = false;
    public int row, column, index;

    public Sprite(String pictureFile){
        this.pictureFile = pictureFile;

        try {
            File file = new File(pictureFile);

            if (AssetPool.hasSprite(pictureFile)){
                throw new Exception("Asset already exists: " + pictureFile);
            }

            this.image = ImageIO.read(file);
            this.width = image.getWidth();
            this.height = image.getHeight();
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public Sprite(BufferedImage image, String pictureFile){
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pictureFile = pictureFile;
    }

    public Sprite (BufferedImage image, int row, int column, int index, String pictureFile){
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.row = row;
        this.column = column;
        this.index = index;
        this.isSubSprite = true;
        this.pictureFile = pictureFile;
    }

    @Override
    public void draw(Graphics2D g2){
        AffineTransform transform = new AffineTransform();
        transform.setToIdentity();
        transform.translate(gameObject.transform.position.x, gameObject.transform.position.y);
        transform.rotate(Math.toRadians(gameObject.transform.rotation), width * gameObject.transform.scale.x/2.0,
                height * gameObject.transform.scale.y/2.0);
        transform.scale(gameObject.transform.scale.x, gameObject.transform.scale.y);
        g2.drawImage(image, transform, null);

    }

    @Override
    public Component copy() {
        if (!isSubSprite)
            return new Sprite(this.image, pictureFile);
        else
            return new Sprite(this.image, this.row, this.column, this.index, pictureFile);
    }

    @Override
    public String serialize(int tabsize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Sprite", tabsize));
        builder.append(addBooleanProperty("isSubSprite", isSubSprite, tabsize + 1, true, true));

        if (isSubSprite){
            builder.append(addStringProperty("FilePath", pictureFile, tabsize + 1, true ,true));
            builder.append(addIntProperty("row", row, tabsize + 1, true, true));
            builder.append(addIntProperty("column", column, tabsize + 1, true, true));
            builder.append(addIntProperty("index", index, tabsize + 1, true, false));
            builder.append(closeObjectProperty(tabsize));

            return builder.toString();

        }

        builder.append(addStringProperty("FilePath", pictureFile, tabsize + 1, true ,false));
        builder.append(closeObjectProperty(tabsize));
        return builder.toString();
    }

    public static Sprite deserialize(){

        boolean isSubSprite = Parser.consumeBooleanProperty("isSubSprite");
        Parser.consume(',');
        String filePath = Parser.consumeStringProperty("FilePath");

        if (isSubSprite){
            Parser.consume(',');
            Parser.consumeIntProperty("row");
            Parser.consume(',');
            Parser.consumeIntProperty("column");
            Parser.consume(',');

            int index = Parser.consumeIntProperty("index");
            if (!AssetPool.hasSpriteSheet(filePath)){
                System.out.println("SpriteSheet '" + filePath + "' has not been loaded yet!");
                System.exit(-1);
            }
            Parser.consumeEndObjectProperty();
            return (Sprite) AssetPool.getSpriteSheet(filePath).sprites.get(index).copy();
        }
        if (!AssetPool.hasSprite(filePath)){
            System.out.println("Sprite '" + filePath + "' has not been loaded yet!");
            System.exit(-1);
        }
        Parser.consumeEndObjectProperty();
        return (Sprite) AssetPool.getSprite(filePath).copy();
    }
}
