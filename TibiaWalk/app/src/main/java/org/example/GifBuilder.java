package org.example;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class GifBuilder {

    public static void createGif(Outfit outfit, String outputGifPath) throws IOException {
        // Contar o número de sprites disponíveis
        int spriteCount = SpriteCounter.countSpriteVariations(outfit);
        if (spriteCount == 0) {
            throw new IOException("No sprite variations found in the directory.");
        }

        BufferedImage[] frames = new BufferedImage[spriteCount];
        for (int i = 1; i <= spriteCount; i++) {
            outfit.setMovement(i); // Atualiza o movimento para cada sprite
            BufferedImage sprite = ImageMerger.mergeImages(outfit);
            frames[i - 1] = sprite;
        }

        saveGif(frames, outputGifPath);
    }

    private static void saveGif(BufferedImage[] frames, String outputGifPath) throws IOException {
        // Obter o ImageWriter para o formato GIF
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("gif");
        if (!writers.hasNext()) {
            throw new IOException("No GIF writer available");
        }

        ImageWriter gifWriter = writers.next();

        // Criar o fluxo de saída para o GIF
        try (ImageOutputStream output = ImageIO.createImageOutputStream(new File(outputGifPath))) {
            gifWriter.setOutput(output);
            gifWriter.prepareWriteSequence(null);

            // Configurar metadados do GIF
            IIOMetadata metadata = getGifMetadata(gifWriter, frames[0]);

            // Adicionar cada frame ao GIF
            for (BufferedImage frame : frames) {
                gifWriter.writeToSequence(new javax.imageio.IIOImage(frame, null, metadata), null);
            }

            gifWriter.endWriteSequence();
        }
    }

    private static IIOMetadata getGifMetadata(ImageWriter gifWriter, BufferedImage frame) throws IOException {
        IIOMetadata metadata = gifWriter.getDefaultImageMetadata(new javax.imageio.ImageTypeSpecifier(frame), null);
        String metaFormatName = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

        // Configurar informações de controle do GIF
        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
        graphicsControlExtensionNode.setAttribute("delayTime", "10"); // Delay entre frames em centésimos de segundo
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");

        // Configurar a extensão da aplicação para repetição contínua
        IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
        IIOMetadataNode appExtensionNode = new IIOMetadataNode("ApplicationExtension");
        appExtensionNode.setAttribute("applicationID", "NETSCAPE");
        appExtensionNode.setAttribute("authenticationCode", "2.0");

        int loopContinuously = 0; // 0 significa loop infinito
        appExtensionNode.setUserObject(new byte[] { 0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF) });
        appExtensionsNode.appendChild(appExtensionNode);

        metadata.setFromTree(metaFormatName, root);

        return metadata;
    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
                return ((IIOMetadataNode) rootNode.item(i));
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return node;
    }
}