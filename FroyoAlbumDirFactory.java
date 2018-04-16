package com.example.metropolia.calculator;

import java.io.File;

import android.content.BroadcastReceiver;
import android.os.Environment;

public final class FroyoAlbumDirFactory extends AlbumStorageDirFactory {

	BroadcastReceiver mExternalStorageReceiver;
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;

	void updateExternalStorageState() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        mExternalStorageAvailable = mExternalStorageWriteable = true;
	    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        mExternalStorageAvailable = true;
	        mExternalStorageWriteable = false;
	    } else {
	        mExternalStorageAvailable = mExternalStorageWriteable = false;
	    }
	    //handleExternalStorageState(mExternalStorageAvailable,
	    //        mExternalStorageWriteable);
	}


	private boolean handleExternalStorageState(boolean mExtAvailable,
			boolean mExtWriteable) {
		if (mExtAvailable && mExtWriteable) return true;
		else return false;
			
		
	}


	@Override
	public File getAlbumStorageDir(String albumName) {
		// TODO Auto-generated method stub
		updateExternalStorageState();
		if (handleExternalStorageState(mExternalStorageAvailable, mExternalStorageWriteable))
		
			return new File(
		  Environment.getExternalStorageDirectory(
		    //Environment.DIRECTORY_PICTURES		  
		  ), 
		  albumName
		);
		else
			return null; // not found media to save a file
	}
}
