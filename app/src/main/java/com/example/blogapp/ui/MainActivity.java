package com.example.blogapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.blogapp.R;
import com.example.blogapp.adapter.PostListAdapter;
import com.example.blogapp.db.Converters;
import com.example.blogapp.model.Post;
import com.example.blogapp.viewmodel.PostViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_POST_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_POST_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_ID = "extra_data_id";

    private PostViewModel mPostViewModel;
    private AlertDialog.Builder mAlertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PostListAdapter adapter = new PostListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the PostViewModel.
        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        // Get all the posts from the database
        // and associate them to the adapter.
        mPostViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable final List<Post> posts) {
                // Update the cached copy of the posts in the adapter.
                adapter.setPosts(posts);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewPostActivity.class);
                startActivityForResult(intent, NEW_POST_ACTIVITY_REQUEST_CODE);
            }
        });

        adapter.setOnItemClickListener(new PostListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Post post = adapter.getPostAtPosition(position);
                launchUpdatePostActivity(post);
            }
        });

        mAlertBuilder = new AlertDialog.Builder(MainActivity.this);

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app.
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a post,
                    // delete that post from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final int position = viewHolder.getAdapterPosition();
                        final Post mPost = adapter.getPostAtPosition(position);


                        // Set the dialog title and message.
                        mAlertBuilder.setTitle("Delete a Post");
                        mAlertBuilder.setMessage("This wil delete "+mPost.getTitle()+" permanently");

                        // Add the dialog buttons.
                        mAlertBuilder.setPositiveButton(R.string.ok_button,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                getString(R.string.delete_post_preamble) + " " +
                                                        mPost.getTitle(), Toast.LENGTH_LONG).show();
                                        // Delete the post.
                                        mPostViewModel.deletePost(mPost);
                                    }
                                });

                        mAlertBuilder.setNegativeButton(R.string.cancel_button,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // User cancelled the dialog.
                                        adapter.notifyItemChanged(position);
                                    }
                                });
                        // Create and show the AlertDialog.
                        mAlertBuilder.show();
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {

            // Set the dialog title and message.
            mAlertBuilder.setTitle("Clear all data");
            mAlertBuilder.setMessage("This wil delete al your posts permanently");

            // Add the dialog buttons.
            mAlertBuilder.setPositiveButton(R.string.ok_button,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Add a toast just for confirmation
                            Toast.makeText(getApplicationContext()
                                    , R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();
                            // Delete the existing data.
                            mPostViewModel.deleteAll();
                        }
                    });

            mAlertBuilder.setNegativeButton(R.string.cancel_button,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // User cancelled the dialog.
                        }
                    });
            // Create and show the AlertDialog.
            mAlertBuilder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_POST_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            //TODO: toast for successful inset
        } else if (requestCode == UPDATE_POST_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            //TODO: Toast for successful update
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    private void launchUpdatePostActivity(Post post) {
        Intent intent = new Intent(this, NewPostActivity.class);
        intent.putExtra(EXTRA_DATA_ID, post.getId());
        startActivityForResult(intent, UPDATE_POST_ACTIVITY_REQUEST_CODE);
    }
}
