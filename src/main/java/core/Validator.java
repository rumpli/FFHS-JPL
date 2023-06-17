package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to validate user input
 */
public final class Validator {

  /**
   * Private constructor to block that Validator can be instantiated
   */
  private Validator() {
  }

  /**
   * Validate an IPv4 address
   * @param ipAddress the ip to validate
   * @return true for valid ipv4 or empty string, false for invalid ip
   */
  public static boolean isValidIP(String ipAddress) {
    // default value, will be interpreted as localhost
    if (ipAddress.length() == 0) {
      return true;
    }

    // Regex from https://www.baeldung.com/java-validate-ipv4-address
    String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(ipAddress);
    return matcher.matches();
  }
}
