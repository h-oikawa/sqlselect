package ho.sqlselect;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_view);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        setContentView(ll);

        ListView lv = new ListView(this);

        //SQLの検索
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" No");
        sql.append(" ,Year");
        sql.append(" ,Month");
        sql.append(" ,Day");
        sql.append(" ,Time");
        sql.append(" ,Work");
        sql.append(" FROM MyTable;");

        //Adapterの作成
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //rawQueryメソッドでデータを取得
        DatabaseHelper dbh = new DatabaseHelper(this);
        SQLiteDatabase db = dbh.getReadableDatabase();

        try {
            Cursor cr = db.rawQuery(sql.toString(), null);



            while (cr.moveToNext()) {
                //DB文字列の連結
                StringBuilder text = new StringBuilder();
                text.append(cr.getInt(0));
                text.append("  " + cr.getString(1));
                text.append("/" + cr.getString(2));
                text.append("/" + cr.getString(3));
                text.append("  " + cr.getString(4)); //dayとtimeの間
                text.append("---" + cr.getString(5)); //timeとworkの間
                //text.append("\n");
                String str = new String(text);
                ad.add(str);
            }



        }finally {
            db.close();
        }
        lv.setAdapter(ad);
        ll.addView(lv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    //action berのボタンを押して画面遷移する
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
