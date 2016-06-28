package com.herve.gurvan.contacts.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.herve.gurvan.contacts.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Address {
  private String street;
  private String suite;
  private String city;
  private String zipcode;
  private Geolocalization geo;

  public Address() {
  }

  public Address(String str) {
    try {
      JSONObject obj = new JSONObject(str);
      street = obj.getString("street");
      suite = obj.getString("suite");
      city = obj.getString("city");
      zipcode = obj.getString("zipcode");
      geo = new Geolocalization(obj.getString("geo"));
    } catch (JSONException e) {
    }
  }

  public String getStreet() {
    return street;
  }

  public String getSuite() {
    return suite;
  }

  public String getCity() {
    return city;
  }

  public String getZipcode() {
    return zipcode;
  }

  @Override
  public String toString() {
    JSONObject json = new JSONObject();
    try {
      json.put("street", street);
      json.put("suite", suite);
      json.put("city", city);
      json.put("zipcode", zipcode);
      json.put("geo", geo.toString());
    } catch (JSONException e) {
    }
    return json.toString();
  }

  static Address createAddress() {
    Address address = new Address();
    address.street = Utils.loremIpsum(15);
    address.suite = Utils.loremIpsum(4);
    address.city = Utils.loremIpsum(8);
    address.zipcode = Utils.randomZipcode();
    address.geo = Geolocalization.createGeolocalization();
    return address;
  }
}
