package com.example.videoimageupload;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.bumptech.glide.Glide;
import com.example.videoimageupload.models.PostModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.example.videoimageupload.RealFilePath.getPath;

public class UploadMedia extends AppCompatActivity {

    private ImageView previewImage, importButton;
    private VideoView previewVideo;

    private static int IMAGE_REQUEST_CODE = 1;
    private static int VIDEO_REQUEST_CODE = 2;
    private static int STORAGE_PERMISSION_CODE = 5;
    private static String NEW_VIDEOS = "New_videos";
    private static String NEW_PICS ="New_pictures";
    private boolean backPressed;
    private StorageReference activeUploadReference;
    private String fileUri = null;
    private String mediaType = "";
    private static String VIDEO_TYPE = "Video";
    public static String IMAGE_TYPE = "Image";
    public static String linkToBeStored= "";
    private String bucketLink = "gs://videoupload-7deac.appspot.com";

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
                    startActivityForResult(Intent.createChooser(intent, "Select Video"), VIDEO_REQUEST_CODE);
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

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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

        String absoluteFilePath = "";

     /*   try {
            absoluteFilePath = getPath(data.getData());
        } catch (Exception e) {
            //if empty will throw
            e.printStackTrace();
        }*/

        if (requestCode == IMAGE_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                try {
                    absoluteFilePath = getPath(this,data.getData());
                    mediaType = IMAGE_TYPE;
                    Toast.makeText(this, mediaType+" : "+ absoluteFilePath, Toast.LENGTH_SHORT).show();
                    Glide.with(this).load(absoluteFilePath).into(previewImage);
                    fileUri = absoluteFilePath;
                    setImageSelected();
                } catch (Exception e) {
                    //if empty will throw
                    e.printStackTrace();
                }
                //setImageSelected();
                // Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this).getContentResolver(), Objects.requireNonNull(data).getData());
                // previewImage.setImageBitmap(bitmap);
                // Toast.makeText(this, "image : " + data.getData() + "with uri " + getPath(data.getData()), Toast.LENGTH_LONG).show();
            } else {
                System.out.println("Something went wrong i.e Image");
            }

        } else if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // Uri selectedVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    mediaType = VIDEO_TYPE;
                    Toast.makeText(this, mediaType+" : "+ getPath(this,data.getData()), Toast.LENGTH_SHORT).show();
                    setVideoSelected(data.getData());
                    fileUri = getPath(this,data.getData());
                    // Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+getVideoPath(selectedVideoUri));
//                    Uri video = FileProvider.getUriForFile(this, this.getPackageName(), new File(getVideoPath(selectedVideoUri)));

                    //setVideoSelected(video);

                    // setVideoSelected(Uri.parse(getVideoPath(selectedVideoUri)));
                    //  Toast.makeText(this, "video : "+ data.getData() + " with uri : "+getVideoPath(selectedVideoUri), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
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

    private void setImageSelected() {
        previewImage.setVisibility(View.VISIBLE);
        previewVideo.setVisibility(View.GONE);
    }

    private void constructPostModel() {

    }

    private void uploadImageToFirestore(PostModel postModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String date = Calendar.getInstance().getTime().toString();  //date that will change after 3 days by api

        db.collection(PostModel.POSTS).document(date).collection(postModel.getPostMetadata().getPostedBy() + "/" + postModel.getPostMetadata().getPostId()).add(postModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    private void uploadVideoToFirestore(PostModel postModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String date = Calendar.getInstance().getTime().toString();  //date that will change after 3 days by api

        db.collection(PostModel.POSTS).document(date).collection(postModel.getPostMetadata().getPostedBy() + "/" + postModel.getPostMetadata().getPostId()).add(postModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    private String uploadVideoToBucketStorage(String path, StorageMetadata storageMetadata) {
        final String[] imageLink = {""};


        // Get a non-default Storage bucket //getRoot() //getParent() //getName() //getBucket() //getPath//
        FirebaseStorage storage = FirebaseStorage.getInstance(); //"gs://video_dump_bucket"
        StorageReference existingVideosReference = storage.getReference();
        StorageReference toStoreReference = storage.getReference().child(NEW_VIDEOS);

        /*  // Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();*/

        Random r = new Random();
        int randomNumber = r.nextInt(100);

        final StorageReference newVideoReference = storage.getReference().child(NEW_VIDEOS).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + " / " + randomNumber);

        Uri file = Uri.fromFile(new File(path));

        Toast.makeText(this, ""+file, Toast.LENGTH_SHORT).show();
        UploadTask uploadTask = newVideoReference.putFile(file, storageMetadata);

        activeUploadReference = Objects.requireNonNull(uploadTask.getSnapshot().getMetadata()).getReference();

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return newVideoReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imageLink[0] = downloadUri.toString();
                    System.out.println("Image uploaded to "+downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("Failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                System.out.println("Upload complete with " + taskSnapshot.getBytesTransferred() + " stored at " + Objects.requireNonNull(taskSnapshot.getMetadata()).getPath() + " with name : " + taskSnapshot.getMetadata().getName());
            }
        });

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        });

        uploadTask.addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        });

        return imageLink[0];

    }

    private String uploadPhotoToBucketStorage(String fileUri, StorageMetadata metadata) {

        final String[] imageLink = {""};

        Uri file = Uri.fromFile(new File(fileUri));
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference toStoreReference = storage.getReference().child(NEW_PICS);

        UploadTask uploadAlert = toStoreReference.putFile(file,metadata);

        uploadAlert.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUrl = Uri.parse(Objects.requireNonNull(task.getResult()).toString()); }
            else
                {System.out.println("Upload Failed, Try again later");}
        });

        Random r = new Random();
        int randomNumber = r.nextInt(100);

        final StorageReference newImageReference = storage.getReference().child(NEW_PICS).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + " / " + randomNumber);

        activeUploadReference = Objects.requireNonNull(uploadAlert.getSnapshot().getMetadata()).getReference();

        Task<Uri> urlTask = uploadAlert.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return newImageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

        // Register observers to listen for when the download is done or if it fails
        uploadAlert.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("Failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                System.out.println("Upload complete with " + taskSnapshot.getBytesTransferred() + " stored at " + Objects.requireNonNull(taskSnapshot.getMetadata()).getPath() + " with name : " + taskSnapshot.getMetadata().getName());
            }
        });

        uploadAlert.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        });

        uploadAlert.addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        });

        toStoreReference.putFile(file).addOnSuccessListener(taskSnapshot -> {
            // Get a URL to the uploaded content
            Uri downloadUrl = Uri.parse(taskSnapshot.getTask().getResult().toString());
        }).addOnFailureListener(exception -> System.out.println("Failed to upload picture"));

        Task<Uri> getDownloadUriTask = uploadAlert.continueWithTask(task -> toStoreReference.getDownloadUrl());

        getDownloadUriTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                //  progressBar.setVisibility(View.GONE);
                if (downloadUri != null) {
                    imageLink[0] = downloadUri.toString();
                    System.out.println("Image uploaded to "+downloadUri.toString());
                }

            }
        });

        /*  // Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();*/




        return imageLink[0];
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // If there's an upload in progress, save the reference so you can query it later
        if (activeUploadReference != null) {
            outState.putString("reference", activeUploadReference.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // If there was an upload in progress, get its reference and create a new StorageReference
        final String stringRef = savedInstanceState.getString("reference");
        if (stringRef == null) {
            return;
        }

        activeUploadReference = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);

        // Find all UploadTasks under this StorageReference (in this example, there should be one)
        List<UploadTask> tasks = activeUploadReference.getActiveUploadTasks();
        if (tasks.size() > 0) {
            // Get the task monitoring the upload
            UploadTask task = tasks.get(0);

            // Add new listeners to the task using an Activity scope
            task.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot state) {
                    // Success!
                    // ...
                    System.out.println("Success upload task");
                }
            });
        }
    }

    private void pauseUpload(UploadTask uploadTask) {
        // Pause the upload
        uploadTask.pause();
    }

    private void resumeUpload(UploadTask uploadTask) {
        // Resume the upload
        uploadTask.resume();
    }

    private void cancelUpload(UploadTask uploadTask) {
        // Cancel the upload
        uploadTask.cancel();
    }


    private void uploadMedia(String type) {
        if (type.equals(VIDEO_TYPE)) {
            if (fileUri != null) {
                StorageMetadata metadata = new StorageMetadata.Builder().setContentType("video/jpg").build();
                System.out.println(uploadVideoToBucketStorage(fileUri,metadata));
            } else {
                Toast.makeText(this, "Pick a video", Toast.LENGTH_SHORT).show();
            }
        } else if (type.equals(IMAGE_TYPE)) {
            if (fileUri != null) {
                StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpg").build();
                System.out.println(uploadPhotoToBucketStorage(fileUri,metadata));
            } else {
                Toast.makeText(this, "Pick an Image", Toast.LENGTH_SHORT).show();
            }
        } else {
            System.out.println("Unknown format");
        }
    }

    public void pushVideo(View view) {
        uploadMedia(mediaType);
    }



}