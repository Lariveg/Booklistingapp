package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyTextView;

    // Progress spinner which appears when data is being downloaded from the internet
    private View progressSpinner;

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * URL for book data from the Google Books API
     */
    private static final String GOOGLE_BOOK_API_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    // User's search input
    private String searchQuery;

    /* Adapter for the list of books */
    private BookAdapter mAdapter;

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, GOOGLE_BOOK_API_REQUEST_URL, searchQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mEmptyTextView.setText(R.string.no_books);

        // Clear the adapter of previous book data
        mAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        progressSpinner = findViewById(R.id.loading_indicator);
        progressSpinner.setVisibility(View.GONE);

        // Find a reference to the {@link ListView} in the layout
        final ListView bookListView = (ListView) findViewById(R.id.list);

        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyTextView);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        final EditText searchField = (EditText) findViewById(R.id.search);

        final Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {

                String searchString = searchField.getText().toString();

                searchQuery = searchString.trim();

                mEmptyTextView.setText("");
                progressSpinner.setVisibility(View.VISIBLE);

                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    // Restart the loader
                    getLoaderManager().restartLoader(0, null,MainActivity.this);
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    progressSpinner.setVisibility(View.GONE);

                    mAdapter.clear();

                    // Update empty state with no connection error message
                    mEmptyTextView.setText(R.string.no_internet_connection);
                }

            }
        });

    }
}
