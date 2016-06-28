package com.herve.gurvan.contacts.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.JsonWriter;

import com.herve.gurvan.contacts.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

public class Contact implements Comparable<Contact> {
  private String name;
  private String username;
  private String email;
  private Address address;
  private String phone;
  private String website;
  private Company company;

  public Contact() {
  }

  public Contact(String str) {
    try {
      JSONObject obj = new JSONObject(str);
      name = obj.getString("name");
      username = obj.getString("username");
      email = obj.getString("email");
      address = new Address(obj.getString("address"));
      phone = obj.getString("phone");
      website = obj.getString("website");
      company = new Company(obj.getString("company"));
    } catch (JSONException e) {
    }
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public Address getAddress() {
    return address;
  }

  public String getPhone() {
    return phone;
  }

  public String getWebsite() {
    return website;
  }

  public Company getCompany() {
    return company;
  }

  @Override
  public int compareTo(Contact contact) {
    return name.compareTo(contact.name);
  }

  @Override
  public String toString() {
    JSONObject json = new JSONObject();
    try {
      json.put("name", name);
      json.put("username", username);
      json.put("email", email);
      json.put("address", address.toString());
      json.put("phone", phone);
      json.put("website", website);
      json.put("company", company.toString());
    } catch (JSONException e) {
    }
    return json.toString();
  }

  @NonNull
  public static Contact createContact() {
    Contact contact = new Contact();
    contact.name = Utils.loremIpsum(6);
    contact.username = Utils.loremIpsum(10);
    contact.email = Utils.loremIpsum(20);
    contact.address = Address.createAddress();
    contact.phone = Utils.randomPoneNumber();
    contact.website = Utils.randomPoneNumber();
    contact.company = Company.createCompany();
    return contact;
  }
}
