package br.com.a2ts_mobile.Util;

import java.util.List;

import br.com.a2ts_mobile.Things_Manager.LocationModel;
import br.com.a2ts_mobile.User_Manager.UserModel;

/**
 * Created by enan on 23/09/17.
 */

public interface onResponseRetrofitListnnerLocations
{
        public void responseLocations(List<LocationModel> response);
}
