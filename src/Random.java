public class Random {
  /**This class random picks a number between the min and max numbers you provide
    * @param min & max
    * @return x
   */
   
  private int max;
  private int min;
   
  public Random(int l, int h ) { 
    max = h;
    min = l;
  }
   
  /*
   * This method randomly generates the inputed numbers given
   */
  public int genRandom(){
      return (int) Math.floor(Math.random() * max + min);
  }
}
