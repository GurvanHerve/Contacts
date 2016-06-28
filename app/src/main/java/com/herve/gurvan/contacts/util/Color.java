package com.herve.gurvan.contacts.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.herve.gurvan.contacts.R;

public final class Color {

  @ColorInt
  public static int getColor(Context context, @ColorRes int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return context.getColor(color);
    } else {
      return context.getResources().getColor(color);
    }
  }
}
