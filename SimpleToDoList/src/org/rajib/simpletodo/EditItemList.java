package org.rajib.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class EditItemList extends Activity {

	private int itemPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item_list);
		// Get the text to be edited
		String editText = getIntent().getStringExtra("editText");
		itemPos = getIntent().getIntExtra("itemPos", 0);
		// Get the edit text view
		EditText etEditView = (EditText) findViewById(R.id.editOldItem);
		etEditView.setText(editText);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	public void onSubmit(View v) {
		EditText etText = (EditText) findViewById(R.id.editOldItem);
		String text = etText.getText().toString();
		// create an Intent
		Intent data = new Intent();
		data.putExtra("editText", text);
		data.putExtra("itemPos", itemPos);

		// set the result code and intent
		setResult(RESULT_OK, data);

		getApplicationContext();
		// Hide the Keyboard
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etText.getWindowToken(), 0);
		// Finish the activity to return to calling activity
		finish();

	}

}
