package com.ingsw.bugboard26.exception.duplicate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameDuplicateException extends Exception
{
  private final String message;

  public UsernameDuplicateException(final String message)
  {
    super(message);
    this.message = message;
  }
}
