package com.example.blogapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blogapp.R;
import com.example.blogapp.model.Post;
import com.example.blogapp.viewmodel.NewPostViewModel;

import java.util.Calendar;
import java.util.Date;

import static com.example.blogapp.ui.MainActivity.EXTRA_DATA_ID;

public class NewPostActivity extends AppCompatActivity {

    private EditText mEditTitleView;
    private EditText mEditContentView;
    private TextView mDateView;

    private int mId;
    private Post mPost;
    private NewPostViewModel mNewPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mEditTitleView = findViewById(R.id.postTitleEditView);
        mEditContentView = findViewById(R.id.postDescEditView);
        mDateView = findViewById(R.id.dateTextView);


        mId = -1 ;

        mNewPostViewModel = ViewModelProviders.of(this).get(NewPostViewModel.class);

        Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            mId = extras.getInt(EXTRA_DATA_ID, -1);
        }

        if (mId == -1){
            mPost = new Post();
            mPost.setPostDate(Calendar.getInstance().getTime());
            populateUI(mPost);
        }else {
            mNewPostViewModel.getPostById(mId).observe(this, new Observer<Post>() {
                @Override
                public void onChanged(Post post) {
                    mPost = post;
                    populateUI(post);
                }
            });
        }
    }

    private void populateUI(Post post) {
        if (post != null){
            mEditTitleView.setText(post.getTitle());
            mEditContentView.setText(post.getContent());
            mDateView.setText(post.getPostDate().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            // Create a new Intent for the reply.
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditTitleView.getText()) &&
                    TextUtils.isEmpty(mEditContentView.getText())) {
                // No post was entered, set the result accordingly.
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                // Get the new post that the user entered.
                mPost.setTitle(mEditTitleView.getText().toString());
                mPost.setContent(mEditContentView.getText().toString());

                if (mId == -1 ){
                    mNewPostViewModel.insert(mPost);
                }else {
                    mNewPostViewModel.update(mPost);
                }
                // Set the result status to indicate success.
                setResult(RESULT_OK, replyIntent);
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.datepicker));
    }

    public void setPostDate(Date date){
        mPost.setPostDate(date);
        mDateView.setText(date.toString());
    }
}
