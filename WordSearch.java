import java.util.*;
import java.io.*;
public class WordSearch {
  private int seed;
  private Random randgen;
  private ArrayList<String> wordsToAdd;
  private ArrayList<String> wordsAdded;
  private char[][] data;

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    public WordSearch(int rows, int cols, String fileName) throws FileNotFoundException {
      if (rows < 0 || cols < 0) {
        throw new IllegalArgumentException();
      }
      randgen = new Random();
      seed = randgen.nextInt();
      File f = new File(fileName);
      Scanner in = new Scanner(f);
      while (in.hasNext()) {
        String word = in.next();
        wordsToAdd.add(word);
      }
      data = new char[rows][cols];
      clear();
      //addAllWords();
    }
    public WordSearch(int rows, int cols, String fileName, int randSeed) throws FileNotFoundException {
      if (rows < 0 || cols < 0) {
        throw new IllegalArgumentException();
      }
      randgen = new Random(randSeed);
      seed = randgen.nextInt();
      File f = new File(fileName);
      Scanner in = new Scanner(f);
      while (in.hasNext()) {
        String word = in.next();
        wordsToAdd.add(word);
      }
      data = new char[rows][cols];
      clear();
      //addAllWords();
    }

    /**Set all values in the WordSearch to underscores'_'*/
    private void clear() {
      for (int i = 0; i < data.length; i++) {
        for (int i2 = 0; i2 < data[i].length; i2++) {
          data[i][i2] = '_';
        }
      }
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

   /*[rowIncrement,colIncrement] examples:
    *[-1,1] would add up and the right because (row -1 each time, col + 1 each time)
    *[ 1,0] would add downwards because (row+1), with no col change
    *[ 0,-1] would add towards the left because (col - 1), with no row change
    */
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













  //IRRELEVANT PARTS :(
  /**Each row is a new line, there is a space between each letter
   *@return a String with each character separated by spaces, and rows
   *separated by newlines.
   */
  /* public String toString(){
    String output = "";
    for (int i = 0; i < data.length; i++) {
      for (int i2 = 0; i2 < data[i].length; i2++) {
        output += data[i][i2] + " ";
      }
      output += "\n";
    }
    return output;
  }
  */

  /**Attempts to add a given word to the specified position of the WordGrid.
   *The word is added from left to right, must fit on the WordGrid, and must
   *have a corresponding letter to match any letters that it overlaps.
   *
   *@param word is any text to be added to the word grid.
   *@param row is the vertical locaiton of where you want the word to start.
   *@param col is the horizontal location of where you want the word to start.
   *@return true when the word is added successfully. When the word doesn't fit,
   * or there are overlapping letters that do not match, then false is returned
   * and the board is NOT modified.
   */
  public boolean addWordHorizontal(String word,int row, int col){
    if ((row < 0 || col < 0) || (row >= data.length || col >= data[0].length) || (word.length() + col > data[row].length)) {
      return false;
    }
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) != data[row][col + i] && data[row][col + i] != '_') {
        return false;
      }
    }
    for (int i = 0; i < word.length(); i++) {
      data[row][col + i] = word.charAt(i);
    }
    return true;
  }

 /**Attempts to add a given word to the specified position of the WordGrid.
   *The word is added from top to bottom, must fit on the WordGrid, and must
   *have a corresponding letter to match any letters that it overlaps.
   *
   *@param word is any text to be added to the word grid.
   *@param row is the vertical locaiton of where you want the word to start.
   *@param col is the horizontal location of where you want the word to start.
   *@return true when the word is added successfully. When the word doesn't fit,
   *or there are overlapping letters that do not match, then false is returned.
   *and the board is NOT modified.
   */
  public boolean addWordVertical(String word,int row, int col){
    if ((row < 0 || col < 0) || (row >= data.length || col >= data[0].length) || (word.length() + row > data.length))  {
      return false;
    }
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) != data[row + i][col] && data[row + i][col] != '_') {
        return false;
      }
    }
    for (int i = 0; i < word.length(); i++) {
      data[row + i][col] = word.charAt(i);
    }
    return true;
  }
  /**Attempts to add a given word to the specified position of the WordGrid.
*The word is added from top left to bottom right, must fit on the WordGrid,
*and must have a corresponding letter to match any letters that it overlaps.
*
*@param word is any text to be added to the word grid.
*@param row is the vertical locaiton of where you want the word to start.
*@param col is the horizontal location of where you want the word to start.
*@return true when the word is added successfully. When the word doesn't fit,
*or there are overlapping letters that do not match, then false is returned.
*/
public boolean addWordDiagonal(String word,int row, int col){
 if ((row < 0 || col < 0) || (row >= data.length || col >= data[0].length) || (word.length() + row > data.length))  {
   return false;
 }
 if (word.length() + row > data.length || word.length() + col > data[0].length) {
   return false;
 }
  for (int i = 0; i < word.length(); i++) {
    if (data[row + i][col + i] != word.charAt(i) && data[row + i][col + i] != '_') {
      return false;
    }
  }
  for (int i = 0; i < word.length(); i++) {
    data[row + i][col + i] = word.charAt(i);
  }
  return true;
}
/**Attempts to add a given word to the specified position of the WordGrid.
  *The word is added in the direction rowIncrement,colIncrement
  *Words must have a corresponding letter to match any letters that it overlaps.
  *
  *@param word is any text to be added to the word grid.
  *@param row is the vertical locaiton of where you want the word to start.
  *@param col is the horizontal location of where you want the word to start.
  *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
  *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction
  *@return true when: the word is added successfully.
  *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
  *        OR there are overlapping letters that do not match
  */

}
