package metro;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static boolean stop = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String fileName = args[0];
        JsonObject metroJsonObject = JsonParser.parseString(readAllFile(fileName))
                                              .getAsJsonObject();

        if (metroJsonObject == null) {
            System.out.println("Incorrect format");
            System.exit(0);
        }
        Metro.initInstanceFromJsonObject(metroJsonObject);
        
        while (!stop) {
            String command = sc.nextLine();
            
            if (isCorrectCommand(command)) {
                executeCommand(command);
            } else {
                System.out.println("Invalid command");
            }

        }
    }
    
    
    private static String readAllFile(String fleName) {
        try {
            return Files.readString(Path.of(fleName));
        } catch (NoSuchFileException e) {
            System.out.println("Error! Such a file doesn't exist!");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Incorrect file");
        System.exit(0);
        return null;
    }

    private static boolean isCorrectCommand(String command) {
        List<String> tokens = ArgumentTokenizer.tokenize(command);



        String fiveArgCommand = "/connect.*|/route.*|/fastest-route.*";
        if (command.matches(fiveArgCommand)) {
            // if the command is the connect command or route command, it must contain 5 arguments
            return tokens.size() == 5;
        }

        String fourArgCommand = "/add .*";
        if (command.matches(fourArgCommand)) {
            // if the command is the add command, it must contain 4 arguments
            return tokens.size() == 4;
        }

        String threeArgCommand = "/append.*|/add-head.*|/remove.*";
        if (command.matches(threeArgCommand)) {
            // if the command is the remove command, it must contain 3 arguments
            return tokens.size() == 3;
        }


        String twoArgCommand = "/output.*";
        if (command.matches(twoArgCommand)) {
            // if the command is the output command, it must contain 2 arguments
            return tokens.size() == 2;
        }

        String exitCommand = "/exit";
        if (command.matches(exitCommand)) {
            // if the command is exit command, it must be "/exit"
            return true;
        }

        return false;
    }

    private static void executeCommand(String command) {
        List<String> tokens = ArgumentTokenizer.tokenize(command);
        // token.get(0): command
        // token.get(1): line name
        // token.get(2): station name
        // token.get(3): line name 2
        // token.get(4): station name 2

        switch (tokens.get(0)) {
            case "/fastest-route": Metro.getInstance().fastestRoute(tokens.get(1), tokens.get(2), tokens.get(3), tokens.get(4)); break;
            case "/route": Metro.getInstance().route(tokens.get(1), tokens.get(2), tokens.get(3), tokens.get(4)); break;
            case "/add": Metro.getInstance().add(tokens.get(1), tokens.get(2), Integer.parseInt(tokens.get(3))); break;
            case "/connect": Metro.getInstance().connect(tokens.get(1), tokens.get(2), tokens.get(3), tokens.get(4)); break;
            case "/append": Metro.getInstance().addToTail(tokens.get(1), tokens.get(2)); break;
            case "/add-head": Metro.getInstance().addToHead(tokens.get(1), tokens.get(2)); break;
            case "/remove": Metro.getInstance().remove(tokens.get(1), tokens.get(2)); break;
            case "/output": Metro.getInstance().outputLine(tokens.get(1)); break;
            case "/exit": stop = true; break;
        }
    }
}