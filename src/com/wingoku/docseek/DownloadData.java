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
		//Toast.makeText(context, "inside reader start", Toast.LENGTH_SHORT).show();	
		
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(); // HttpGet is used to read data from internet
		
		request.setURI(address);
		
		System.out.println("address is "+ address);
		
		HttpResponse response = client.execute(request);
		
			
		HttpEntity entity = response.getEntity();
		
		is = entity.getContent();
		
		reader = new InputStreamReader(is, "iso-8859-1");
		
		bufReader = new BufferedReader(reader,8);
	
	}
	
	
	public String divideStrings() throws IOException
	{
		StringBuilder sb = new StringBuilder(); // it is used for applying different stuff on string like concatenation. We can also concatenate using + sign but 
		// string builder does it more efficiently and faster
		
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
