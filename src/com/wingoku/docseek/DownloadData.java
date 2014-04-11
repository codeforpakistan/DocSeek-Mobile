package com.wingoku.docseek;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
 
public class DownloadData {
 
	 
	BufferedReader bufReader = null;
	
	InputStream is;
	InputStreamReader reader;
	Context con;
	
	public DownloadData(URI address, Context context) throws ClientProtocolException, IOException
	{
		con = context;	
		
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(); // request data using http get protocol
		
		request.setURI(address);
		
		HttpResponse response = client.execute(request);
			
		HttpEntity entity = response.getEntity();
		
		is = entity.getContent();
		
		reader = new InputStreamReader(is, "iso-8859-1");
		
		bufReader = new BufferedReader(reader,8);
		


	}
	
	
	public String divideStrings() throws IOException
	{
		StringBuilder sb = new StringBuilder(); // efficient & faster string processing compared to String
		
		String line = ""; 
		
		
		while((line = bufReader.readLine() ) != null)
		{
			sb.append(line);
		}
		
		reader.close();
		is.close();
		
		if(sb == null)
			Toast.makeText(con, "unable to get response", Toast.LENGTH_SHORT).show();
		
		return sb.toString();
	}
}
