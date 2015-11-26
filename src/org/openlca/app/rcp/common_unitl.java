package org.openlca.app.rcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ResourceManager;

public class common_unitl {

	public common_unitl() {
		// TODO Auto-generated constructor stub
	}
//	   ClassLoader clo=Thread.currentThread().getContextClassLoader();


	 public static String getImageURL(String resourceName) throws IOException {
		 InputStream inputStream = null ;
		   ResourceManager resourceManager = RWT.getResourceManager();
		   ClassLoader clo=Thread.currentThread().getContextClassLoader();

		   if( !resourceManager.isRegistered( resourceName ) ) {
			   try {
			    inputStream = clo.getResourceAsStream( resourceName );

			    if( inputStream == null ) {
			      throw new RuntimeException( "Resource not found" );
			    }
			    
			      resourceManager.register( resourceName, inputStream );
			    }
			  
			    finally {
			      inputStream.close();
			    }
			  }
		   return resourceManager.getLocation(resourceName);
		 }

	 public static String getTxtContent(String filename) throws IOException{
		 
		 InputStream inputStream = null ;
		   ResourceManager resourceManager = RWT.getResourceManager();
		   ClassLoader clo=Thread.currentThread().getContextClassLoader();

		   if( !resourceManager.isRegistered( filename ) ) {
			   try {
			    inputStream = clo.getResourceAsStream( filename );
			    

			    if( inputStream == null ) {
			      throw new RuntimeException( "Resource not found" );
			    }
			    
			      resourceManager.register( filename, inputStream );
			    }
			  
			    finally {
			    
			    }
			  }
		   String filecontent =  IOUtils.toString(resourceManager.getRegisteredContent(filename), "UTF-8");

		   return filecontent;
		 
	 }
	
 public static String getpasswd(String filename) throws IOException{	 
		   String filecontent="";
		   ClassLoader clo=Thread.currentThread().getContextClassLoader();		 
		   InputStream inputStream = clo.getResourceAsStream( filename );
		   filecontent =  IOUtils.toString(inputStream, "UTF-8");

		   return filecontent;
		 
	 }
	public static void main(String[] args) {
			// TODO Auto-generated method stub
			try {
				System.out.println(common_unitl.getpasswd("resources/openlca_user.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
