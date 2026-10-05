package com.shipcarte.techrubiks;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DensityApiTest {
  @Autowired MockMvc mvc;

  @Test void metricRequestUsesReviewedCalculator() throws Exception {
    mvc.perform(get("/api/density").param("length","30.48").param("breadth","30.48")
        .param("height","30.48").param("weight","4.5359237")
        .param("lengthUnit","cm").param("weightUnit","kg"))
        .andExpect(status().isOk()).andExpect(jsonPath("$.density").value(10.0))
        .andExpect(jsonPath("$.unit").value("lb/ft³"));
  }
  @Test void rejectsInvalidMeasurementsAndUnits() throws Exception {
    for (String length : new String[]{"0", "-1", "NaN", "Infinity", "bad"}) {
      mvc.perform(get("/api/density").param("length",length).param("breadth","12")
          .param("height","12").param("weight","10")
          .param("lengthUnit","inch").param("weightUnit","lbs"))
          .andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").isString());
    }
    mvc.perform(get("/api/density").param("length","12").param("breadth","12")
        .param("height","12").param("weight","10")
        .param("lengthUnit","metres").param("weightUnit","lbs"))
        .andExpect(status().isBadRequest());
  }
  @Test void missingMeasurementsAreRejected() throws Exception {
    mvc.perform(get("/api/density")).andExpect(status().isBadRequest());
  }
  @Test void servesBrowserInterfaceAndAssets() throws Exception {
    mvc.perform(get("/")).andExpect(status().isOk());
    mvc.perform(get("/app.js")).andExpect(status().isOk());
    mvc.perform(get("/style.css")).andExpect(status().isOk());
  }
}
