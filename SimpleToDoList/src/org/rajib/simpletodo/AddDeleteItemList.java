package org.rajib.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.rajib.simpletodo.util.file.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddDeleteItemList extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private static final String fileName = "list_store_text.txt";
	private final int REQUEST_CODE = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_delete_list);
		// readItems();
		lvItems = (ListView) findViewById(R.id.lvItems);
		items = new ArrayList<String>();
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	public void addToDoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		String newStr = etNewItem.getText().toString().trim();
		itemsAdapter.add(newStr);
		saveItems(newStr);
		etNewItem.setText("");
	}

	private void setupListViewListener() {
		// Long click listener for the list item
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> aView, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetInvalidated();
				// Update the file after removing the item.
				updateFinalList();
				Toast toast = Toast.makeText(getApplicationContext(),
						"Item Removed from list", Toast.LENGTH_SHORT);
				toast.show();
				return true;
			}
		});

		// Onclick listener for the list item to make it editable
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View item, int pos,
					long id) {
				// Create an intent
				Intent intent = new Intent(AddDeleteItemList.this,
						EditItemList.class);

				// open Edit activity and pass on the the data of the item
				intent.putExtra("editText", items.get(pos));
				intent.putExtra("itemPos", pos);
				// Launch other activity, here Edit activity.
				startActivityForResult(intent, REQUEST_CODE);
			}

		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name value from result extras
			String etText = data.getExtras().getString("editText");
			int itemPos = data.getIntExtra("itemPos", 0);
			items.remove(itemPos);
			items.add(itemPos, etText);
			itemsAdapter.notifyDataSetInvalidated();
			updateFinalList();
			Toast toast = Toast.makeText(getApplicationContext(),
					"List Updated !!!", Toast.LENGTH_SHORT);
			toast.show();

		}
	}

	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, fileName);
		try {

			ArrayList<String> itemList = Utility.readLines(todoFile);

			if (itemList != null && itemList.size() > 0) {
				items.addAll(itemList);
			}

		} catch (IOException ex) {

		}

	}

	private void saveItems(String newText) {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, fileName);
		try {
			Utility.writeLines(todoFile, newText);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void updateFinalList() {
		// Delete the file
		deleteList();
		for (String lsText : items) {
			saveItems(lsText);
		}

	}

	private void deleteList() {
		// call delete file function
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, fileName);
		try {

			Utility.deleteLines(todoFile);

		} catch (Exception ex) {

		}

	}
}
