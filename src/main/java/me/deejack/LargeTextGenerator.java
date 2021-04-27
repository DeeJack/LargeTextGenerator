package me.deejack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class LargeTextGenerator {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Path: ");
    String path = sc.nextLine();

    System.out.print("Size (in bytes) of the file you want to create: ");
    long size = sc.nextLong();
    sc.nextLine(); // Skip line

    System.out.println("Mode: ");
    System.out.println("1) Numeric (0-9)");
    System.out.println("2) Alphabetic (a-Z)");
    System.out.println("3) Alphanumeric (a-Z-0-9)");
    System.out.print("=> ");
    if (!sc.hasNextInt()) {
      System.err.println("Bruh, use a number");
      return;
    }

    int choice = sc.nextInt();

    if (choice < 1 || choice > 3) {
      System.err.println("C'mon, the number have to be between 1 and 3");
      return;
    }
    sc.nextLine(); // Skip line

    System.out.print("Last string (if you need to search in the file): ");
    String toFind = sc.nextLine();

    /* Create a generator function for every choice */
    StringGenerator generator;
    if (choice == 1) {
      generator = num -> Character.forDigit((int) num % 10, 10);
    } else if (choice == 2) {
      generator = num -> (char) ('a' + ((int) num % 24));
    } else {
      System.out.println("Not supported yet sorry");
      return;
    }

    // The maximum heap space is 1GB by default, so I count the number of times I need to create a 1GB string to reach the size
    double phases = size / Math.pow(10, 9);
    for (long i = 0; i < phases; i++) { // For every phase
      System.out.println("Starting phase " + (i + 1) + " out of " + (phases) + " phases");
      long newSize = size;
      if (phases > 1) // If I have more than one phase
        newSize = size / (long) phases; // Get the size to reach for the phase

      StringBuilder builder = buildString(newSize, generator); // Build the string using the generator until the size is reached
      builder.append(toFind);
      try {
        // Create the file if it doesn't exists, then append
        Files.writeString(Paths.get(path), builder.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
      } catch (IOException ex) {
        System.err.println("Couldn't find the file");
      }
      System.gc();
    }
  }

  public static StringBuilder buildString(long size, StringGenerator generator) {
    StringBuilder builder = new StringBuilder();
    long i = 0;
    try {
      for (; i < size; i++) {
        builder.append(generator.generate(i));
      }
    } catch (OutOfMemoryError error) {
      // Error thrown if the JVM doesn't have enough memory (HEAP)
      System.err.println("Out of memory error on i = " + i);
      error.printStackTrace();
    }
    return builder;
  }

  interface StringGenerator {
    char generate(long num);
  }
}
