package com.example.appclima.controller;

import com.example.appclima.service.ClimaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ciudad")
public class ClimaController {


  private final ClimaService climaService;

  public ClimaController(ClimaService climaService) {
    this.climaService = climaService;
  }


  @CrossOrigin(origins = {"http://localhost:5174", "http://localhost:5173", "http://localhost:3000", "http://127.0.0.1:5173", "http://127.0.0.1:5174"})
  @GetMapping("/clima/{ciudad}")
  public ResponseEntity<String> obtenerClima(@PathVariable String ciudad) {
    return climaService.obtenerClima(ciudad);
  }
}
