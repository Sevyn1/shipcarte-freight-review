package com.shipcarte.techrubiks;

import static org.junit.jupiter.api.Assertions.*;

import com.shipcarte.techrubiks.calc.FreightCalculator;
import com.shipcarte.techrubiks.commons.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FreightCalculatorTest {
  @Test
  void imperialDensity() {
    assertEquals(10, FreightCalculator.findDensity(12, 12, 12, 10, "inch", "lbs"), 1e-12);
  }

  @Test
  void feetMatchInches() {
    assertEquals(10, FreightCalculator.findDensity(1, 1, 1, 10, "feet", "lbs"), 1e-12);
  }

  @Test
  void metricEquivalent() {
    assertEquals(
        10, FreightCalculator.findDensity(30.48, 30.48, 30.48, 4.5359237, "cm", "kg"), 1e-10);
  }

  @Test
  void smallCentimetresDoNotRoundToZero() {
    assertEquals(
        1728 / Math.pow(.001 / 2.54, 3),
        FreightCalculator.findDensity(.001, .001, .001, 1, "cm", "lbs"),
        1);
  }

  @Test
  void fractionalMeasurementsRetainPrecision() {
    assertEquals(1 / 2.54, LengthUtils.convertCmToInches(1), 1e-12);
    assertEquals(.12, LengthUtils.convertFeetToInches(.01), 1e-12);
    assertEquals(.001 / 0.45359237, WeightUtils.convertKgToLbs(.001), 1e-12);
  }

  @Test
  void ounceConversions() {
    assertEquals(16, WeightUtils.convertLbsToOunce(1), 1e-12);
    assertEquals(1 / 0.028349523125, WeightUtils.convertKgToOunce(1), 1e-12);
  }

  @Test
  void normalizesUnitNames() {
    assertEquals(10, FreightCalculator.findDensity(1, 1, 1, 10, " FEET ", " LBS "), 1e-12);
  }

  @ParameterizedTest
  @ValueSource(doubles = {0, -1, Double.NaN, Double.POSITIVE_INFINITY})
  void rejectsInvalidMeasurements(double value) {
    assertThrows(
        IllegalArgumentException.class,
        () -> FreightCalculator.findDensity(value, 1, 1, 1, "feet", "lbs"));
    assertThrows(
        IllegalArgumentException.class,
        () -> FreightCalculator.findDensity(1, value, 1, 1, "feet", "lbs"));
    assertThrows(
        IllegalArgumentException.class,
        () -> FreightCalculator.findDensity(1, 1, value, 1, "feet", "lbs"));
    assertThrows(
        IllegalArgumentException.class,
        () -> FreightCalculator.findDensity(1, 1, 1, value, "feet", "lbs"));
  }

  @Test
  void rejectsUnknownAndNullUnits() {
    for (String unit : new String[] {"meters", "", null})
      assertThrows(
          IllegalArgumentException.class,
          () -> FreightCalculator.findDensity(1, 1, 1, 1, unit, "lbs"));
    assertThrows(
        IllegalArgumentException.class,
        () -> FreightCalculator.findDensity(1, 1, 1, 1, "inch", "stones"));
    assertThrows(
        IllegalArgumentException.class,
        () -> FreightCalculator.findDensity(1, 1, 1, 1, "inch", null));
  }

  @Test
  void rejectsOverflowAndUnderflow() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            FreightCalculator.findDensity(Double.MAX_VALUE, Double.MAX_VALUE, 1, 1, "feet", "lbs"));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            FreightCalculator.findDensity(
                Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, 1, "inch", "lbs"));
  }
}
