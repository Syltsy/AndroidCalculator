package com.example.metropolia.calculator;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import com.example.metropolia.calculator.DBAdapter.*;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;


// MyContentProvider-class is based on implementation found from here: 
// http://www.vogella.com/tutorials/AndroidSQLite/article.html

public class MyContentProvider extends ContentProvider {

	private DatabaseHelper database;
	
	// used for the UriMacher
	private static final int ALL = 10; 
	private static final int CURR_ID = 20; // query for one currency
	
	private static final String AUTHORITY = "com.metropolia.calculator.contentprovider";

	private static final String BASE_PATH = "currencies";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
	      + "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	      + "/currencies";
	  public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	      + "/currencies";

	  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	  static {
	    sURIMatcher.addURI(AUTHORITY, BASE_PATH, ALL);
	    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", CURR_ID);
	  }

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) { 
	    int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsDeleted = 0;
	    switch (uriType) {
	    case ALL:
	      rowsDeleted = sqlDB.delete("currencies", selection,
	          selectionArgs);
	      break;
	    case CURR_ID:
	      String id = uri.getLastPathSegment();
	      if (TextUtils.isEmpty(selection)) {
	        rowsDeleted = sqlDB.delete("currencies",
	            DBAdapter.KEY_ROWID + "=" + id, 
	            null);
	      } 
	      else {
	        rowsDeleted = sqlDB.delete("currencies",
	        	DBAdapter.KEY_ROWID + "=" + id 
	            + " and " + selection,
	            selectionArgs);
	      }
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return rowsDeleted;
	  }

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) { // values = Currency
	    int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsDeleted = 0;
	    long id = 0;
	    switch (uriType) {
	    case ALL:
	      id = sqlDB.insert("currencies", null, values);
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return Uri.parse(BASE_PATH + "/" + id);
	  }
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		database = new DatabaseHelper(getContext());
		
	    return false;
	}

	@Override
	 public Cursor query(Uri uri, String[] projection, String selection,
		      String[] selectionArgs, String sortOrder) {
	 
		// Using SQLiteQueryBuilder instead of query() method
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

	    // check if the caller has requested a column which does not exists 
	    
	    checkColumns(projection);
	    // Set the table
	    queryBuilder.setTables("currencies"); // name of a table

	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case ALL:
	      break;
	    case CURR_ID:
	      // adding the ID to the original query
	      queryBuilder.appendWhere(DBAdapter.KEY_ROWID + "="
	         + uri.getLastPathSegment());
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }

	    
	    SQLiteDatabase db = database.getReadableDatabase(); //getWritableDatabase();
	    		
	    Cursor cursor = queryBuilder.query(db, projection, selection,
	        selectionArgs, null, null, sortOrder);
	    // make sure that potential listeners are getting notified
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);

	    return cursor;
	 
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void checkColumns(String[] projection) {
	    String[] available = { DBAdapter.KEY_CURRENCY_NAME,
	    		DBAdapter.KEY_CURRENCY_RELATION, DBAdapter.KEY_DATE,
	    		DBAdapter.KEY_ROWID };
	    if (projection != null) {
	      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
	      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
	      // check if all columns which are requested are available
	      if (!availableColumns.containsAll(requestedColumns)) {
	        throw new IllegalArgumentException("Unknown columns in projection");
	      }
	    }
	  }

	public Context getBaseContext() {
		return baseContext;
	}

	private class DownloadWebpageTask extends AsyncTask<String, Void, String> {


	  /*else if (id == R.id.update){
			  if (isNetworkAvailable()){
				  try {
					  new DownloadWebpageTask().execute("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");

				  }
				  catch (Exception e) {
					  Toast.makeText(this, "Reading the web page is unsuccesfull", Toast.LENGTH_SHORT).show();
				  }
			  }
			  else
				  Toast.makeText(this, "Network is not available", Toast.LENGTH_SHORT).show();

		  }*/

		@Override
		protected String doInBackground(String... url) {

			// params comes from the execute() call: params[0] is the url.
			try (IOException e){
				

				return downLoadUrl(url[0]);
			} catch (IOException e) {
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}

		  private String downLoadUrl(String s) {
			return s;
		  }

		  // onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

			String URL = "content://<authority>/currencies";
			//....

		}
	}
}
