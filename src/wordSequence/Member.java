package wordSequence;

import java.util.Random;

public class Member {
  private char[] charSequence;
  private int fitness;
  private double normalizedFitness;
  
  public Member(int sequenceLength) {
    charSequence = new char[sequenceLength];
    this.generateSequence();
  }
  
  public Member(char[] sequence) {
    this.charSequence = sequence;
  }
  
  public static char randomLetter() {
    Random letter = new Random();
    return (char) (letter.nextInt(122 + 1 - 97) + 97);
  }
  
  public void generateSequence() {
    for (int i = 0; i < charSequence.length; i++) {
      charSequence[i] = randomLetter();
    }
  }
  
  public char[] getSequence() {
    return charSequence;
  }
  
  public void setFitness(int fitness) {
    this.fitness = fitness;
  }
  
  public int getFitness() {
    return fitness;
  }
  
  public void setNormalizedFitness(double fitness) {
    this.normalizedFitness = fitness;
  }

  public double getNormalizedFitness() {
    return normalizedFitness;
  }
}