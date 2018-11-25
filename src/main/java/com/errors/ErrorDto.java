package fr.maileva.facile.commons.jaxrs.errors;

/**
 * An immutable error with a code and a message.
 */
public class ErrorDto {

  /**
   * Default error code for server errors: "{@value #SERVER_ERROR_CODE}".
   */
  public static final String SERVER_ERROR_CODE = "SERVER_ERROR";

  /**
   * Default error code for client errors: "{@value #SERVER_ERROR_CODE}".
   */
  public static final String CLIENT_ERROR_CODE = "CLIENT_ERROR";

  /**
   * Short alphanumeric error code.
   * Error code should be a legal java enum identifier : uppercase, no space,
   * start with a letter.
   */
  private final String code;

  /**
   * Error message.
   */
  private final String message;

  /**
   * Creates an ErrorInfo instance with the passed code and message.
   * @param code code to use
   * @param message
   *
   */
  public ErrorDto(String code , String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
