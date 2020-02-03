package hongvinhlcode.app.todolist.Main.Main.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import hongvinhlcode.app.todolist.Main.Main.About.AboutActivity;
import hongvinhlcode.app.todolist.Main.Main.Create.CreateActivity;
import hongvinhlcode.app.todolist.Main.Main.Database.Database;
import hongvinhlcode.app.todolist.Main.Main.Setting.SettingActivity;
import hongvinhlcode.app.todolist.Main.Main.ToDo.ToDo;
import hongvinhlcode.app.todolist.Main.Main.ToDo.ToDoAdapter;
import hongvinhlcode.app.todolist.R;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ImageButton btnAdd;
    LinkedList<ToDo> listToDo;
    ToDoAdapter adapter;

    Database database;

    String title, des, remind;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listToDo = new LinkedList<>();
        adapter = new ToDoAdapter(this, R.layout.row_list, listToDo);
        listView.setAdapter(adapter);

        btnAdd = findViewById(R.id.btnAdd);
        getSupportActionBar().setTitle("All Tasks");

        database = new Database(this, "todolist.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS ToDo3(id INTEGER PRIMARY KEY AUTOINCREMENT,title VARCHAR(200)," +
                "des VARCHAR(200),remind VARCHAR(200))");
        AddToDo();

        final Animation animButtonAdd= AnimationUtils.loadAnimation(this,R.anim.anim_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButtonAdd);
                startActivity(new Intent(MainActivity.this, CreateActivity.class));
                overridePendingTransition(R.anim.anim_enter_create,R.anim.anim_exit_create);
            }
        });
        AddDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                overridePendingTransition(R.anim.anim_enter_create,R.anim.anim_exit_create);
                break;
            case R.id.menu_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                overridePendingTransition(R.anim.anim_enter_create,R.anim.anim_exit_create);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddToDo() {
        Cursor dataToDo = database.GetData("SELECT * FROM ToDo3");
        listToDo.clear();
        while (dataToDo.moveToNext()) {
            int id = dataToDo.getInt(0);
            String title = dataToDo.getString(1);
            String des = dataToDo.getString(2);
            String remind = dataToDo.getString(3);
            listToDo.add(new ToDo(id, title, des, remind));
        }
        adapter.notifyDataSetChanged();
    }

    private void AddDatabase() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        if (bundle != null) {
            title = bundle.getString("title");
            des = bundle.getString("des");
            remind = bundle.getString("remind");
            database.QueryData("INSERT INTO ToDo3 VALUES(null,'" + title + "','" + des + "','" + remind + "')");
            AddToDo();
        }
    }

    public void ShowDialogDone(final int getID) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Have you finished yet?");
        builder.setPositiveButton("Of course", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM ToDo3 WHERE id='" + getID + "'");
                Toast.makeText(MainActivity.this, "Congratulation", Toast.LENGTH_SHORT).show();
                AddToDo();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}

