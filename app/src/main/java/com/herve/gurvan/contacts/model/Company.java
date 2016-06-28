package com.herve.gurvan.contacts.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.herve.gurvan.contacts.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Company {
  private String name;
  private String catchPhrase;
  private String bs;

  public Company() {
  }

  public Company(String str) {
    try {
      JSONObject obj = new JSONObject(str);
      name = obj.getString("name");
      catchPhrase = obj.getString("catchPhrase");
      bs = obj.getString("bs");
    } catch (JSONException e) {
    }
  }

  public String getName() {
    return name;
  }

  public String getCatchPhrase() {
    return catchPhrase;
  }

  public String getBs() {
    return bs;
  }

  @Override
  public String toString() {
    JSONObject json = new JSONObject();
    try {
      json.put("name", name);
      json.put("catchPhrase", catchPhrase);
      json.put("bs", bs);
    } catch (JSONException e) {
    }
    return json.toString();
  }

  static Company createCompany() {
    Company company = new Company();
    company.name = Utils.loremIpsum(15);
    company.catchPhrase = Utils.loremIpsum(25);
    company.bs = Utils.loremIpsum(10);
    return company;
  }
}
