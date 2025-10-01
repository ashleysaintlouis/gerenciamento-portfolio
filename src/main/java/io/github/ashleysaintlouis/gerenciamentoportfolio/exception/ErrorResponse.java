package io.github.ashleysaintlouis.gerenciamentoportfolio.exception;

import java.time.LocalDateTime;

record ErrorResponse(int status, String message, LocalDateTime timestamp) {}