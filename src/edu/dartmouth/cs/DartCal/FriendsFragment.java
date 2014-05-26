package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FriendsFragment extends Fragment {
	MenuItem friends;
	EventDbHelper database;
	ArrayList<Integer> selectedFriends;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.friends_fragment, container, false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		database = new EventDbHelper(getActivity());
		selectedFriends = new ArrayList<Integer>();
		Friend user = new Friend();
		user.setName("Paul Champeau");
		try {
			database.insertEntry(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Friend user1 = new Friend();
		user1.setName("Lisa Luo");
		try {
			database.insertEntry(user1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		friends = menu.add("View Friends");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		ArrayList<Friend> values = null;
		try {
			values = database.fetchEntries();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//CharSequence[] items;
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i < values.size(); i++){
			names.add(values.get(i).getName());
		}
		CharSequence[] items = names.toArray(new CharSequence[names.size()]);
        //CharSequence[] items = {" Easy "," Medium "," Hard "," Very Hard "};
        // arraylist to keep the selected items
		
        final ArrayList<Integer> seletedItems=new ArrayList<Integer>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Friends");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int indexSelected,
                 boolean isChecked) {
             if (isChecked) {
                 // If the user checked the item, add it to the selected items
                 seletedItems.add(indexSelected);
             } else if (seletedItems.contains(indexSelected)) {
                 // Else, if the item is already in the array, remove it
                 seletedItems.remove(Integer.valueOf(indexSelected));
             }
         }
     })
      // Set the action buttons
     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int id) {
             //  Your code when user clicked on OK
             //  You can write the code  to save the selected item here
        	 
        	// System.out.println(seletedItems.size());
        	 
        	 for(int i = 0; i < seletedItems.size(); i++){
        		 selectedFriends.add(seletedItems.get(i));
        	 }
        	 
        	 try {
				displaySchedules();
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

         }
     })
     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int id) {
            //  Your code when user clicked on Cancel

         }
     });
        AlertDialog dialog;
        dialog = builder.create(); //create like this outside onClick
        dialog.show();
        
        return true;
}
	
	public void displaySchedules() throws StreamCorruptedException, SQLException, ClassNotFoundException, IOException{
		
		//this is where we will call the draw schedules method on all the selected db entries.
		//System.out.println(selectedFriends.get(0));
		for (int i = 0; i < selectedFriends.size(); i++){
		Friend temp = database.fetchEntryByIndex((long) selectedFriends.get(i) + 1);
		System.out.println(temp.getName());
		
		}
		
		selectedFriends.clear();
	}
	
	}

