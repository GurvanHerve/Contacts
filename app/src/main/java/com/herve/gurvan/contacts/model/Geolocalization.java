package com.herve.gurvan.contacts.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.herve.gurvan.contacts.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Geolocalization {
  private int latitute;
  private int longitude;

  public Geolocalization(int latitute, int longitude) {
    this.latitute = latitute;
    this.longitude = longitude;
  }

  public Geolocalization(String str) {
    try {
      JSONObject json = new JSONObject(str);
      latitute = json.getInt("latitude");
      longitude = json.getInt("longitude");
    } catch (JSONException e) {
    }
  }

  @Override
  public String toString() {
    JSONObject json = new JSONObject();
    try {
      json.put("latitude", latitute);
      json.put("longitude", longitude);
    } catch (JSONException e) {
    }
    return json.toString();
  }

  static Geolocalization createGeolocalization() {
    return new Geolocalization(10, 10);
  }
}
