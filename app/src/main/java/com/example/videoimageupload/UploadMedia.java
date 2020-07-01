package com.example.videoimageupload;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.videoimageupload.models.PostModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

public class UploadMedia extends AppCompatActivity {

    private ImageView previewImage,importButton;
    private VideoView previewVideo;

    private static int IMAGE_REQUEST_CODE= 1;
    private static int VIDEO_REQUEST_CODE = 2;
    private static int STORAGE_PERMISSION_CODE = 5;

    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_media);

        previewImage = findViewById(R.id.previewImage);
        previewVideo = findViewById(R.id.previewVideo);
        importButton = findViewById(R.id.importButton);

        backPressed = false;
    }

    public void showMediaImportDialog(final View view) {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.media_select);
        ImageButton image = d.findViewById(R.id.imageSelect);
        ImageButton video = d.findViewById(R.id.videoSelect);

        d.show();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    importButton.setImageResource(R.drawable.image);
                    d.cancel();
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, IMAGE_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    importButton.setImageResource(R.drawable.video);
                    d.cancel();
                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Video"),VIDEO_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getStoragePermission();
    }

    private void getStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


     /*   try {
            absoluteFilePath = getPath(data.getData());
        } catch (Exception e) {
            //if empty will throw
            e.printStackTrace();
        }*/

        if (requestCode == IMAGE_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                try {
                    setImageSelected();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this).getContentResolver(), Objects.requireNonNull(data).getData());
                    previewImage.setImageBitmap(bitmap);
                    Toast.makeText(this, "image : "+ data.getData() + "with uri "+getPath(data.getData()), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                System.out.println("Something went wrong i.e Image");
            }

        } else if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                   // Uri selectedVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                    setVideoSelected(data.getData());


                   // Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+getVideoPath(selectedVideoUri));
//                    Uri video = FileProvider.getUriForFile(this, this.getPackageName(), new File(getVideoPath(selectedVideoUri)));

                    //setVideoSelected(video);

                   // setVideoSelected(Uri.parse(getVideoPath(selectedVideoUri)));
                  //  Toast.makeText(this, "video : "+ data.getData() + " with uri : "+getVideoPath(selectedVideoUri), Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
            System.out.println("Something went wrong i.e Video");
            }
        } else {
            Toast.makeText(this, "Unknown action", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

   /* public String getVideoPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return null;
        }
    }*/

    private String getPath(Uri uri){
        //todo fix getting image intent

        Cursor cursor = Objects.requireNonNull(this).getContentResolver().query(uri,null,null,null,null);
        assert cursor != null;
        cursor.moveToFirst();
        String document_id = cursor.getString(0);

        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID + "= ?",new String[]{document_id},null);

        assert cursor != null;
        cursor.moveToFirst();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    /*public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }*/


    @Override
    public void onBackPressed() {
        if (!backPressed) {
            backPressed = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    private void setVideoSelected(final Uri uri) {
        previewVideo.setVisibility(View.VISIBLE);
        previewImage.setVisibility(View.GONE);
        previewVideo.setVideoURI(uri);
        previewVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                previewVideo.start();
                previewVideo.requestFocus();

                previewVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (previewVideo.isPlaying()) {
                            previewVideo.pause();
                        } else {
                            if (previewVideo.getCurrentPosition() == previewVideo.getDuration()) {
                                previewVideo.seekTo(0);
                            }
                            previewVideo.start();

                        }
                    }
                });
            }
        });

    }

    private void setImageSelected(){
        previewImage.setVisibility(View.VISIBLE);
        previewVideo.setVisibility(View.GONE);
    }

    private void constructPostModel() {

    }

    private void uploadImage(PostModel postModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String date = Calendar.getInstance().getTime().toString();  //date that will change after 3 days by api

        db.collection(PostModel.POSTS).document(date).collection(postModel.getPostMetadata().getPostedBy()+"/"+postModel.getPostMetadata().getPostId()).add(postModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("Image Save successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Image Save successful");
            }
        });
    }

    private void uploadVideo(PostModel postModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String date = Calendar.getInstance().getTime().toString();  //date that will change after 3 days by api

        db.collection(PostModel.POSTS).document(date).collection(postModel.getPostMetadata().getPostedBy()+"/"+postModel.getPostMetadata().getPostId()).add(postModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("Video Save successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Video Save failure");
            }
        });
    }
}