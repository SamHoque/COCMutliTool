package lynx.coc.multitool;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

/**
 * Self Explanatory
 */
public class url_LYNX extends AsyncTask<String, Void, String> {
    public static final String ERROR_MESSAGE = "Something went wrong - try again.";
    private static final String API_BASE = "https://api.clashofclans.com/v1/";
    private static final String token = "API_TOKEN_HERE";

    protected String doInBackground(String[] strArr) {
        try {
            String url = "https://api.clashofclans.com/v1/players/" + URLEncoder.encode(strArr[0], "UTF-8");
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + token);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine).append("\n");
                } else {
                    bufferedReader.close();
                    return stringBuilder.toString().trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong - try again.";
        }
    }

    public static String getContent_LYNX(String str) {
        try {
            return new url_LYNX().execute(new String[]{str}).get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong - try again.";
        }
    }
}

