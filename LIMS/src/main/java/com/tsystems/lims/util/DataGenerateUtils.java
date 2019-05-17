package com.tsystems.lims.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataGenerateUtils {
    public static String generateInsurance() {
        return UUID.randomUUID().toString();
    }

    public static List<String> getFilesAsList(String path) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            result.add(strLine);
        }
        br.close();
        return result;
    }

    public static <T> T getRandomElementFromList(List<T> entities) {
        return entities.get(new Random().nextInt(entities.size()));
    }

    public static <T> Set<T> getRandomSetOfElementsFromList(List<T> entities) {
        int size = new Random().nextInt(4);
        Set<T> result = new HashSet<T>();
        for (int i = 0; i < size; i++)
            result.add(getRandomElementFromList(entities));
        return result;
    }

    public static String prepareName(String raw) {
        return raw.substring(0, 1) + raw.substring(1).toLowerCase();
    }
}
