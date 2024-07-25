package org.example;

import java.io.File;

public class SpriteCounter {

    public static int countSpriteVariations(Outfit outfit) {
        // String directoryPath = SpritePathBuilder.getPathForLooktype(outfit.getLooktype());
        String directoryPath = SpritePathBuilder.getPathForLooktype(outfit.getLooktype());

        int mountState = outfit.getMount() > 0 ? 2 : 1;
        String baseNamePattern = String.format("_1_1_%d.png", mountState, outfit.getDirection());

        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return 0;
        }

        int count = 0;
        while (true) {
            String fileName = (count + 1) + baseNamePattern;
            File file = new File(dir, fileName);
            if (!file.exists()) {
                break;
            }
            count++;
        }

        return count;
    }
}