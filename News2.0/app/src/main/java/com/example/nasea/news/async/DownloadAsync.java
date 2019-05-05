package com.example.nasea.news.async;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadAsync extends AsyncTask<String, Integer, String> {
    private DownloadCallback callback;

    public DownloadAsync(DownloadCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.e("doInBackground","doInBackground" );
        String link = strings[0];
        String path = strings[1];
        try {
                String dir = Environment.getExternalStorageDirectory()+File.separator+"Saved";
                Log.e("onClick","dir "+dir);
                //create folder
                File folder = new File(dir); //folder name
                folder.mkdirs();

            //create file
            File f = new File(dir, path);

//            File f = new File(path);
//            Log.e("File","File path: "+path);
//            f.getParentFile().mkdirs();
//            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            URL url = new URL(link);
            Log.e("doInBackground","url "+url);
            URLConnection connection = url.openConnection();
            Log.e("doInBackground","url "+connection.toString());
            InputStream in = connection.getInputStream();
            int total = connection.getContentLength();
            int totalSave = 0;
            byte[] b = new byte[1024];
            int count = in.read(b);
            while (count != -1){
                totalSave += count;
                float percent = (float) totalSave / total;
                publishProgress((int) (percent* 100));
                out.write(b,0, count);
                count = in.read(b);
            }
            in.close();
            out.close();
            return f.getPath();
        }catch (Exception ex){
            ex.printStackTrace();
            Log.e("Exception","Exception: "+ex);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        callback.updatePercent(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.downloadFinish(s);
    }

    public interface DownloadCallback{
        void updatePercent(int percent);
        void downloadFinish(String path);
    }
}
