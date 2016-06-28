package com.herve.gurvan.contacts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ContactsListActivity extends AppCompatActivity {
  @NonNull
  private ContactsListFragment frag;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_contacts);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    String tag = ContactsListFragment.class.getSimpleName();
    FragmentManager manager = getSupportFragmentManager();
    frag = (ContactsListFragment) manager.findFragmentByTag(tag);
    if (frag == null) {
      frag = new ContactsListFragment();
      manager.beginTransaction().add(R.id.content, frag, tag).commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_contacts, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_sort_az) {
      frag.sortAZ();
      return true;
    } else if (id == R.id.action_sort_za){
      frag.sortZA();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
