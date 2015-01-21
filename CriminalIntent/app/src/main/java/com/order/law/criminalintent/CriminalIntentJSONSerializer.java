package com.order.law.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by jhjh550 on 2015-01-21.
 */
public class CriminalIntentJSONSerializer {
    private Context mContext;
    private String mFilename;

    public CriminalIntentJSONSerializer(Context context, String filename){
        mContext = context;
        mFilename = filename;
    }

    public void saveCrimes(ArrayList<Crime> crimes)
        throws JSONException, IOException{
        //JSON 객체가 저장되는 배열을 생성한다.
        JSONArray array = new JSONArray();
        for(Crime c : crimes){
            array.put(c.toJSON());
        }

        // 파일을 디스크에 쓴다.
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if(writer != null)
                writer.close();
        }
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException{
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try{
            // 파일을 열고 데이터를 읽어서 StringBuilder 객체로 만든다.
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null ){
                jsonString.append(line);
            }

            // JSONTokener 객체를 사용해서 JSON 객체를 파싱한다.
            JSONArray array =
                    (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // JSONObject 가 저장된 JSONArray 로 부터 Crime 객체가 저장되는
            // ArrayList 를 생성한다.
            for(int i=0; i<array.length(); i++){
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null)
                reader.close();
        }

        return crimes;
    }
}
























