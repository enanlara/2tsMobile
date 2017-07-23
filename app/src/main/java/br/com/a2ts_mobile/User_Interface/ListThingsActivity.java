package br.com.a2ts_mobile.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.a2ts_mobile.R;
import br.com.a2ts_mobile.Things_Manager.SearchForThingsAsync;
import br.com.a2ts_mobile.Things_Manager.ThingsModel;

public class ListThingsActivity extends AppCompatActivity {

    private ListView listWiewThings;
    private List<ThingsModel> listThingsModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_things);
        Intent intent = getIntent();
        String location = (String) intent.getSerializableExtra("location");

        listWiewThings = (ListView)findViewById(R.id.list_things);


        listWiewThings.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ThingsModel thingsModel = (ThingsModel) listWiewThings.getItemAtPosition(position);
                Intent intent = new Intent(ListThingsActivity.this, DetailsThingsActivity.class);
                intent.putExtra("things", thingsModel);
                startActivity(intent);
            }

        });



              final SearchForThingsAsync sync = new SearchForThingsAsync(ListThingsActivity.this, new SearchForThingsAsync.onResponseRetrofitListnner() {
                @Override
                public void responseThings(List<ThingsModel> response) {
                    if(response == null){
                        Toast.makeText(ListThingsActivity.this, "Nenhum objeto encontrado!", Toast.LENGTH_SHORT).show();
                    }else {
                        listThingsModels = response;
                        if (listThingsModels != null) {
//                    Log.i("ddddd", String.valueOf(response.size()));
//                    List<ThingsModel> listThingsModel = new ArrayList<ThingsModel>();
                            ArrayAdapter<ThingsModel> adapter = new ArrayAdapter<ThingsModel>(ListThingsActivity.this, android.R.layout.simple_list_item_1, response);
                            listWiewThings.setAdapter(adapter);
                        }
                    }

                }
            });

            sync.execute(location,"ola");



        registerForContextMenu(listWiewThings);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem editar = menu.add("Editar");
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                ThingsModel thingsModel = (ThingsModel) listWiewThings.getItemAtPosition(info.position);

                Intent intent = new Intent(ListThingsActivity.this, EditThingsActivity.class);
                intent.putExtra("things", thingsModel);
                startActivity(intent);
                //Toast.makeText(ListThingsActivity.this, thingsModel.getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}