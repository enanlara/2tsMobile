package br.com.a2ts_mobile.Synchronization_Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.a2ts_mobile.Things_Manager.ThingsModel;
import br.com.a2ts_mobile.Url;
import br.com.a2ts_mobile.User_Manager.UserModel;
import br.com.a2ts_mobile.Util.StringDeserialization;
import br.com.a2ts_mobile.Util.onResponseRetrofitListnnerSynchonize;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Enan on 6/17/2017.
 */

public class SynchronizeThings extends AsyncTask<Void, Void, String> {
    private Context context;
    public ProgressDialog dialog;
    public onResponseRetrofitListnnerSynchonize listnner;
    private ThingsModel thingsModel;

    public SynchronizeThings(Context context, onResponseRetrofitListnnerSynchonize listnner, ThingsModel thingsModel) {
        this.context = context;
        this.listnner = listnner;
        this.thingsModel = thingsModel;
    }
    @Override
    protected String doInBackground(Void... params) {
        return editThings();
    }

    private String editThings(){
        try {

            Call<String> listThingsService = null;

            String baseUrl = Url.UrlDeACesso;

            Gson gsonConverter = new GsonBuilder().registerTypeAdapter(String.class, new StringDeserialization())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                    .build();

            final SynchronizationThingsService services = retrofit.create(SynchronizationThingsService.class);

             listThingsService = services.synchronizeThings(UserModel.TOKEN.trim(), thingsModel.getNrThings1().toString().trim(), thingsModel.getLocationCurrent().getId().toString().trim(), thingsModel.getSituation().trim(), thingsModel.getState().trim(), (thingsModel.getNote().isEmpty()?" ":thingsModel.getNote().trim()), thingsModel.getLocation().getId().toString());


            String response = listThingsService.execute().body();
            Log.i("EXC----------------", response);

            return response;
        }catch (Exception e){
            Log.i("EXCEÇÃO----------------", e.getMessage());
            return null;
        }
    }


    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "updating", "Wait...", true, true );
        dialog.setCancelable(false);
    }
    @Override
    protected void onPostExecute(String response) {
        listnner.responseEditThing(response);
        dialog.dismiss();
    }

}

