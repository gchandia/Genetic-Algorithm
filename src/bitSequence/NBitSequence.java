package bitSequence;

import java.util.Arrays;
import java.util.Random;

public class NBitSequence {
  private int totalGenerations = 1;
  
  private int compareSequences(int[] a, int[] b) {
    int counter = 0;
    
    for (int i = 0; i < a.length; i++) {
      if (a[i] == b[i]) {
        ++counter;
      }
    }
    
    return counter;
  }
  
  private int getFitnessSum(Member[] population) {
    int sum = 0;
    for (Member member : population) {
      sum += member.getFitness();
    }
    return sum;
  }
  
  private Member[] sortByFitness(Member[] population) {
    Member[] sorted = population.clone();
    Member fittest, swap;
    int index;
    
    for (int i = 0; i < sorted.length - 1; i++) {
      fittest = sorted[i];
      index = i;
      for (int j = i + 1; j < population.length; j++) {
        if (sorted[j].getNormalizedFitness() > fittest.getNormalizedFitness()) {
          fittest = sorted[j];
          index = j;
        }
      }
      swap = sorted[i];
      sorted[i] = fittest;
      sorted[index] = swap;
    }
    
    return sorted;
  }
  
  private void mutate(int[] sequence, int index, int mutationRate) {
    Random random = new Random();
    int randomTo100 = random.nextInt(101);
    if (randomTo100 <= mutationRate) {sequence[index] = sequence[index] == 0? 1:0;}
  }
  
  private Member crossOver(Member dad, Member mom, int mutationRate) {
    int[] son = new int[dad.getSequence().length];
    int cut = dad.getSequence().length * 3 / 5;
    
    for (int i = 0; i <= cut; i++) {
      son[i] = dad.getSequence()[i];
      mutate(son, i, mutationRate);
    }
    for (int j = cut + 1; j < son.length; j++) {
      son[j] = mom.getSequence()[j];
      mutate(son, j, mutationRate);
    }
    
    return new Member(son);
  }
  
  public Member[] initiatePopulation(int N) {
    Member[] population = new Member[N];
    for (int i = 0; i < N; i++) {
      population[i] = new Member(5);
    }
    return population;
  }
  
  public boolean evaluateFitness(Member[] population, int[] sequence) {
    boolean sequenceFound = false;
    
    for (int i = 0; i < population.length; i++) {
      population[i].setFitness(compareSequences(population[i].getSequence(), sequence));
      if (population[i].getFitness() == sequence.length) {sequenceFound = true;}
    }
    
    return sequenceFound;
  }
  
  public Member[] selection(Member[] firstGen) {
    Member[] secondGen = new Member[firstGen.length];
    int fitnessSum = getFitnessSum(firstGen);
    
    for (int i = 0; i < firstGen.length; i++) {
      firstGen[i].setNormalizedFitness(1.0 * firstGen[i].getFitness() / fitnessSum);
    }
    
    secondGen = sortByFitness(firstGen);
    
    for (int j = 1; j < secondGen.length; j++) {
      secondGen[j].setNormalizedFitness(secondGen[j - 1].getNormalizedFitness() + secondGen[j].getNormalizedFitness());
    }
    
    double R = Math.random();
    int cutIndex = 0;
    
    for (int k = 0; k < secondGen.length; k++) {
      if (secondGen[k].getNormalizedFitness() <= R) cutIndex = k;
    }
    
    return Arrays.copyOfRange(secondGen, 0, cutIndex + 1);
  }
  
  public Member[] reproduction(Member[] fittest, int N) {
    Member[] breed = new Member[N];
    Random random = new Random();
    int filled = 0;
    
    while (filled < 10) {
      Member dad = fittest[random.nextInt(fittest.length)];
      Member mom = fittest[random.nextInt(fittest.length)];
      breed[filled++] = crossOver(dad, mom, 1);
    }
    
    return breed;
  }
  
  public void geneticAlgorithm(int N, int[] sequence, Member[] initialPopulation) {
    Member[] population;
    if (initialPopulation == null) population = initiatePopulation(N);
    else population = initialPopulation;
    
    if (evaluateFitness(population, sequence)) {
      return;
    } else {
      Member[] fittest = selection(population);
      Member[] breed = reproduction(fittest, N);
      ++this.totalGenerations;
      geneticAlgorithm(N, sequence, breed);
    }
  }
  
  public int getNumberOfGenerations() {
    return this.totalGenerations;
  }
  
  public static void main(String[] args) {
    NBitSequence gene = new NBitSequence();
    int[] example = {1, 1, 1, 0, 0};
    
    long startTime = System.currentTimeMillis();
    gene.geneticAlgorithm(10, example, null);
    long stopTime = System.currentTimeMillis();
    
    System.out.println("Elapsed time is: " + (stopTime - startTime));
    System.out.println("Number of generations was: " + gene.getNumberOfGenerations());
  }
}