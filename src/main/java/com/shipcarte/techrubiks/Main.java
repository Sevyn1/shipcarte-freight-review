package com.shipcarte.techrubiks;

import com.shipcarte.techrubiks.calc.FreightCalculator;

/** Local example runner; no carrier, database or cloud access. */
public class Main {
  public static void main(String[] args) {
    if (args.length != 6) {
      System.err.println("Usage: length breadth height weight lengthUnit weightUnit");
      System.exit(2);
    }
    try {
      double result =
          FreightCalculator.findDensity(
              Double.parseDouble(args[0]),
              Double.parseDouble(args[1]),
              Double.parseDouble(args[2]),
              Double.parseDouble(args[3]),
              args[4],
              args[5]);
      System.out.printf(java.util.Locale.ROOT, "Density: %.6f lbs/ft³%n", result);
    } catch (IllegalArgumentException error) {
      System.err.println(error.getMessage());
      System.exit(2);
    }
  }
}
