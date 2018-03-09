package sdm.ifspsaocarlos.edu.br.asyncwssdm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private final String ARQUIVO_IMAGEM = 'imagem.png';
    private final String URL_BASE = 'http://www.nobile.com/';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void acessarWs(View view) {
        if (view.getId() == R.id.bt_acessar_ws) {
            imageSearch(URL_BASE + ARQUIVO_IMAGEM);
        }
    }

    private void imageSearch(String url) {
        AsyncTask<String, Void, Bitmap> buscaImagemAt = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                String url = strings[0];

                try {
                    HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        return BitmapFactory.decodeStream(inputStream);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                ImageView imageView = findViewById(R.id.iv_imagem);
                imageView.setImageBitmap(bitmap);
            }
        };

        buscaImagemAt.execute(url);
    }
}
