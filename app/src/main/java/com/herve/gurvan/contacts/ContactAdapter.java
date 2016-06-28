package com.herve.gurvan.contacts;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.herve.gurvan.contacts.model.Contact;
import com.herve.gurvan.contacts.util.Color;

import java.util.ArrayList;
import java.util.List;

class ContactAdapter extends BaseAdapter {
  @NonNull
  private List<Contact> contacts;
  @NonNull
  private LayoutInflater inflater;
  @ColorInt
  private int colorUsername;
  @ColorInt
  private int colorEmail;

  ContactAdapter(@NonNull Context context) {
    this.inflater = LayoutInflater.from(context);
    this.contacts = new ArrayList<>();
    colorUsername = Color.getColor(context, R.color.colorAccent);
    colorEmail = Color.getColor(context, R.color.colorPrimary);
  }

  void setData(List<Contact> data) {
    this.contacts.clear();
    this.contacts.addAll(data);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return contacts.size();
  }

  @Override
  public Contact getItem(int i) {
    return contacts.get(i);
  }

  @Override
  public long getItemId(int i) {
    return contacts.get(i).hashCode();
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    Contact contact = contacts.get(position);
    ViewHolder vh;

    if (view == null) {
      vh = new ViewHolder();
      view = inflater.inflate(android.R.layout.simple_list_item_2, viewGroup, false);
      vh.name = (TextView) view.findViewById(android.R.id.text1);
      vh.email = (TextView) view.findViewById(android.R.id.text2);
      view.setTag(vh);
    } else {
      vh = (ViewHolder) view.getTag();
    }

    vh.name.setText(contact.getName());
    vh.name.setAllCaps(true);
    vh.name.setTextColor(colorUsername);
    vh.email.setText(contact.getEmail());
    vh.email.setAllCaps(true);
    vh.email.setTextColor(colorEmail);

    return view;
  }

  private static class ViewHolder {
    TextView name;
    TextView email;
  }
}
