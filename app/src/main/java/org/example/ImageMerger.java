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
        BufferedImage addon1Img = null;
        BufferedImage addon2Img = null;

        // Carregar addons se existirem
        if (outfit.getAddons() == 1 || outfit.getAddons() == 3) {
            addon1Img = ImageIO.read(new File(paths.getAddon1Path()));
        }
        if (outfit.getAddons() == 2 || outfit.getAddons() == 3) {
            addon2Img = ImageIO.read(new File(paths.getAddon2Path()));
        }

        BufferedImage mountImg = null;
        if (outfit.getMount() > 0) {
            mountImg = ImageIO.read(new File(paths.getMountPath()));
        }

        BufferedImage outfitTplImg = ImageIO.read(new File(paths.getOutfitTemplatePath()));
        BufferedImage addon1TplImg = null;
        BufferedImage addon2TplImg = null;

        // Carregar templates de addons se existirem
        if (outfit.getAddons() == 1 || outfit.getAddons() == 3) {
            addon1TplImg = ImageIO.read(new File(paths.getAddon1TemplatePath()));
        }
        if (outfit.getAddons() == 2 || outfit.getAddons() == 3) {
            addon2TplImg = ImageIO.read(new File(paths.getAddon2TemplatePath()));
        }

        BufferedImage mountTplImg = null;
        try {
            mountTplImg = ImageIO.read(new File(paths.getMountTemplatePath()));
        } catch (IOException e) {
            // O arquivo do template da montaria não foi encontrado
            // Não fazer nada, apenas continuar
        }

        // Verificar a largura e altura da imagem base
        int width = looktypeCheckImg.getWidth();
        int height = looktypeCheckImg.getHeight();

        // Criar uma nova imagem com a mesma largura e altura
        BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Adicionar montaria se houver
        if (mountImg != null) {
            Graphics g = mergedImage.getGraphics();
            g.drawImage(mountImg, 0, 0, null);
            g.dispose();
        }

        // Desenhar a imagem principal
        Graphics g = mergedImage.getGraphics();
        g.drawImage(outfitImg, 0, 0, null);
        g.dispose();

        // Pintar o template
        BufferedImage coloredTemplate = colorizer.applyColors(outfitTplImg, outfitImg);
        overlay(mergedImage, coloredTemplate);

        // Pintar e mesclar addons se existirem
        if (addon1Img != null && addon1TplImg != null) {
            BufferedImage coloredAddonTemplate = colorizer.applyColors(addon1TplImg, addon1Img);
            overlay(addon1Img, coloredAddonTemplate);
            overlay(mergedImage, addon1Img);
        }

        if (addon2Img != null && addon2TplImg != null) {
            BufferedImage coloredAddon2Template = colorizer.applyColors(addon2TplImg, addon2Img);
            overlay(addon2Img, coloredAddon2Template);
            overlay(mergedImage, addon2Img);
        }

        // Aplicar cores ao template da montaria, se ele existir
        if (mountTplImg != null && mountImg != null) {
            BufferedImage coloredMountTemplate = colorizer.applyColors(mountTplImg, mountImg);
            overlay(mergedImage, coloredMountTemplate);
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

    public static void saveImage(BufferedImage image, String outputPath) throws IOException {
        File outputfile = new File(outputPath);
        ImageIO.write(image, "png", outputfile);
    }
}
