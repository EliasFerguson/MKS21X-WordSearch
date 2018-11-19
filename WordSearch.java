import java.util.*;
import java.io.*;
public class WordSearch {
  private int seed;
  private Random randgen;
  private ArrayList<String> wordsToAdd;
  private ArrayList<String> wordsAdded;
  private char[][] data;
  private String invalidDims = "Invalid Dimensions for the WordSearch, both rols and cols must be above 0.";
  private String invalidSeed = "Invaid seed, seed must be between 0 and 10000 inclusive.";
  private String textMessage = "That text file doesn't exist!";
  public static void main(String[] args) {
    String formatMessage = "Incorrect Format, Use: java WordSearch <#rows> <#cols> <filename.txt> <OPTIONAL seed#> <OPTIONAL key>.";
    WordSearch ws1;
    boolean key = false;
    int len  = args.length;
    try {
      if (args.length == 3) {
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        String file = args[2];
        ws1 = new WordSearch(rows, cols, file, key);
        System.out.println(ws1.toString());
      }
      if (args.length == 4) {
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        String file = args[2];
        int seed = Integer.parseInt(args[3]);
        ws1 = new WordSearch(rows, cols, file, seed, key);
        System.out.println(ws1.toString());
      }
      if (args.length > 4) {
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        String file = args[2];
        int seed = Integer.parseInt(args[3]);
        if (args[4].compareTo("key") == 0) {
          key = true;
        }
        ws1 = new WordSearch(rows, cols, file, seed, key);
        System.out.println(ws1.toString());
      }
    }
    catch (NumberFormatException e) {
      System.out.println(formatMessage);
    }
  }
  public WordSearch(int rows, int cols, String fileName, boolean answer) {
    if (rows <= 0 || cols <= 0) {
      System.out.println(invalidDims);
    }
    wordsToAdd = new ArrayList<String>();
    wordsAdded = new ArrayList<String>();
    data = new char[rows][cols];
    clear();
    Random seedCreation = new Random();
    seed = seedCreation.nextInt() % 10001;
    randgen = new Random(seed);
    try {
      wordsToAdd = readFile(fileName);
    }
    catch (FileNotFoundException badFile) {
      System.out.println(textMessage);
    }
    addAllWords();
    if (!answer) {
      fillBoxLetter();
    }
    else {
      fillBoxSpace();
    }
  }
    public WordSearch(int rows, int cols, String fileName, int randSeed, boolean answer) {
      if (rows <= 0 || cols <= 0) {
        System.out.println(invalidDims);
      }
      if (seed < 0 || seed > 10000) {
        throw new IllegalArgumentException(invalidSeed);
      }
      wordsToAdd = new ArrayList<String>();
      wordsAdded = new ArrayList<String>();
      data = new char[rows][cols];
      clear();
      Random seedCreation = new Random();
      seed = Math.abs(seedCreation.nextInt() % 10001);
      randgen = new Random(seed);
      try {
        wordsToAdd = readFile(fileName);
      }
      catch (FileNotFoundException badFile) {
        System.out.println(textMessage);
      }
      addAllWords();
      if (!answer) {
        fillBoxLetter();
      }
      else {
        fillBoxSpace();
      }
    }

    private void clear() {
      for (int i = 0; i < data.length; i++) {
        for (int i2 = 0; i2 < data[i].length; i2++) {
          data[i][i2] = '_';
        }
      }
    }

    public ArrayList<String> readFile(String fileName) throws FileNotFoundException {
      ArrayList<String> output = new ArrayList<String>();
      File f = new File(fileName);
      Scanner in = new Scanner(f);
      while (in.hasNext()) {
        String word = in.next().toUpperCase();
        output.add(word);
      }
      return output;
    }

    public boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
     word = word.toUpperCase();
     int len = word.length();
     if (rowIncrement == 0 && colIncrement == 0) {
       return false;
     }
     if (len * rowIncrement + row > data.length || len * colIncrement + col > data[0].length) {
       return false;
     }
     if ((len - 1) * rowIncrement + row < 0 || (len - 1) * colIncrement + col < 0) {
       return false;
     }
     if (len == 0 || word.contains(" ")) {
       return false;
     }
     for (int i = 0; i < len; i++) {
       if (row + i * rowIncrement < 0 || col + i * colIncrement < 0) {
         return false;
       }
       if (data[row + i * rowIncrement][col + i * colIncrement] != word.charAt(i) && data[row + i * rowIncrement][col + i * colIncrement] != '_') {
         return false;
       }
     }
     for (int i = 0; i < word.length(); i++) {
       data[row + i * rowIncrement][col + i * colIncrement] = word.charAt(i);
     }
     return true;
   }

    public String toString() {
      String output = "";
      for (int i = 0; i < data.length; i++) {
        for (int i2 = 0; i2 < data[i].length; i2++) {
          if (i2 == 0) {
            output += "|" + data[i][i2] + " ";
          }
          else if (i2 == data[i].length - 1) {
            output += data[i][i2] + "|\n";
          }
          else {
            output += data[i][i2] + " ";
          }
        }
      }
      String words = "Words:";
      for (int i = 0; i < wordsAdded.size() - 1; i++) {
        words += wordsAdded.get(i) + " ";
      }
      return output + words + "\nSeed: " + seed;
    }
    public void addAllWords() {
      int rows = data.length;
      int cols = data[0].length;
      for (int i = 0; i < wordsToAdd.size(); i++) {
        int rowIncrement = randgen.nextInt() % 2;
        int colIncrement = randgen.nextInt() % 2;
        int tries = 100;
        while (tries > 0) {
          String inQuestion = wordsToAdd.get(i);
          int startRow = randgen.nextInt() % rows;
          int startCol = randgen.nextInt() % cols;
          if (addWord(inQuestion, startRow, startCol, rowIncrement, colIncrement)) {
            tries = 0;
            wordsAdded.add(inQuestion);
          }
          else {
            tries -= 1;
          }
        }
      }
    }
    public void fillBoxLetter() {
      String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      for (int i = 0; i < data.length; i++) {
        for (int i2 = 0; i2 < data[i].length; i2++) {
          if (data[i][i2] == '_') {
            int rndm = Math.abs(randgen.nextInt()) % 26;
            data[i][i2] = letters.charAt(rndm);
          }
        }
      }
    }
    public void fillBoxSpace() {
      for (int i = 0; i < data.length; i++) {
        for (int i2 = 0; i2 < data[i].length; i2++) {
          if (data[i][i2] == '_') {
            data[i][i2] = ' ';
          }
        }
      }
    }
  }
