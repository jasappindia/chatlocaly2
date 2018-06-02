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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.ApplozicClient;
import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.api.account.user.UserLogoutTask;
import com.applozic.mobicomkit.uiwidgets.conversation.adapter.DetailedConversationAdapter;
import com.applozic.mobicommons.commons.core.utils.Utils;
import com.bumptech.glide.request.RequestListener;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.chat.ApplozicBridge;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.global.Constants;
import com.chatlocaly.imageView.CircleImageView;
import com.chatlocaly.model.BusinessMemberProfile;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.model.UpdateProfileModel;
import com.chatlocaly.model.UserProfileModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.textview.TextViewReguler;
import com.chatlocaly.ui.RoundedImageView;
import com.chatlocaly.utill.BasicUtill;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chatlocaly.utill.Utill.calculateInSampleSize;
import static com.chatlocaly.utill.Utill.isConnectingToInternet;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {


    /*************** *******************/
    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    public static String selectedImagePath;/*, filepath;*/
    public String filePath;
    protected TextView toolbarTitle;
    protected Toolbar toolbar;
    protected ImageView ci_back;
    protected ImageView ivEditeImage, ivEditeUpdateName;
    protected EditText edName;
    protected ImageView ivEditeName;
    CircleImageView ivUserProfile;
    protected EditText edNumber;
    protected TextView tvNumber, tvEditeNum, tvEditemobielnum_Update, tvUsername;

    private Toolbar mToolbar;
    private TextView tv_toolbartitle;
    private Button btn_takePhoto, btn_logout;
    private TextView tv_chooseGallery;
    private Bitmap bitmapUser;
    private RequestListener target;
    private Uri fileUri = null;
    private EditText et_userName;
    private Utill util;
    private Chatlocalyshareprefrence preference;
    private Context context;
    private String user_id;
    private String mCurrentPhotoPath;
    private int height=450,width=720;
    private AlertDialog alertdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_user_profile);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbartitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv_toolbartitle.setText("Your Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        initView();

        if(getIntent()!=null  && getIntent().hasExtra(DetailedConversationAdapter.CHAT_USER_ID))
        {

          // hide button

            ivEditeName.setVisibility(View.INVISIBLE);
            ivEditeUpdateName.setVisibility(View.INVISIBLE);
            ivEditeImage.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
            getBusinessUserProfile(preference.getUserId(),""+getIntent().getExtras().getString(DetailedConversationAdapter.CHAT_USER_ID),preference.getEncryptKey());


        }
        else
        {
            setOnClick();
            // hide and show function for change mobile number
           getUserProfile(preference.getUserId(),preference.getEncryptKey());
            // setUser mobile number
            preference.getMOBILENUMBER();
            //
            tvNumber.setText(""+preference.getMOBILENUMBER());
            tvUsername.setText(""+preference.getFullName());
            Utill.imageshow(context,ivUserProfile,preference.getUserImage());




        }





        // check multiple device login
        new BasicUtill().CheckStatus(this);





    }

    private void hidemobilenumbertextview() {

        tvNumber.setVisibility(View.GONE);
        edNumber.setVisibility(View.VISIBLE);
        tvEditeNum.setVisibility(View.GONE);
        tvEditemobielnum_Update.setVisibility(View.VISIBLE);


    }

    private void showmobilenumber() {

        tvNumber.setVisibility(View.VISIBLE);
        edNumber.setVisibility(View.GONE);
        tvEditeNum.setVisibility(View.VISIBLE);
        tvEditemobielnum_Update.setVisibility(View.GONE);

    }

    private void hideprofilename() {


        tvUsername.setVisibility(View.GONE);
        edName.setVisibility(View.VISIBLE);
        ivEditeName.setVisibility(View.GONE);
        edName.requestFocus();
        edName.setSelection(edName.getText().length());

        ivEditeUpdateName.setVisibility(View.VISIBLE);


    }

    private void showprofilename() {


        if(!TextUtils.isEmpty(edName.getText().toString().trim()) )
        {

            updateName(edName.getText().toString().trim());

        }
        else
        {
          Utill.showCenteredToast(context,"Please enter your name");
        }


    }

    private void setOnClick() {


        toolbar.setOnClickListener(UserProfileActivity.this);
        ivUserProfile.setOnClickListener(UserProfileActivity.this);
        ivEditeImage.setOnClickListener(UserProfileActivity.this);
        edName.setOnClickListener(UserProfileActivity.this);
        tvUsername.setOnClickListener(UserProfileActivity.this);
        ivEditeName.setOnClickListener(UserProfileActivity.this);
        edNumber.setOnClickListener(UserProfileActivity.this);
        tvNumber.setOnClickListener(UserProfileActivity.this);
        tvEditeNum.setOnClickListener(UserProfileActivity.this);
        tvEditemobielnum_Update.setOnClickListener(UserProfileActivity.this);
        btn_logout.setOnClickListener(UserProfileActivity.this);
        ivEditeUpdateName.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        if(Utill.isConnectingToInternet(context))
        {

            if (view.getId() == R.id.toolbar_title) {

            } else if (view.getId() == R.id.toolbar) {


            } else if (view.getId() == R.id.iv_user_profile) {

            } else if (view.getId() == R.id.iv_edite_image) {

                if (isConnectingToInternet(context)) {
                    Dialog dialog = chooserDialog(getString(R.string.upload_image_Dialog));
                    dialog.show();
                } else {
                    Utill.showCenteredToast(context, getResources().getString(R.string.str_check_internet_connection));
                }

            } else if (view.getId() == R.id.ed_name) {

            } else if (view.getId() == R.id.iv_edite_name_update) {

                if (isConnectingToInternet(context))
                    showprofilename();
                else {
                    Utill.showCenteredToast(context, getString(R.string.str_check_internet_connection));
                }


            } else if (view.getId() == R.id.iv_edite_name) {
                if (isConnectingToInternet(context))
                    hideprofilename();
                else {
                    Utill.showCenteredToast(context, getString(R.string.str_check_internet_connection));
                }

            } else if (view.getId() == R.id.ed_number) {

            } else if (view.getId() == R.id.ci_user) {


                Intent intent = new Intent(context, Full_ImageActivity.class);
                intent.putExtra(Constants.FUILL_IMAGE_PATH, preference.getUserImage());
                startActivity(intent);

            } else if (view.getId() == R.id.tv_edite_mobilenum_update) {

                //     showmobilenumber();
            } else if (view.getId() == R.id.tv_edite_num) {

                //   hidemobilenumbertextview();
            } else if (view.getId() == R.id.btn_logout) {

                loginAlert(context);


            }



        }
        else
            Utill.showCenteredToast(context,getString(R.string.str_check_internet_connection));



    }

    private void image_selected() {




    }

    private void initView() {
        context = UserProfileActivity.this;
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // ci_back = (ImageView) findViewById(R.id.iv_user_profile);
        ivEditeImage = (ImageView) findViewById(R.id.iv_edite_image);
        edName = (EditText) findViewById(R.id.ed_name);
        tvUsername = (TextViewReguler) findViewById(R.id.tv_username);
        preference=new Chatlocalyshareprefrence(context);
        ivUserProfile=(CircleImageView) findViewById(R.id.ci_user);
        user_id=""+preference.getUserId();
        ivEditeName = (ImageView) findViewById(R.id.iv_edite_name);
        ivEditeUpdateName = (ImageView) findViewById(R.id.iv_edite_name_update);

        edNumber = (EditText) findViewById(R.id.ed_number);
        tvNumber = (TextViewReguler) findViewById(R.id.tv_number);


        tvEditeNum = (TextView) findViewById(R.id.tv_edite_num);
        tvEditemobielnum_Update = (TextView) findViewById(R.id.tv_edite_mobilenum_update);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        // set user  data
        ivEditeImage.setOnClickListener(this);

        //setuserData


        setUserData();



    }

    private void setUserData() {
    }

    // name update api  in android

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();


        return super.onOptionsItemSelected(item);

    }

    // user profile update

    public void updateName(String userName) {

        callUserName(user_id,userName,preference.getEncryptKey());


    }

    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            {
                getTakePictureIntent();

            }
            else
                {

                requestMultiplePermissions(CAMERA_REQUEST);
            }
        } else {
            getTakePictureIntent();

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

    public void chooseFromGallery() {
        if (Build.VERSION.SDK_INT >= 23) {


            if (checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                     getImagePickerIntent();

                      } else {
                requestMultiplePermissions(SELECT_PICTURE);
            }
        }
        else
        {
            getImagePickerIntent();

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {

                if (requestCode == SELECT_PICTURE) {

                    Uri selectedImage = data.getData();
                    ivUserProfile.setImageURI(selectedImage);// To display selected image in image view



                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();

                 /*   bitmapUser = BitmapFactory.decodeFile(filePath);
                    ivUserProfile.setImageBitmap(bitmapUser);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapUser.compress(Bitmap.CompressFormat.PNG, 100, stream);
*/
                    callImageUpload(filePath,user_id,"",preference.getEncryptKey());

                    //  Glide.with(context).load(stream.toByteArray()).listener(target).into(iv_profilePic);


                } else if (requestCode == CAMERA_REQUEST) {

                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File savefile = new File(mCurrentPhotoPath);
                    Uri contentUri = Uri.fromFile(savefile);
                    mediaScanIntent.setData(contentUri);
                    this.sendBroadcast(mediaScanIntent);

                    if (savefile.exists()) {
                        callImageUpload(savefile.getAbsolutePath(),user_id,"",preference.getEncryptKey());

                    }

                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Toast.makeText(getApplicationContext(), " " + getResources().getString(R.string.str_cancelled), Toast.LENGTH_LONG).show();
            } else if (resultCode != Activity.RESULT_CANCELED) {
                if (requestCode == CAMERA_REQUEST) {
                    bitmapUser = (Bitmap) data.getExtras().get("data");
                    ivUserProfile.setImageBitmap(bitmapUser);
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

    public Dialog chooserDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        String[] array = {"Take Photo", "Choose from Gallery"};

        builder.setTitle(title).setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    // for full image code

                    takePhoto();

                } else if (i == 1) {
                    chooseFromGallery();
                }

            }
        });

        return builder.create();
    }

    /*************************
     * call image update
     *********************/

    public void callImageUpload(String mediaPath, final String c_user_id, String fullname, final String encrypt) {

        final ProgressDialog pDialogofimaged = ProgressDialog.show(context, "", "Please wait ...", true);
        pDialogofimaged.setCancelable(false);

        //      progressDialog.show();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("c_user_id",Utill.createPartFromString(c_user_id));
        params.put("c_full_name", Utill.createPartFromString(fullname));
        params.put("encrypt_key", Utill.createPartFromString(encrypt));


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

                    Utill.showCenteredToast(context, "" + resultModel.getData().getMessage());
                    getUserProfile(c_user_id,encrypt);

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

    /*userName*/
    public void callUserName(String c_user_id, final String fullname, String encrypt) {

        final ProgressDialog pDialogofimaged = ProgressDialog.show(context, "", "Please wait ...", true);
        pDialogofimaged.setCancelable(false);
        //      progressDialog.show();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("c_user_id",Utill.createPartFromString(c_user_id));
        params.put("c_full_name", Utill.createPartFromString(fullname));
        params.put("encrypt_key", Utill.createPartFromString(encrypt));
        Call<ResultModel> call = null;
            call = apiService.uploadFile(null, null, params);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

              //  pDialogofimaged.dismiss();
                ResultModel resultModel = response.body();
                String res=resultModel.toString();
                if (resultModel != null && resultModel.getData() != null && resultModel.getData().getResultCode().equalsIgnoreCase("1")) {


                     loginByApplozic(pDialogofimaged,fullname);

                    tvUsername.setVisibility(View.VISIBLE);
                    edName.setVisibility(View.GONE);
                    ivEditeName.setVisibility(View.VISIBLE);
                    ivEditeUpdateName.setVisibility(View.GONE);
                    Utill.showCenteredToast(context, "" + getString(R.string.nameSuccessFullyupdateed));
                    preference.setFullName(""+fullname);

                    tvUsername.setText(fullname);
                    edName.setText(fullname);




                } else {
                   pDialogofimaged.dismiss();

                    Utill.showCenteredToast(context, "" + resultModel.getData().getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {


                pDialogofimaged.dismiss();
            }
        });


    }


    /* user Name   */
    public void getUserProfile( String c_user_id, String encrypt) {

      //  final ProgressDialog pDialogofimaged = ProgressDialog.show(context, "", "Please wait ...", true);
      //  pDialogofimaged.setCancelable(false);
        //      progressDialog.show();
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id",/*Utill.createPartFromString(*/c_user_id/*)*/);
       params.put("encrypt_key", /*Utill.createPartFromString(*/encrypt/*)*/);
        Call<UserProfileModel> call = null;
        call = apiService.getUserProfileById(params);
        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

         //       pDialogofimaged.dismiss();
                UserProfileModel resultModel = response.body();
                if (resultModel != null && resultModel.getGetUserProfile() != null && resultModel.getGetUserProfile().getResultCode().equalsIgnoreCase("1"))
                {

                if(resultModel.getGetUserProfile().getCFullName()!=null && ! resultModel.getGetUserProfile().getCFullName().equals(""))
                {
                    preference.setFullName(resultModel.getGetUserProfile().getCFullName());
                    tvUsername.setText(resultModel.getGetUserProfile().getCFullName());
                    edName.setText(resultModel.getGetUserProfile().getCFullName());
                }
                if(resultModel.getGetUserProfile().getCProfileImage()!=null && !resultModel.getGetUserProfile().getCProfileImage().equals(""))
                {

                    preference.setImage(""+resultModel.getGetUserProfile().getCProfileImage());
                    Utill.imageshow(context,ivUserProfile,resultModel.getGetUserProfile().getCProfileImage());

                }


                }
            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {


            //    pDialogofimaged.dismiss();
            }
        });


    }

    public void loginAlert(final Context context) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
     //   builder.setTitle("Logout!");
        builder.setMessage(getString(R.string.logout_dialog_title));
        builder.setPositiveButton("logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                preference.logout();
                UserLogoutTask.TaskListener userLogoutTaskListener = new UserLogoutTask.TaskListener() {
                    @Override
                    public void onSuccess(Context context) {
                        //Logout success
                    }
                    @Override
                    public void onFailure(Exception exception) {
                        //Logout failure
                    }
                };
                UserLogoutTask userLogoutTask = new UserLogoutTask(userLogoutTaskListener, context);
                userLogoutTask.execute((Void) null);

                Intent intent = new Intent(context, SplashScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();


            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertdialog.dismiss();
            }
        });
        alertdialog = builder.create();
        alertdialog.getWindow().setBackgroundDrawableResource(R.drawable.roundbox);
        alertdialog.setCancelable(true);
        alertdialog.show();
    }


    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {


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

/******************************************************   business user profile   ***********************************/

public void getBusinessUserProfile( String c_user_id,String b_user_id ,String encrypt) {


    final ProgressDialog pDialogofimaged = ProgressDialog.show(context, "", "Please wait ...", true);
    pDialogofimaged.setCancelable(false);
    //      progressDialog.show();
    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    HashMap<String, String> params = new HashMap<>();
    params.put("c_user_id", c_user_id);
    params.put("b_user_id", b_user_id.replace("0B", ""));
    params.put("encrypt_key", encrypt);
    Call<BusinessMemberProfile> call = null;
    call = apiService.getBusinessProfile(params);
    call.enqueue(new Callback<BusinessMemberProfile>() {
        @Override
        public void onResponse(Call<BusinessMemberProfile> call, Response<BusinessMemberProfile> response) {

            pDialogofimaged.dismiss();
            BusinessMemberProfile resultModel = response.body();
            if (resultModel != null && resultModel.getData() != null && resultModel.getData().getResultCode().equalsIgnoreCase("1")) {

                if (resultModel.getData().getBFullName() != null && !resultModel.getData().getBMobileNumber().equals("")) {


                    tvUsername.setText("" + resultModel.getData().getBFullName());
                    tvNumber.setText("" + resultModel.getData().getBMobileNumber());
                    Utill.customSizeimageshow(context, ivUserProfile, resultModel.getData().getBProfileImage(),width,height);




                }


            }
        }

        @Override
        public void onFailure(Call<BusinessMemberProfile> call, Throwable t) {


            pDialogofimaged.dismiss();
        }
    });
}

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "ChatLocaly" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void getTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this, Utils.getMetaDataValue(this, MobiComKitConstants.PACKAGE_NAME) + ".provider", photoFile);
                // Uri photoURI = FileProvider.getUriForFile(this, "${applicationId}.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }


    public void  getImagePickerIntent() {
/*
        Intent intent = new Intent();
        intent.setType("image");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
   */
   Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_PICTURE);

    }






    private void loginByApplozic( final ProgressDialog pDialog, String name) {

        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                //After successful registration with Applozic server the callback will come here
                Log.d("applozic login","login success");

                if(registrationResponse!=null) {


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

        User user = new User();
        user.setUserId(Utill.getApplozicUserId(preference.getUserId()));
        String s[]=name.split(" ");

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

}



