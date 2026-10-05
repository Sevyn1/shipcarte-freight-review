package com.shipcarte.techrubiks.commons;

/**
 * Defines the length utils used for application
 *
 * @author Pavithra
 * @since 24/04/2020
 */
public class LengthUtils {

  /**
   * Method is used to convert feet to inches
   *
   * @author Pavithra
   * @since 24/04/2020
   * @param feet
   * @return converted inches value
   */
  public static double convertFeetToInches(double feet) {

    double inches = 0.0;

    if (feet > 0) {

      inches = feet * 12;

      inches = inches;
    }

    return inches;
  }

  /**
   * Method is used to convert centimeter to inches
   *
   * @author Pavithra
   * @since 24/04/2020
   * @param centimeter
   * @return converted inches value
   */
  public static double convertCmToInches(double centimeter) {

    double inches = 0.0;

    if (centimeter > 0) {

      inches = centimeter / 2.54;

      inches = inches;
    }

    return inches;
  }

  public static void main(String[] args) {

    // convertFeetToInches(0);

    // convertCmToInches(2.54);
  }
}
