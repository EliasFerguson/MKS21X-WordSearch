import java.util.*;
import java.io.*;
public class WordSearch {
  private int seed;
  private Random randgen;
  private ArrayList<String> wordsToAdd;
  private ArrayList<String> wordsAdded;
  private char[][] data;
  public static void main(String[] args) throws FileNotFoundException {
    boolean key = false;
    if ((args.length == 5) && args[4].compareTo("key") == 0) {
      key = true;
    }
    int rc = Integer.parseInt(args[0]);
    int cc = Integer.parseInt(args[1]);
    WordSearch ws1 = new WordSearch(rc, cc, args[2], false);
    System.out.println(ws1.toString());
  }
  public WordSearch(int rows, int cols, String fileName, boolean answer) throws FileNotFoundException {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException();
    }
    wordsToAdd = new ArrayList<String>();
    wordsAdded = new ArrayList<String>();
    data = new char[rows][cols];
    clear();
    randgen = new Random();
    seed = Math.abs(randgen.nextInt() % 10001);
    wordsToAdd = readFile(fileName);
    addAllWords();
    if (!answer) {
      fillBoxLetter();
    }
    else {
      fillBoxSpace();
    }
  }
    public WordSearch(int rows, int cols, String fileName, int randSeed, boolean answer) throws FileNotFoundException {
      if (rows <= 0 || cols <= 0) {
        throw new IllegalArgumentException();
      }
      wordsToAdd = new ArrayList<String>();
      wordsAdded = new ArrayList<String>();
      data = new char[rows][cols];
      clear();
      randgen = new Random(randSeed);
      seed = randSeed;
      wordsToAdd = readFile(fileName);
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
            output += "|" + data[i][i2];
          }
          else {
            output += data[i][i2] + " ";
          }
        }
        output += "|\n";
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
        for (int i2 = 0; i < data[i].length; i2++) {
          if (data[i][i2] != '_') {
            data[i][i2] = ' ';
          }
        }
      }
    }
  }
