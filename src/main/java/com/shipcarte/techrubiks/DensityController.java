package com.shipcarte.techrubiks;

import com.shipcarte.techrubiks.calc.FreightCalculator;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class DensityController {
  public record DensityResult(double density, String unit) {}

  @GetMapping("/api/density")
  public DensityResult calculate(@RequestParam double length, @RequestParam double breadth,
      @RequestParam double height, @RequestParam double weight,
      @RequestParam String lengthUnit, @RequestParam String weightUnit) {
    return new DensityResult(FreightCalculator.findDensity(length, breadth, height, weight,
        lengthUnit, weightUnit), "lb/ft³");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> invalid(IllegalArgumentException e) {
    return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, String>> malformed() {
    return ResponseEntity.badRequest().body(Map.of("error", "Enter valid numeric measurements."));
  }
}
