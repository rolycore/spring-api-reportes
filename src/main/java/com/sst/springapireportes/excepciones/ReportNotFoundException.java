package com.sstproyects.springboot.backend.apirest.excepciones;

public class ReportNotFoundException extends RuntimeException {
  public ReportNotFoundException(String message) {
    super(message);
  }
}
