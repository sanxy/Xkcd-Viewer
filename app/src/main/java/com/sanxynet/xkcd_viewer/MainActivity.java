package com.sanxynet.xkcd_viewer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MIN_ID = 1;
    public static final int MAX_ID = 2299;
    int currentId = MIN_ID;

    ProgressBar progressBar;
    ImageView comicImage;
    TextView tvComicTitle;
    Button btnFirst, btnLast, btnPrev, btnNext, btnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        comicImage = findViewById(R.id.comic_image);
        tvComicTitle = findViewById(R.id.comic_title);
        btnFirst = findViewById(R.id.btn_go_first);
        btnLast = findViewById(R.id.btn_go_last);
        btnPrev = findViewById(R.id.btn_go_prev);
        btnNext = findViewById(R.id.btn_go_next);
        btnRandom = findViewById(R.id.btn_go_random);

        progressBar.setVisibility(View.VISIBLE);
        getComicById(currentId);

        btnFirst.setOnClickListener(this);
        btnLast.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnRandom.setOnClickListener(this);
    }

    private void getComicById(int id) {
        Call<ResponseModel> request = ApiConfig.getService().getComicById(id);
        request.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel result = response.body();

                    //load image to image view from url
                    Picasso.get()
                            .load(result.imageUrl)
                            .placeholder(R.drawable.ic_sync)
                            .error(R.drawable.ic_error)
                            .into(comicImage);

                    tvComicTitle.setText(result.comicTitle);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get comic", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int button = v.getId();

        switch (button) {
            case R.id.btn_go_first:
                currentId = MIN_ID;

                getComicById(MIN_ID);
                break;
            case R.id.btn_go_last:
                currentId = MAX_ID;

                getComicById(MAX_ID);
                break;
            case R.id.btn_go_prev:
                if (currentId > MIN_ID) {
                    currentId--;

                    getComicById(currentId);
                } else {
                    Toast.makeText(this, "Minimum page reached", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.btn_go_next:
                if (currentId < MAX_ID) {
                    currentId++;

                    getComicById(currentId);
                } else {
                    Toast.makeText(this, "Maximum page reached", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.btn_go_random:
                Random rand = new Random();
                int randomId = rand.nextInt(MAX_ID) + 1;

                currentId = randomId;

                getComicById(randomId);
            default:
                break;
        }
        progressBar.setVisibility(View.VISIBLE);
    }
}