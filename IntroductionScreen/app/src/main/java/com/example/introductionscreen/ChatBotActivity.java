package com.example.introductionscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatBotActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText message;
    ImageView send;
    List<MessageModel>list;
    MessageAdapter adapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat_bot);

        recyclerView = findViewById(R.id.recyclerView);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapter(list);
        recyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String question = message.getText().toString();

                if (question.isEmpty()) {

                    Toast.makeText(ChatBotActivity.this, "Write Something", Toast.LENGTH_SHORT).show();
                    
                }

                else {

                    addToChat(question,MessageModel.SENT_BY_ME);
                    message.setText("");

                    callAPI(question);

                }

            }
        });

    }

    private void callAPI(String question) {

        list.add(new MessageModel("Typing...",MessageModel.SENT_BY_BOT));

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("model","text-davinci-003");
            jsonObject.put("prompt",question);
            jsonObject.put("max_tokens",5000);
            jsonObject.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();
        //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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                addResponse("Failed to load." + e.getMessage());

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()){

                    JSONObject jsonObject1 = null;

                    try {
                        jsonObject1 = new JSONObject(response.body().toString());
                        JSONArray jsonArray = jsonObject1.getJSONArray("choices");

                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

                else {

                    addResponse("Failed to load" + response.body().toString());

                }

            }
        });

    }

    private void addResponse(String s) {

        list.remove(list.size()-1);
        addToChat(s,MessageModel.SENT_BY_BOT);

    }

    private void addToChat(String question, String sentByMe) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                list.add(new MessageModel(question,sentByMe));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());

            }
        });

    }
}