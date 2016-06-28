package com.herve.gurvan.contacts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.herve.gurvan.contacts.databinding.DetailContactBinding;
import com.herve.gurvan.contacts.model.Contact;

public class ContactDetailActivity extends AppCompatActivity {
  @NonNull
  private Contact contact;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    DetailContactBinding binding = DataBindingUtil.setContentView(this, R.layout.detail_contact);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    contact = new Contact(getIntent().getStringExtra("contact"));

    binding.setContact(contact);
    binding.setAddress(contact.getAddress());
    binding.setCompany(contact.getCompany());
  }
}
