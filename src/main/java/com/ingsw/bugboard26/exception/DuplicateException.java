package com.ingsw.bugboard26.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateException extends RuntimeException
{
  private final String message;

  public DuplicateException(final String message)
  {
    super(message);
    this.message = message;
  }
}
