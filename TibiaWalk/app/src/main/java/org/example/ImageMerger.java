package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageMerger {

    private static void overlay(BufferedImage base, BufferedImage overlay) {
        Graphics g = base.getGraphics();
        g.drawImage(overlay, 0, 0, null);
        g.dispose();
    }

    public static BufferedImage mergeImages(Outfit outfit) throws IOException {
        ImageColorizer colorizer = new ImageColorizer(outfit.getHead(), outfit.getBody(), outfit.getLegs(), outfit.getFeet());

        // Busca o Path das imagens
        OutfitPaths paths = SpritePathBuilder.getAllPaths(outfit);

        // Carregar todas as imagens
        BufferedImage looktypeCheckImg = ImageIO.read(new File(paths.getLooktypeCheckPath()));
        BufferedImage outfitImg = ImageIO.read(new File(paths.getOutfitPath()));
        BufferedImage addon1Img = ImageIO.read(new File(paths.getAddon1Path()));
        BufferedImage addon2Img = ImageIO.read(new File(paths.getAddon2Path()));

        // Declarar mountImg e inicializá-la com null
        BufferedImage mountImg = null;

        // Carregar imagem da montaria se outfit.getMount() > 0
        if (outfit.getMount() > 0) {
            mountImg = ImageIO.read(new File(paths.getMountPath()));
        }

        BufferedImage outfitTplImg = ImageIO.read(new File(paths.getOutfitTemplatePath()));
        BufferedImage addon1TplImg = ImageIO.read(new File(paths.getAddon1TemplatePath()));
        BufferedImage addon2TplImg = ImageIO.read(new File(paths.getAddon2TemplatePath()));

        // Verificar a largura e altura da imagem base
        int width = looktypeCheckImg.getWidth();
        int height = looktypeCheckImg.getHeight();

        // Criar uma nova imagem com a mesma largura e altura
        BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Adicionar montaria se houver
        if (mountImg != null) {
            BufferedImage mountImage = getMountImage(mountImg);

            Graphics g = mergedImage.getGraphics();
            g.drawImage(mountImage, 0, 0, null);
            g.dispose();
        }

        // Desenhar a imagem principal
        Graphics g = mergedImage.getGraphics();
        g.drawImage(outfitImg, 0, 0, null);
        g.dispose();

        // Pintar o template
        
        BufferedImage coloredTemplate = colorizer.applyColors(outfitTplImg, outfitImg);

        // Mesclar o template colorido sobre o outfit original
        overlay(mergedImage, coloredTemplate);

        // Adicionar addons
        if (outfit.getAddons() == 1 || outfit.getAddons() == 3) {
            overlay(mergedImage, addon1Img);
        }
        if (outfit.getAddons() == 2 || outfit.getAddons() == 3) {
            overlay(mergedImage, addon2Img);
        }

        // Ajustar para 64x64 se necessário
        if (mergedImage.getWidth() < 64) {
            BufferedImage base = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = base.createGraphics();
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.drawImage(mergedImage, 32, 32, null);
            g2d.dispose();
            mergedImage = base;
        }

        return mergedImage;
    }

    private static BufferedImage getMountImage(BufferedImage mountImg) {
        // Transformar 32x32 em 64x64 se necessário
        if (mountImg.getWidth() < 64) {
            BufferedImage baseMount = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = baseMount.createGraphics();
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.drawImage(mountImg, 32, 32, null);
            g2d.dispose();
            return baseMount;
        }
        return mountImg;
    }

    public static void saveImage(BufferedImage image, String outputPath) throws IOException {
        File outputfile = new File(outputPath);
        ImageIO.write(image, "png", outputfile);
    }
}