package com.herve.gurvan.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.herve.gurvan.contacts.model.Contact;
import com.herve.gurvan.contacts.sync.Sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<ContactsListFragment.Result>, Sort {
  @NonNull
  private ContactAdapter adapter;
  @NonNull
  private List<Contact> contacts;

  public ContactsListFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    contacts = new ArrayList<>();
    adapter = new ContactAdapter(getContext());
    setListAdapter(adapter);
    getLoaderManager().initLoader(0, null, this);
  }

  @Override
  public void onStart() {
    super.onStart();
    setListShown(false);
  }

  @Override
  public Loader<ContactsListFragment.Result> onCreateLoader(int id, Bundle args) {
    return args == null ? new ContactsLoader(getContext()) : new ContactsLoader(getContext(), args.getInt("sort"));
  }

  @Override
  public void onLoadFinished(Loader<ContactsListFragment.Result> loader, ContactsListFragment.Result data) {
    if (data.message != -1) {
      Toast.makeText(getContext(), data.message, Toast.LENGTH_LONG).show();
    } else {
      contacts.clear();
      contacts.addAll(data.contacts);
      adapter.setData(contacts);
    }
    setListShown(true);
  }

  @Override
  public void onLoaderReset(Loader<ContactsListFragment.Result> loader) {

  }

  @Override
  public void sortAZ() {
    Collections.sort(contacts);
    adapter.setData(contacts);
  }

  @Override
  public void sortZA() {
    Collections.reverse(contacts);
    adapter.setData(contacts);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);

    Intent intent = new Intent(getContext(), ContactDetailActivity.class);
    Contact contact = (Contact) l.getAdapter().getItem(position);
    intent.putExtra("contact", contact.toString()); // Parcelable should be faster
    startActivity(intent);
  }

  private static class ContactsLoader extends AsyncTaskLoader<ContactsListFragment.Result> {
    private Result result;
    private int sort;

    private ContactsLoader(Context context) {
      this(context, Sort.AZ);
    }

    private ContactsLoader(Context context, int sort) {
      super(context);
      this.sort = sort;
    }

    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(ContactsListFragment.Result result) {
      if (isReset()) {
        // An async query came in while the loader is stopped.  We
        // don't need the result.
        if (result != null) {
          onReleaseResources(result);
        }
      }
      Result oldResult = result;
      this.result = result;

      if (isStarted()) {
        // If the Loader is currently started, we can immediately
        // deliver its results.
        super.deliverResult(result);
      }

      // At this point we can release the resources associated with
      // 'oldResult' if needed; now that the new result is delivered we
      // know that it is no longer in use.
      if (oldResult != null) {
        onReleaseResources(oldResult);
      }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
      if (result != null) {
        // If we currently have a result available, deliver it
        // immediately.
        deliverResult(result);
      }

      if (takeContentChanged() || result == null) {
        // If the data has changed since the last time it was loaded
        // or is not currently available, start a load.
        forceLoad();
      }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
      // Attempt to cancel the current load task if possible.
      cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(ContactsListFragment.Result contacts) {
      super.onCanceled(contacts);

      // At this point we can release the resources associated with 'result'
      // if needed.
      onReleaseResources(contacts);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
      super.onReset();

      // Ensure the loader is stopped
      onStopLoading();

      // At this point we can release the resources associated with 'result'
      // if needed.
      if (result != null) {
        onReleaseResources(result);
        result = null;
      }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    void onReleaseResources(Result contacts) {
      // For a simple Object there is nothing to do.  For something
      // like a Cursor, we would close it here.
    }


    @Override
    public ContactsListFragment.Result loadInBackground() {
      List<Contact> contacts = new ArrayList<>();
      Result res = null;
      boolean hasNetwork = false;
      if (getContext().getResources().getBoolean(R.bool.isSimulated)) {
        for (int i = 0; i < 20; i++) {
          contacts.add(i, Contact.createContact());
        }
      } else {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = connectivityManager.getActiveNetworkInfo();
        hasNetwork = net != null && net.isConnected();
        if (hasNetwork) {
          Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();
          Sync sync = retrofit.create(Sync.class);
          Call<List<Contact>> call = sync.getContacts();
          try {
            Response<List<Contact>> resp = call.execute();
            contacts.addAll(resp.body());
          } catch (IOException e) {
          }
        } else {
          res = new ContactsListFragment.Result(R.string.no_network);
        }
      }

      if (contacts.isEmpty()) {
        if (hasNetwork) {
          res = new ContactsListFragment.Result(R.string.no_contacts);
        }
      } else {
        Collections.sort(contacts);
        res = new ContactsListFragment.Result(contacts);
      }

      return res;
    }
  }

  protected static class Result {
    final List<Contact> contacts;
    @StringRes
    final int message;

    Result(List<Contact> contacts) {
      this(contacts, -1);
    }

    Result(@StringRes int message) {
      this(null, message);
    }

    private Result(List<Contact> contacts, @StringRes int message) {
      this.contacts  = contacts;
      this.message = message;
    }
  }
}
