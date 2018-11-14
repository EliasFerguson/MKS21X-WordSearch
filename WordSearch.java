import java.util.*;
import java.io.*;
public class WordSearch {
  private int seed;
  private Random randgen;
  private ArrayList<String> wordsToAdd;
  private ArrayList<String> wordsAdded;
  private char[][] data;

  public WordSearch(int rows, int cols, String fileName, boolean answer) throws FileNotFoundException {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException();
    }
    data = new char[rows][cols];
    clear();
    wordsToAdd = readFile(fileName);
    addAllWords();
    if (!answer) {
      //fillBox();
    }
  }
    public WordSearch(int rows, int cols, String fileName, int randSeed, boolean answer) throws FileNotFoundException {
      if (rows <= 0 || cols <= 0) {
        throw new IllegalArgumentException();
      }
      data = new char[rows][cols];
      clear();
      wordsToAdd = readFile(fileName);
      addAllWords();
      if (!answer) {
        //fillBox();
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
     for (int i = 0; i < word.length(); i++) {
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
      return output + words;
    }
    public void addAllWords() {
      int tries = 0;

    }
  }
