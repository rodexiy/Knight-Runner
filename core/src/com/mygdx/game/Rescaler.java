package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Uma classe utilitária para redimensionar texturas.
 */
public class Rescaler {
    private Pixmap pixmap100;
    private Pixmap pixmap200;

    /**
     * Redimensiona uma textura para as dimensões especificadas.
     *
     * @param internalPath O caminho interno da textura.
     * @param pixelSize     O tamanho desejado da textura em pixels (largura x altura).
     * @return A textura redimensionada.
     */
    public Texture ReScale(String internalPath, Vector2 pixelSize) {
        int x = (int) pixelSize.x;
        int y = (int) pixelSize.y;
        pixmap200 = new Pixmap(Gdx.files.internal(internalPath));
        pixmap100 = new Pixmap(x, y, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );

        return new Texture(pixmap100);
    }

    /**
     * Redimensiona uma textura para as dimensões especificadas.
     *
     * @param internalPath O caminho interno da textura.
     * @param x            A largura desejada da textura em pixels.
     * @param y            A altura desejada da textura em pixels.
     * @return A textura redimensionada.
     */
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
