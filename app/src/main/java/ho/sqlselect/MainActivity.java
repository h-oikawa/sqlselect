package ho.sqlselect;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    /* CreateDumyボタンClickリスナー */
    private OnClickListener buttonCreateDumy_OnClickListener = new OnClickListener(){
        public void onClick(View v) {buttonCreateDumyData_OnClick(v);}};
    /* CreateDumy02ボタンClickリスナー */
    private OnClickListener buttonCreateDumy02_OnClickListener = new OnClickListener(){
        public void onClick(View v) {buttonCreateDumy02Data_OnClick(v);}};
    /* buttonRowQueryボタンClickリスナー 　*/
    private OnClickListener buttonRowQuery_OnClickListener = new OnClickListener(){
        public void onClick(View v) {buttonRowQuery_OnClick(v);}};
    /* buttonQueryボタンClickリスナー 　*/
    private OnClickListener buttonQuery_OnClickListener = new OnClickListener(){
        public void onClick(View v) {buttonQuery_OnClick(v);}};

    /*
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        Button btnCreateDumy = (Button)this.findViewById(R.id.btnCreateDumy);
        btnCreateDumy.setOnClickListener(buttonCreateDumy_OnClickListener);
        Button btnCreateDumy02 = (Button)this.findViewById(R.id.btnCreateDumy02);
        btnCreateDumy02.setOnClickListener(buttonCreateDumy02_OnClickListener);
        Button btnRowQuery = (Button)this.findViewById(R.id.buttonRowQuery);
        btnRowQuery.setOnClickListener(buttonRowQuery_OnClickListener);
        Button btnQuery = (Button)this.findViewById(R.id.buttonQuery);
        btnQuery.setOnClickListener(buttonQuery_OnClickListener);
    }

    /*
     * ダミーデータ作成ボタン　クリック処理
     */
    private void buttonCreateDumyData_OnClick(View v){

        //現在日時を取得
        Date date = new Date();
        //表示形式を設定
        SimpleDateFormat sdf_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        SimpleDateFormat sdf_d = new SimpleDateFormat("dd");
        SimpleDateFormat sdf_t = new SimpleDateFormat("kk':'mm':'ss");

        DatabaseHelper dbh = new DatabaseHelper(this);
        SQLiteDatabase db=dbh.getWritableDatabase();
        long ret;
        try{
            ContentValues values = new ContentValues();
            for (int i=0; i<1; i++){
                values.put("Year", sdf_y.format(date));
                values.put("Month", sdf_m.format(date));
                values.put("Day", sdf_d.format(date));
                values.put("Time", sdf_t.format(date));
                values.put("Work", "出勤" );
                db.insert("MyTable", null, values);
                values.clear();
            }

        }finally{
            db.close();
        }

        Toast.makeText(this, "今日も1日がんばりましょう(^O^)／", Toast.LENGTH_SHORT).show();

    }

    /*
     * ダミーデータ02作成ボタン　クリック処理
     */
    private void buttonCreateDumy02Data_OnClick(View v){

        //現在日時を取得
        Date date = new Date();
        //表示形式を設定
        SimpleDateFormat sdf_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        SimpleDateFormat sdf_d = new SimpleDateFormat("dd");
        SimpleDateFormat sdf_t = new SimpleDateFormat("kk':'mm':'ss");

        DatabaseHelper dbh = new DatabaseHelper(this);
        SQLiteDatabase db=dbh.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            for (int i=0; i<1; i++){
                values.put("Year", sdf_y.format(date));
                values.put("Month", sdf_m.format(date));
                values.put("Day", sdf_d.format(date));
                values.put("Time", sdf_t.format(date));
                values.put("Work", "退勤" );
                db.insert("MyTable", null, values);
                values.clear();
            }
        }finally{
            db.close();
        }

        Toast.makeText(this, "1日おつかれさまでしたm(_ _)m", Toast.LENGTH_SHORT).show();

    }


    /*
     * RowQueryボタン　クリック処理
     */
    private void buttonRowQuery_OnClick(View v) {
        //SQL作成
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" No");
        sql.append(" ,Year");
        sql.append(" ,Month");
        sql.append(" ,Day");
        sql.append(" ,Time");
        sql.append(" ,Work");
        sql.append(" FROM MyTable;");

        //rawQueryメソッドでデータを取得
        DatabaseHelper dbh = new DatabaseHelper(this);
        SQLiteDatabase db = dbh.getReadableDatabase();
        try {
            Cursor cr = db.rawQuery(sql.toString(), null);
            //TextViewに表示
            StringBuilder text = new StringBuilder();
            while (cr.moveToNext()) {
                text.append(cr.getInt(0));
                text.append("  " + cr.getString(1));
                text.append("/" + cr.getString(2));
                text.append("/" + cr.getString(3));
                text.append("  " + cr.getString(4)); //dayとtimeの間
                text.append("---" + cr.getString(5)); //timeとworkの間
                text.append("\n");

                TextView lblList = (TextView) this.findViewById(R.id.labelList);
                lblList.setText(text);

            }
        } finally {
            db.close();
        }
    }

    /*
     * queryボタン　クリック処理
     * No.5の情報を表示
     */
    private  void buttonQuery_OnClick(View v){
        //queryメソッドでデータを取得
        String[] cols = {"No","Year","Month","Day","Time","Work"};
        String selection = "Month = ?";
        String[] selectionArgs = {"11"};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        try{
            Cursor cr = db.query("MyTable", cols, selection, selectionArgs, groupBy, having, orderBy);
            //TextViewに表示
            StringBuilder text = new StringBuilder();
            while (cr.moveToNext()){
                text.append(cr.getInt(0));
                text.append("  " + cr.getString(1));
                text.append("/" + cr.getString(2));
                text.append("/" + cr.getString(3));
                text.append("  " + cr.getString(4));
                text.append("---" + cr.getString(5));
                text.append("\n");

                TextView lblList = (TextView)this.findViewById(R.id.labelList);
                lblList.setText(text);

            }
        }finally{
            db.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    //action berのボタンを押して画面遷移する
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
