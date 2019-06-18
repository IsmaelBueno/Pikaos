package com.example.ismael.pikaos.UI.newTeam;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.ApiRestClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTeamInteractorImpl {

    private final int MINTEAMNAMELENGTH = 3;
    private final int MAXTEAMNAMELENGTH = 50;

    private final String regex = "^[A-Za-zñÑ0-9\\s]*$";

    interface NewTeamInteractor{
        void onSuccess();
        void onMessageError(int error);
        void onErrorName(int error);
    }

    public void createTeam(final NewTeamInteractor listener, String nameTeam, Bitmap mBitmap) {

        if(!nameTeam.matches(regex) ||nameTeam.trim().length() > MAXTEAMNAMELENGTH || nameTeam.trim().length() < MINTEAMNAMELENGTH)
            listener.onErrorName(R.string.error_newTeam_wrong_name);
        else {

            try {
                File filesDir = PikaosApplication.getAppContext().getFilesDir();
                File file = new File(filesDir, "image" + ".png");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();


                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();


                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

                Log.d("puta",nameTeam);

                Call<ResponseBody> req = ApiRestClient.getInstance().createTeam(PikaosApplication.token,nameTeam,body, name);
                req.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        switch (response.code()) {
                            case 200:
                                listener.onSuccess();
                                break;
                            case 409:
                                listener.onMessageError(R.string.error_team_name_exists);
                                break;
                            default:
                                listener.onMessageError(R.string.error_unexpeted);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onMessageError(R.string.error_cant_connect);
                    }
                });


            } catch (FileNotFoundException e) {
                listener.onMessageError(R.string.error_file_cant_find);
            } catch (IOException e) {
                listener.onMessageError(R.string.error_unexpeted);
            }
        }
    }
}
