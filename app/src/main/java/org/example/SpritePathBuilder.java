package org.example;

import java.io.File;
import java.util.Arrays;

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

    private static String buildImageName(Outfit outfit, int addonLevel) {
        int movement = adjustValue(outfit.getMovement());
        int direction = adjustValue(outfit.getDirection());
        int mountstate = determineMountState(outfit);

        return String.format("%d_%d_%d_%d", movement, mountstate, addonLevel, direction);
    }

    public static String getLooktypeCheckPath(Outfit outfit) {
        String path = getPathForLooktype(outfit.getLooktype()) + "1_1_1_1.png";
        return path;
    }

    public static String getOutfitPath(Outfit outfit, boolean isPlayer) {
        if (isPlayer) {
            String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 1) + ".png";
            return path;
        } else {
            String path = getPathForLooktype(outfit.getLooktype()) + String.format("%d_1_1_%d.png", 
                adjustValue(outfit.getMovement()), adjustValue(outfit.getDirection()));
            return path;
        }
    }

    public static String getOutfitTemplatePath(Outfit outfit, boolean isPlayer) {
        if (isPlayer) {
            String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 1) + "_template.png";
            return path;
        } else {
            String path = getPathForLooktype(outfit.getLooktype()) + String.format("%d_1_1_%d.png", 
                adjustValue(outfit.getMovement()), adjustValue(outfit.getDirection()));
            return path;
        }
    }

    public static String getAddon1Path(Outfit outfit, boolean isPlayer) {
        if (isPlayer) {
            String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 2) + ".png";
            return path;
        } else {
            // Não considera addon
            String path = getPathForLooktype(outfit.getLooktype()) + String.format("%d_1_1_%d.png", 
                adjustValue(outfit.getMovement()), adjustValue(outfit.getDirection()));
            return path;
        }
    }

    public static String getAddon1TemplatePath(Outfit outfit, boolean isPlayer) {
        if (isPlayer) {
            String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 2) + "_template.png";
            return path;
        } else {
            // Não considera addon
            String path = getPathForLooktype(outfit.getLooktype()) + String.format("%d_1_1_%d.png", 
                adjustValue(outfit.getMovement()), adjustValue(outfit.getDirection()));
            return path;
        }
    }

    public static String getAddon2Path(Outfit outfit, boolean isPlayer) {
        if (isPlayer) {
            String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 3) + ".png";
            return path;
        } else {
            // Não considera addon
            String path = getPathForLooktype(outfit.getLooktype()) + String.format("%d_1_1_%d.png", 
                adjustValue(outfit.getMovement()), adjustValue(outfit.getDirection()));
            return path;
        }
    }

    public static String getAddon2TemplatePath(Outfit outfit, boolean isPlayer) {
        if (isPlayer) {
            String path = getPathForLooktype(outfit.getLooktype()) + buildImageName(outfit, 3) + "_template.png";
            return path;
        } else {
            // Não considera addon
            String path = getPathForLooktype(outfit.getLooktype()) + String.format("%d_1_1_%d.png", 
                adjustValue(outfit.getMovement()), adjustValue(outfit.getDirection()));
            return path;
        }
    }

    public static String getMountPath(Outfit outfit, boolean isPlayer) {
        if (isPlayer) {
            int movement = adjustValue(outfit.getMovement());
            int direction = adjustValue(outfit.getDirection());
            String path = getPathForLooktype(adjustValue(outfit.getMount()))
                    + String.format("%d_1_1_%d.png", movement, direction);
            return path;
        } else {
            // Não considera mount
            int movement = adjustValue(outfit.getMovement());
            int direction = adjustValue(outfit.getDirection());
            String path = getPathForLooktype(outfit.getLooktype())
                    + String.format("%d_1_1_%d.png", movement, direction);
            return path;
        }
    }

    public static String getMountTemplatePath(Outfit outfit, boolean isPlayer) {
        if (isPlayer) {
            int movement = adjustValue(outfit.getMovement());
            int direction = adjustValue(outfit.getDirection());
            String path = getPathForLooktype(adjustValue(outfit.getMount()))
                    + String.format("%d_1_1_%d_template.png", movement, direction);
            return path;
        } else {
            // Não considera mount
            int movement = adjustValue(outfit.getMovement());
            int direction = adjustValue(outfit.getDirection());
            String path = getPathForLooktype(outfit.getLooktype())
                    + String.format("%d_1_1_%d_template.png", movement, direction);
            return path;
        }
    }

    public static OutfitPaths getAllPaths(Outfit outfit) {
        boolean isPlayer = isLooktypePlayer(getPathForLooktype(outfit.getLooktype()));
        OutfitPaths paths = new OutfitPaths();
        paths.setPath(BASE_DIR + outfit.getLooktype() + "/");
        paths.setLooktypeCheckPath(getLooktypeCheckPath(outfit));
        paths.setOutfitPath(getOutfitPath(outfit, isPlayer));
        paths.setOutfitTemplatePath(getOutfitTemplatePath(outfit, isPlayer));
        paths.setAddon1Path(getAddon1Path(outfit, isPlayer));
        paths.setAddon1TemplatePath(getAddon1TemplatePath(outfit, isPlayer));
        paths.setAddon2Path(getAddon2Path(outfit, isPlayer));
        paths.setAddon2TemplatePath(getAddon2TemplatePath(outfit, isPlayer));
        paths.setMountPath(getMountPath(outfit, isPlayer));
        paths.setMountTemplatePath(getMountTemplatePath(outfit, isPlayer));
        return paths;
    }

    public static void logAllPaths(Outfit outfit) {
        boolean isPlayer = isLooktypePlayer(getPathForLooktype(outfit.getLooktype()));
        System.out.println("Looktype Check Path: " + getLooktypeCheckPath(outfit));

        System.out.println("Outfit Path: " + getOutfitPath(outfit, isPlayer));
        System.out.println("Outfit Template Path: " + getOutfitTemplatePath(outfit, isPlayer));

        System.out.println("Addon 1 Path: " + getAddon1Path(outfit, isPlayer));
        System.out.println("Addon 1 Template Path: " + getAddon1TemplatePath(outfit, isPlayer));
        System.out.println("Addon 2 Path: " + getAddon2Path(outfit, isPlayer));
        System.out.println("Addon 2 Template Path: " + getAddon2TemplatePath(outfit, isPlayer));

        System.out.println("Mount Template Path: " + getMountTemplatePath(outfit, isPlayer));
        System.out.println("Mount Path: " + getMountPath(outfit, isPlayer));
    }

    public static Boolean isLooktypePlayer(String looktypePath) {
        File folder = new File(looktypePath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            return false; // Retorna falso se o diretório não existir ou estiver vazio
        }

        return Arrays.stream(listOfFiles)
                .anyMatch(file -> file.getName().contentEquals("1_2_1_1.png"));
    }
}