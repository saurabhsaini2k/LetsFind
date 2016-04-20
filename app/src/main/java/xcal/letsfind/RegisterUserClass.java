package xcal.letsfind;
//  import org.apache.http.HttpException;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RegisterUserClass {

    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(getPostDataString(postDataParams));


            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.i("ababababa","abababa");
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.i("11111111111222222222","abababa");

                InputStream is  = conn.getInputStream();
                if(is==null){
                    Log.v("hdvsdhfs","Input stream is null");
                }else{
                    Log.v("hjsagdjsa","OKKKKK");
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Log.i("111111111112222222223","hhhh");
                StringBuilder sb = new StringBuilder();
                Log.i("111111111112222222224","hhhh");
                String line = null;
                Log.i("111111111112222222223","hhhh");
                while ((line = br.readLine()) != null) {
                    Log.i("ababababa","abababa");
                    sb.append(line + "\n");
                }
                //response = br.readLine();
                Log.e("TTTTTTTTTTTTT",sb.toString());
                response=sb.toString();
            }
            else {
                Log.i("askjdskadjfsakdjf","abababa");
                response="FAILED";
                Log.i("AAAAAAAAAABBBBBBBBBBB: ",response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet())
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.e("Tagggggggggg BABABA",result.toString());
        return result.toString();
    }
}
