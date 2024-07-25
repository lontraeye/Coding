package org.example;

public class SpritePathBuilder {

    private static final String BASE_DIR = "app/src/main/resources/outfitSprites/";

    static String getPathForLooktype(int looktype) {
        return BASE_DIR + looktype + "/";
    }

    private static int adjustValue(int value) {
        return (value == 0) ? 1 : value;
    }

    private static int determineMountState(Outfit outfit) {
        return (outfit.getMount() > 0) ? 2 : 1;
    }

    // Método para construir o nome do arquivo da imagem com base nas variáveis do Outfit
    private static String buildImageName(Outfit outfit, int addonLevel) {
        int movement = adjustValue(outfit.getMovement());
        int direction = adjustValue(outfit.getDirection());
        int mountstate = determineMountState(outfit);

        return String.format("%d_%d_%d_%d", movement, mountstate, addonLevel, direction);
    }

    // Métodos para construir os caminhos para cada tipo de sprite
    public static String getLooktypeCheckPath(Outfit outfit) {
        String path = getPathForLooktype(outfit.getLooktype()) + "1_1_1_1.png";
        return path;
    }

    public static String getOutfitPath(Outfit outfit) {
        String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 1) + ".png";
        return path;
    }

    public static String getOutfitTemplatePath(Outfit outfit) {
        String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 1) + "_template.png";
        return path;
    }

    public static String getAddon1Path(Outfit outfit) {
        String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 2) + ".png";
        return path;
    }

    public static String getAddon1TemplatePath(Outfit outfit) {
        String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 2) + "_template.png";
        return path;
    }

    public static String getAddon2Path(Outfit outfit) {
        String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 3) + ".png";
        return path;
    }

    public static String getAddon2TemplatePath(Outfit outfit) {
        String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 3) + "_template.png";
        return path;
    }

    public static String getMountPath(Outfit outfit) {
        int movement = adjustValue(outfit.getMovement());
        int direction = adjustValue(outfit.getDirection());
        String path = getPathForLooktype(adjustValue(outfit.getMount())) + String.format("%d_1_1_%d.png", movement, direction);
        return path;
    }

    public static String getMountTemplatePath(Outfit outfit) {
        int movement = adjustValue(outfit.getMovement());
        int direction = adjustValue(outfit.getDirection());
        String path = getPathForLooktype(adjustValue(outfit.getMount())) + String.format("%d_1_1_%d_template.png", movement, direction);
        return path;
    }

    /// Método que retorna um objeto OutfitPaths com todos os paths
    public static OutfitPaths getAllPaths(Outfit outfit) {
        OutfitPaths paths = new OutfitPaths();
        paths.setPath(BASE_DIR + outfit.getLooktype() + "/");
        paths.setLooktypeCheckPath(getLooktypeCheckPath(outfit));
        paths.setOutfitPath(getOutfitPath(outfit));
        paths.setOutfitTemplatePath(getOutfitTemplatePath(outfit));
        paths.setAddon1Path(getAddon1Path(outfit));
        paths.setAddon1TemplatePath(getAddon1TemplatePath(outfit));
        paths.setAddon2Path(getAddon2Path(outfit));
        paths.setAddon2TemplatePath(getAddon2TemplatePath(outfit));
        paths.setMountPath(getMountPath(outfit));
        paths.setMountTemplatePath(getMountTemplatePath(outfit));
        return paths;
    }

    public static void logAllPaths(Outfit outfit) {
        System.out.println("Looktype Check Path: " + getLooktypeCheckPath(outfit));
        
        System.out.println("Outfit Path: " + getOutfitPath(outfit));
        System.out.println("Outfit Template Path: " + getOutfitTemplatePath(outfit));

        System.out.println("Addon 1 Path: " + getAddon1Path(outfit));
        System.out.println("Addon 1 Template Path: " + getAddon1TemplatePath(outfit));
        System.out.println("Addon 2 Path: " + getAddon2Path(outfit));
        System.out.println("Addon 2 Template Path: " + getAddon2TemplatePath(outfit));

        System.out.println("mount Template Path: " + getMountTemplatePath(outfit));
        System.out.println("Mount Path: " + getMountPath(outfit));
    }
}