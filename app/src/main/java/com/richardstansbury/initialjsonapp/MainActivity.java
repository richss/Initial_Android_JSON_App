package com.richardstansbury.initialjsonapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


public class MainActivity extends Activity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textfield);

        new JSONParser().execute();
    }

    //Reference: http://developer.android.com/guide/components/processes-and-threads.html
    private class JSONParser extends AsyncTask<String, Void, String> {



        /**
         * Performs the http post.
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {

            String result="";

            try {

                URL url = new URL("http://10.0.2.2/php_projects/simple_json_test/simple_json.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setChunkedStreamingMode(0);
                connection.setRequestMethod("POST");


                OutputStream out = new BufferedOutputStream(connection.getOutputStream());

                //Perform Post
                String outStr = "ID=12345";
                byte [] outBytes = outStr.getBytes(Charset.forName("UTF-8"));
                out.write(outBytes,0,outBytes.length);
                out.flush();
                out.close();

                InputStream in = new BufferedInputStream(connection.getInputStream());

                //Read Result
                int val;
                while ((val=in.read()) > 0) {
                    result += (char) val;
                }
                in.close();

            } catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            Log.i("Result", "--"+result+"--");
        }

    }
}

