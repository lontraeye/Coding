package org.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageColorizer {

    private final Color headColor;
    private final Color bodyColor;
    private final Color legsColor;
    private final Color feetColor;

    private static final Color[] SOLID_COLORS = {
        Color.YELLOW, // Amarelo
        Color.RED,    // Vermelho
        Color.GREEN,  // Verde
        Color.BLUE    // Azul
    };

    public ImageColorizer(String headColorHex, String bodyColorHex, String legsColorHex, String feetColorHex) {
        this.headColor = Color.decode("#" + headColorHex);
        this.bodyColor = Color.decode("#" + bodyColorHex);
        this.legsColor = Color.decode("#" + legsColorHex);
        this.feetColor = Color.decode("#" + feetColorHex);
    }

    public BufferedImage applyColors(BufferedImage templateImage, BufferedImage outfitImage) {
        if (templateImage.getWidth() != outfitImage.getWidth() ||
            templateImage.getHeight() != outfitImage.getHeight()) {
            throw new IllegalArgumentException("As imagens devem ter o mesmo tamanho");
        }

        BufferedImage coloredImage = new BufferedImage(templateImage.getWidth(), templateImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = coloredImage.createGraphics();
        g2d.drawImage(templateImage, 0, 0, null);

        for (int y = 0; y < templateImage.getHeight(); y++) {
            for (int x = 0; x < templateImage.getWidth(); x++) {
                int templateRGB = templateImage.getRGB(x, y);
                Color templateColor = new Color(templateRGB, true);

                if (isSolidColor(templateColor)) {
                    Color replacementColor = getReplacementColor(templateColor, outfitImage.getRGB(x, y));
                    coloredImage.setRGB(x, y, replacementColor.getRGB());
                }
            }
        }

        g2d.dispose();
        return coloredImage;
    }

    private boolean isSolidColor(Color color) {
        for (Color solidColor : SOLID_COLORS) {
            if (color.equals(solidColor)) {
                return true;
            }
        }
        return false;
    }

    private Color getReplacementColor(Color templateColor, int outfitRGB) {
        Color baseColor;
        if (templateColor.equals(Color.YELLOW)) {
            baseColor = headColor;
        } else if (templateColor.equals(Color.RED)) {
            baseColor = bodyColor;
        } else if (templateColor.equals(Color.GREEN)) {
            baseColor = legsColor;
        } else if (templateColor.equals(Color.BLUE)) {
            baseColor = feetColor;
        } else {
            return templateColor; // Retorna a cor original se não for uma cor sólida
        }

        // Ajusta a cor base com base na tonalidade do pixel da imagem outfit
        return adjustColor(baseColor, outfitRGB);
    }

    private Color adjustColor(Color baseColor, int outfitRGB) {
        Color outfitColor = new Color(outfitRGB, true);

        // Calcula o brilho da cor do outfit
        float[] hsbOutfit = Color.RGBtoHSB(outfitColor.getRed(), outfitColor.getGreen(), outfitColor.getBlue(), null);
        float brightnessOutfit = hsbOutfit[2];

        // Calcula o brilho da cor base
        float[] hsbBase = Color.RGBtoHSB(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), null);
        float brightnessBase = hsbBase[2];

        // Ajusta a cor base para ter o mesmo brilho da cor do outfit
        hsbBase[2] = brightnessOutfit;

        // Converte de volta para RGB e retorna a cor ajustada
        int rgb = Color.HSBtoRGB(hsbBase[0], hsbBase[1], hsbBase[2]);
        return new Color(rgb);
    }
}