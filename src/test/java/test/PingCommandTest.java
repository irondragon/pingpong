package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by messi on 19.06.2015.
 */

public class PingCommandTest {

    public final static String url = "http://localhost/handler";

    private String readStreamToString(InputStream in, String encoding)
            throws IOException {
        StringBuffer b = new StringBuffer();
        InputStreamReader r = new InputStreamReader(in, encoding);
        int c;
        while ((c = r.read()) != -1) {
            b.append((char) c);
        }
        r.close();
        return b.toString();
    }

    public void get(String query) throws IOException {
        URLConnection conn = new URL(url + "?" + query).openConnection();
        String html = readStreamToString(conn.getInputStream(), "UTF-8");
        System.out.println("URL:" + url + " \nHtml:\n" + html);
    }

    public void post(String query, int num) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("type", "post");
        Map<String, List<String>> request = conn.getRequestProperties();
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
        out.write(query.toString());
        out.write("\r\n");
        out.flush();
        out.close();
        String html = readStreamToString(conn.getInputStream(), "UTF-8");
        Map<String, List<String>> response = conn.getHeaderFields();
        System.out.println(num + " URL: " + url + "\nHtml:\n" + html);
    }

    public static void main(String[] args) {
        final PingCommandTest pingCommandTest = new PingCommandTest();
        for (int i = 99; i < 100; ++i) {
            final int num = i+1;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String query = "{\"commandType\":\"PING\", \"sessionId\":\"8\"}";
                    try {
                        pingCommandTest.post(query, num);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

}
