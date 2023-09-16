package com.sst.springapireportes.excepciones;

public class ReportNotFoundException extends RuntimeException {
  public ReportNotFoundException(String message) {
    super(message);
  }
}
