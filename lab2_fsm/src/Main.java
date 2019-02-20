import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int amountFiniteStates; //количество конечных состояний
        HashSet<Integer> finiteStates = new HashSet<>(); //множество конечных состояний
        String line;

        /*Map для описания конечного автомата: ключ - некоторое состояние,
        * значение - ещё один Map, в котором ключ - функция перехода,
        * значение - результат перехода*/
        HashMap<Integer, HashMap<Character, Integer>> machine = new HashMap<>();
        HashMap<Character, Integer> buffer; //буфер для получения всех переходов и состояний-результатов перехода
        Character letter;
        Integer currentState = 1; //состояние автомата в некоторый момент времени


        if (args.length != 2) {
            System.err.println("Error: wrong amount of arguments, terminating the program.");
            System.exit(-1);
        }

        try (
                Scanner sc1 = new Scanner(new File(args[1]));
                Scanner sc0 = new Scanner(new File(args[0]))
        ) {
            /*Считывание конечных состояний автомата*/
            amountFiniteStates = sc0.nextInt();
            for (int i = 0; i < amountFiniteStates; i++) {
                finiteStates.add(sc0.nextInt());
            }
            sc0.nextLine();

            /*Считывание описания конечного автомата*/
            while (sc0.hasNextLine()) {
                line = sc0.nextLine();
                String[] machineProcedure = line.split(" ");
                Integer startState = Integer.parseInt(machineProcedure[0]);
                letter = machineProcedure[1].charAt(0);
                Integer endState = Integer.parseInt(machineProcedure[2]);

                if (machine.containsKey(startState)) {
                    buffer = machine.get(startState);
                }
                else {
                    buffer = new HashMap<>();
                }
                buffer.put(letter, endState);
                machine.put(startState, buffer);
            }

            /*Распознавание строки*/
            String string = sc1.nextLine();
            for (int i = 0; i < string.length(); i++) {
                letter = string.charAt(i);
                if (!machine.containsKey(currentState)) {
                    System.err.println("Error: machine doesn't have that state!");
                    System.exit(-2);
                }
                else {
                    buffer = machine.get(currentState);
                    if (!buffer.containsKey(letter)) {
                        System.err.println("Error: machine can't recognize that symbol!");
                        System.exit(-3);
                    }
                    else {
                        currentState = buffer.get(letter);
                    }
                }
            }

            if (!finiteStates.contains(currentState)) {
                System.err.println("Error: machine didn't stop in machine finite state! String hasn't been recognized!");
                System.exit(-4);
            }
            else {
                System.out.println("String has been recognized: " + string);
            }
        } catch (FileNotFoundException eFileNotFound) {
            System.err.println("Error: can't open file");
        }
    }
}
