package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Rescaler {
    private Pixmap pixmap100;
    private Pixmap pixmap200;

    public Texture ReScale(String internalPath, Vector2 pixelSize) {
        int x = (int)pixelSize.x;
        int y = (int)pixelSize.y;
        pixmap200 = new Pixmap(Gdx.files.internal(internalPath));
        pixmap100 = new Pixmap(x, y, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );

        return new Texture(pixmap100);
    }

    public Texture ReScale(String internalPath, int x, int y) {
        pixmap200 = new Pixmap(Gdx.files.internal(internalPath));
        pixmap100 = new Pixmap(x, y, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );

        pixmap200.dispose();
        return new Texture(pixmap100);
    }
}
