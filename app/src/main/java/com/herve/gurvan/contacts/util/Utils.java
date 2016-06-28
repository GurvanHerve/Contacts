package com.herve.gurvan.contacts.util;

import android.support.annotation.NonNull;

import java.util.Random;

public final class Utils {
  private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

  @NonNull
  public static String loremIpsum(int length) {
    return LOREM_IPSUM.substring(0, length - 1);
  }

  @NonNull
  public static String randomPoneNumber() {
    return randomNumber(10);
  }

  @NonNull
  public static String randomZipcode() {
    return randomNumber(5);
  }

  @NonNull
  private static String randomNumber(int length) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(random.nextInt(9));
    }
    return sb.toString();
  }
}
