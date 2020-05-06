package com.ddhuy4298.demomvvm.activities.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.MutableLiveData;

import com.t3h.basemodule.base.BaseViewModel;

import java.net.URL;
import java.net.URLConnection;

public class MainViewModel extends BaseViewModel {

    private MutableLiveData<Bitmap> value = new MutableLiveData<>();

    public MutableLiveData<Bitmap> getValue() {
        return value;
    }

    public void startCounter(String link) {
        AsyncAction<Bitmap> b = new AsyncAction<Bitmap>() {
            @Override
            public Bitmap doAction() throws Exception {
                URL url = new URL(link);
                URLConnection connection = url.openConnection();
                return BitmapFactory.decodeStream(connection.getInputStream());
            }
        };
        doAction(b, value);
    }
}
