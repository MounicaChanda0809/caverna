package ch.quazz.caverna.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.GamesTable;
import ch.quazz.caverna.R;
import ch.quazz.caverna.games.Game;

public class MainActivity extends Activity {

    private List<Game> games;
    private CavernaDbHelper dbHelper;
    private GamesTable gamesTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (games == null) {
            games = new ArrayList<Game>();
            dbHelper = new CavernaDbHelper(this);
            gamesTable = new GamesTable(dbHelper);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void newGame(View view) {
        long id = gamesTable.add(Calendar.getInstance().getTimeInMillis());

        // pass id to game activity

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        gamesTable.load(games);

        ListView listView = (ListView)findViewById(R.id.games);
        listView.setAdapter(new GamesAdapter(this, games));
    }
}
