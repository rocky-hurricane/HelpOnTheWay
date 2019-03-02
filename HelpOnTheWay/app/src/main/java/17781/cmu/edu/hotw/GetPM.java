package yuqis.cmu.edu.aircasting;
import java.io.IOException;
import java.net.URL;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Given a city name, search for the pm2.5 air quality information from the cloud application server.
 */
public class GetPM {
    PM particalMatter = null;

    /**
     * request pm2.5 info by executing async search and callback
     *
     * @param searchTerm
     * @param particularMatter instance of the PM class
     */
    public void search(String searchTerm, PM particularMatter) {
        this.particalMatter = particularMatter;
        new AsyncPMRequest().execute(searchTerm);
    }

    /*
     * request for the pm2.5 info for the city in the background
     */
    private class AsyncPMRequest extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return search(urls[0]);
        }

        protected void onPostExecute(String pm) {
            particalMatter.displayInfo(pm);
        }


        /**
         * search from the cloud web application for the pm2.5 information for the specified city and return the information as a text view
         *
         * @param cityName
         * @return
         */
        private String search(String cityName) {
            String responseInfo = read("https://docker-zbrytnoeos.now.sh/AirCasting/" + cityName);
            String parsedResp = parse(responseInfo);
            return parsedResp;
        }

        private String parse(String json) {
            JSONParser parser = new JSONParser();

            Object resultObj = null;
            String r;
            try {
                resultObj = parser.parse(json);

                JSONObject result = (JSONObject) resultObj;
                // change representation of the response form json object to string
                r = (String) result.get("response");
            } catch (ParseException e) {
                e.printStackTrace();
                r = "Ooops.. something is wrong";
            }

            return r;
        }


        /**
         * try to read pm2.5 info and return error message or the read result
         *
         * @param name
         * @return
         */
        public String read(String name) {
            Result r = new Result();
            int status = 0;
            if ((status = doGet(name, r)) != 200) {
                return "Error from server " + status;
            }
            return r.getValue();
        }

        public int doGet(String name, Result r) {

            // GET operation to pass the city name to URL
            r.setValue("");
            String response = "";
            HttpURLConnection conn;
            int status = 0;

            try {

                URL url = new URL(name);

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "text/plain");

                status = conn.getResponseCode();

                if (status != 200) {
                    // not using msg
                    String msg = conn.getResponseMessage();
                    return status;
                }
                String output = "";
                // read the response from the server for pm2.5 info
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                while ((output = br.readLine()) != null) {
                    response += output;

                }

                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            r.setValue(response);
            // return the HTTP status
            return status;
        }

    }


    /**
     * holds the request result from the server on cloud for the GET method
     * REFERENCE: REST lab
     */
    class Result {

        String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}