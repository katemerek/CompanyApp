package com.github.katemerek.companyapp.dto;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime timestamp, int status, String error, String message) {}

