package com.cognizant.springlearn.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.springlearn.Country;

@RestController
public class DataController {

    /**
     * Hands-on 2: SimpleDateFormat XML Bean Initialization & REST Request Verification
     * URL Path: http://localhost:8085/api/date/parse?dateStr=31/12/2018
     */
    @GetMapping("/api/date/parse")
    public ResponseEntity<Map<String, String>> parseDate(@RequestParam String dateStr) {
        Map<String, String> response = new HashMap<>();
        try {
            // Load spring configuration file from application context
            @SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
            
            // Retrieve bean instance using getBean()
            SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
            
            // Parse the incoming request string parameter
            Date parsedDate = format.parse(dateStr);
            
            response.put("status", "SUCCESS");
            response.put("message", "Date parsed successfully using configured XML Bean!");
            response.put("result", parsedDate.toString());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Parsing Failure: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Hands-on 4: Load Country from Spring Configuration XML & Trace Logs
     * URL Path: http://localhost:8085/api/country
     */
    @GetMapping("/api/country")
    public ResponseEntity<Map<String, String>> displayCountry() {
        Map<String, String> response = new HashMap<>();
        try {
            // Instantiate ApplicationContext to read country bean config metadata
            @SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
            
            // Retrieve bean reference through getBean() invocation
            Country country = context.getBean("country", Country.class);
            
            response.put("status", "SUCCESS");
            response.put("bean_id", "country");
            response.put("country_code", country.getCode());
            response.put("country_name", country.getName());
            response.put("toString_output", country.toString());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Failed to resolve bean: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}