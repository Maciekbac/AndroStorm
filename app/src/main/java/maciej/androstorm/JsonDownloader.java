package maciej.androstorm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonDownloader {
    int city_id;
    JsonDownloader(int id){
        city_id=id;
    }

    public String getJson(){
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL("http://antistorm.eu/webservice.php?id="+city_id);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                return null;
            }

            String json = buffer.toString();
            return json;
        } catch (IOException e) {
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
