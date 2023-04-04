package dataMethods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FileWork {
    public static String userDir = System.getProperty("user.dir");

    public static List<String> fileReading (String filePathNames) {
        List<String> names;
        try {
            names = Files.lines(Paths.get(filePathNames)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    public static String randomName (List <String> name) {
        Random randomName = new Random();
        return name.get(randomName.nextInt(name.size()));
    }
}
