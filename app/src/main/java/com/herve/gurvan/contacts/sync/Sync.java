package com.herve.gurvan.contacts.sync;

import com.herve.gurvan.contacts.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Sync {
  @GET("users")
  Call<List<Contact>> getContacts();
}
