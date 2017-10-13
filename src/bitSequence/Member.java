package bitSequence;

import java.util.Random;

public class Member {
  private int[] bitSequence;
  private int fitness;
  private double normalizedFitness;
  
  public Member(int sequenceLength) {
    bitSequence = new int[sequenceLength];
    this.generateSequence();
  }
  
  public Member(int[] sequence) {
    this.bitSequence = sequence;
  }
  
  private int randomBit() {
    Random bit = new Random();
    return bit.nextInt(2);
  }
  
  public void generateSequence() {
    for (int i = 0; i < bitSequence.length; i++) {
      bitSequence[i] = randomBit();
    }
  }
  
  public int[] getSequence() {
    return bitSequence;
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