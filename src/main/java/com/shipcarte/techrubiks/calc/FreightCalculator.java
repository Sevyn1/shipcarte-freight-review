package com.shipcarte.techrubiks.calc;

import com.shipcarte.techrubiks.commons.WeightUtils;
import com.shipcarte.techrubiks.constants.AppnConstants;
import java.util.Locale;

/**
 * Review of the density calculation from the shared ShipCarte course source. Returns pounds per
 * cubic foot. Maintenance changes validate units and retain conversion precision. Original
 * implementation is preserved in docs/baseline.
 */
public class FreightCalculator {
  public static double findDensity(
      double length,
      double breadth,
      double height,
      double totalWeight,
      String lengthUnit,
      String weightUnit) {
    positive(length, "length");
    positive(breadth, "breadth");
    positive(height, "height");
    positive(totalWeight, "weight");
    String unit = unit(lengthUnit);
    String massUnit = unit(weightUnit);
    double multiplier;
    switch (unit) {
      case AppnConstants.Unit.FEET:
        multiplier = 12;
        break;
      case AppnConstants.Unit.CENTIMETER:
        multiplier = 1 / 2.54;
        break;
      case AppnConstants.Unit.INCH:
        multiplier = 1;
        break;
      default:
        throw new IllegalArgumentException("Length unit must be inch, feet or cm.");
    }
    double weight;
    switch (massUnit) {
      case AppnConstants.Unit.LBS:
        weight = totalWeight;
        break;
      case AppnConstants.Unit.KILOGRAM:
        weight = WeightUtils.convertKgToLbs(totalWeight);
        break;
      default:
        throw new IllegalArgumentException("Weight unit must be lbs or kg.");
    }
    double density =
        weight / (length * multiplier * breadth * multiplier * height * multiplier / 1728);
    if (!Double.isFinite(density) || density <= 0)
      throw new IllegalArgumentException("Measurements exceed supported numeric range.");
    return density;
  }

  private static String unit(String input) {
    if (input == null) throw new IllegalArgumentException("Unit is required.");
    return input.trim().toLowerCase(Locale.ROOT);
  }

  private static void positive(double input, String name) {
    if (!Double.isFinite(input) || input <= 0)
      throw new IllegalArgumentException(name + " must be positive and finite.");
  }
}
