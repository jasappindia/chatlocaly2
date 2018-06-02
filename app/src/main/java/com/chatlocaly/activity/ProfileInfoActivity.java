package com.chatlocaly.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.api.account.user.UserService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.model.LoginModel;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.model.UpdateProfileModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.ui.RoundedImageView;
import com.chatlocaly.utill.BasicUtill;
import com.google.android.gms.common.api.Api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileInfoActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    public static String selectedImagePath;/*, filepath;*/


    public String filePath;
    private Button btn_takePhoto, btn_next;
    private TextView tv_chooseGallery;
    private RoundedImageView iv_profilePic;
    private Bitmap bitmapUser;
    private RequestListener target;
    private Uri fileUri = null;
    private EditText et_userName;
    private Utill util;
    private Chatlocalyshareprefrence preference;
    private Context context;

    private String user_id;
    private String Tag="ProfileInfoActivity";
    private String TAG=ProfileInfoActivity.class.getName();

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight)
    {



        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(path, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        init();

        target = new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
        /*     iv_profilePic.setBackgroundResource(R.drawable.user_not_available);
                iv_profilePic.setImageResource(R.drawable.user_not_available);
       */
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        };

        // check multiple device login
        new BasicUtill().CheckStatus(this);


    }

    private void init() {


        util = new Utill();
        context = ProfileInfoActivity.this;
        preference = new Chatlocalyshareprefrence(this);
        iv_profilePic = (RoundedImageView) findViewById(R.id.iv_profilePic);
        btn_takePhoto = (Button) findViewById(R.id.btn_takePhoto);
        btn_next = (Button) findViewById(R.id.btn_next);
        tv_chooseGallery = (TextView) findViewById(R.id.tv_chooseGallery);
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_userName.addTextChangedListener(this);
        btn_takePhoto.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_chooseGallery.setOnClickListener(this);
        btn_next.setFocusable(false);
        btn_next.setClickable(false);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_takePhoto:
                takePhoto();
                break;
            case R.id.btn_next:

                if (et_userName.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(context, " please fill all entry", Toast.LENGTH_LONG).show();
             /*   else if( filePath == null || filePath.equalsIgnoreCase(""))
                    Toast.makeText(context, " Please  en  ", Toast.LENGTH_LONG).show();
             */
                else
                    callImageUpload(filePath, preference.getUserId(), et_userName.getText().toString().trim(), "ecddd");
                break;
            case R.id.tv_chooseGallery:
                chooseFromGallery();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestMultiplePermissions(int requestCode) {

        String camera_permission = Manifest.permission.CAMERA;
        int hascampermission = checkSelfPermission(camera_permission);

        String storage_permission_group = Manifest.permission.READ_EXTERNAL_STORAGE;
        int hasStoragePermission = checkSelfPermission(storage_permission_group);

        String storage_writepermission_group = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int hasstroage = checkSelfPermission(storage_permission_group);

        List<String> permissions = new ArrayList<String>();

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(storage_permission_group);
        }
        if (hascampermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(camera_permission);
        }

        if (hasstroage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(storage_writepermission_group);
        }

        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, requestCode);
        }

    }




    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    takePhoto();
                }
                break;

            case SELECT_PICTURE:
                if(grantResults[1]==PackageManager.PERMISSION_GRANTED)
                {
                    chooseFromGallery();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(CameraIntent, CAMERA_REQUEST);
            } else {
                requestMultiplePermissions(CAMERA_REQUEST);

            }
        } else {
            Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(CameraIntent, CAMERA_REQUEST);
        }
    }

    public void chooseFromGallery() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECT_PICTURE);
            } else {
                requestMultiplePermissions(SELECT_PICTURE);
            }
        }
        else
        {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, SELECT_PICTURE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();
                    iv_profilePic.setImageURI(selectedImage);

/*
                    bitmapUser = BitmapFactory.decodeFile(filePath);
                    iv_profilePic.setImageBitmap(bitmapUser);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapUser.compress(Bitmap.CompressFormat.PNG, 50, stream);

                */    //  Glide.with(context).load(stream.toByteArray()).listener(target).into(iv_profilePic);


                }
                else if (requestCode == CAMERA_REQUEST) {

                    Uri imageUri = null;
                    try {
                        if (data != null) {
                            imageUri = data.getData();
                            filePath = getPath(imageUri);
                        } else {
                            imageUri = fileUri;
                            filePath = imageUri.getPath();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }

                    if (filePath != null) {
                        bitmapUser = decodeSampledBitmapFromResource(filePath, 600, 600);
                    } else {
                        Bundle extras = data.getExtras();
                        bitmapUser = (Bitmap) extras.get("data");
                    }

                    if (bitmapUser != null) {
                        try {

                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmapUser.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                            File destination = new File(Environment.getExternalStorageDirectory(),
                                    System.currentTimeMillis() + ".jpg");
                            FileOutputStream fo;
                            filePath = destination.getAbsolutePath();
                            try {
                                destination.createNewFile();
                                fo = new FileOutputStream(destination);
                                fo.write(bytes.toByteArray());
                                fo.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iv_profilePic.setImageBitmap(bitmapUser);

                    } else {
//                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_fetch), Toast.LENGTH_LONG).show();
                    }
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Toast.makeText(getApplicationContext(), " " + getResources().getString(R.string.str_cancelled), Toast.LENGTH_LONG).show();
            } else if (resultCode != Activity.RESULT_CANCELED) {
                if (requestCode == CAMERA_REQUEST) {
                    bitmapUser = (Bitmap) data.getExtras().get("data");
                    iv_profilePic.setImageBitmap(bitmapUser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor;
        if (Build.VERSION.SDK_INT > 19) {
            // Will return "image:x*"
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, sel, new String[]{id}, null);
        } else {
            cursor = getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {

        }
        return path;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() > 2) {
            btn_next.setFocusable(true);
            btn_next.setClickable(true);
            btn_next.setTextColor(ContextCompat.getColor(this, R.color.white));
            btn_next.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            btn_next.setFocusable(false);
            btn_next.setClickable(false);
            btn_next.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
            btn_next.setBackgroundColor(getResources().getColor(R.color.button_back));

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /*************************
     * call image update
     *********************/

    public void callImageUpload(String mediaPath, String c_user_id, final String fullname, String encrypt) {

        final ProgressDialog pDialogofimaged = ProgressDialog.show(context, "", "Please wait ...", true);
        pDialogofimaged.setCancelable(false);

        //      progressDialog.show();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("c_user_id",createPartFromString(c_user_id));
        params.put("c_full_name", createPartFromString(fullname));
        params.put("encrypt_key", createPartFromString(encrypt));


        Call<ResultModel> call = null;
        if (mediaPath != null) {
            File file = new File(mediaPath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            //MultipartBody.Part body = MultipartBody.Part.createFormData("group_image", file.getName(), reqFile);
            //  RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "group_image");
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), mediaPath);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("c_profile_image", file.getName(), reqFile);
            call = apiService.uploadFile(fileToUpload, filename, params);

        } else
            call = apiService.uploadFile(null, null, params);
             call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                pDialogofimaged.dismiss();
                ResultModel resultModel = response.body();



                if (resultModel != null && resultModel.getData() != null && resultModel.getData().getResultCode().equalsIgnoreCase("1")) {

                  // call user details update  on applozic account
                //     new UserDetailUpdate().execute(fullname);

                    loginByApplozic(pDialogofimaged,fullname);





                } else {
                    Utill.showCenteredToast(context, "" + resultModel.getData().getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {


                pDialogofimaged.dismiss();
            }
        });


    }

    private RequestBody createPartFromString(String value) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestFile;
    }


public class  UserDetailUpdate extends AsyncTask<String,Void,Void>
{

    @Override
    protected Void doInBackground(String... voids) {

        String name=voids[0];
        //  user name update on applozic
        UserService userService=UserService.getInstance(context);
        String  responce = userService.updateDisplayNameORImageLink(name,null,null,null);


        return null;
    }
}


    private void loginByApplozic( final ProgressDialog pDialog, String name) {

        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                //After successful registration with Applozic server the callback will come here
                Log.d("applozic login","login success");

                if(registrationResponse!=null) {

                  preference.setApplozic_key(registrationResponse.getDeviceKey());
                    pDialog.dismiss();


             ///       Utill.showCenteredToast(context, "" + resultModel.getData().getMessage());
                    Intent i = new Intent(context, MainActivity.class);
                    startActivity(i);
                    finish();


                }
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                //If any failure in registration the callback  will come here
                pDialog.dismiss();

                finish();

            }};
        String s[]=name.split(" ");

        User user = new User();
        user.setUserId(Utill.getApplozicUserId(preference.getUserId()));

        //Log.e("userId",""+Utill.getApplozicUserId(preference.getUserId()));
        user.setAuthenticationTypeId(User.AuthenticationType.CLIENT.getValue());
        user.setPassword("chatlocaly123456");
        user.setDisplayName(s[0]);


        //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
        //userId it can be any unique user identifier NOTE : +,*,? are not allowed chars in userId.
       /* user.setDisplayName(displayName); //displayName is the name of the user which will be shown in chat messages
        user.setEmail(email); //optional
        user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());  //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
        user.setPassword(""); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
        user.setImageLink("");//optional, set your image link if you have
//       */
        new UserLoginTask(user, listener, this).execute((Void) null);





    }


public void downloadfile()
{

    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    Call<ResponseBody> call=apiInterface.downloadFile("http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-34/20180411102205pul3ej2pxl.png");

    call.enqueue(new Callback<ResponseBody>() {


        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
        {


        //    http://184.154.53.181/chatlocaly/assets/uploads/businesses/business-34/20180411102205pul3ej2pxl.png
writeResponseBodyToDisk(response.body());
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t)
        {

        }
    });




}



    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }



}
