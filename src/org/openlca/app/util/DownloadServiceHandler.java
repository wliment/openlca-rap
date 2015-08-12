package org.openlca.app.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServiceHandler;

public class DownloadServiceHandler implements ServiceHandler
{


@Override
public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	// Which file to download?
    String fileName = request.getParameter( "filename" );
    // Get the file content
    
    byte[] download = MyDataStore.getByteArrayData(fileName);  
    response.setContentType( "application/octet-stream" );
    response.setContentLength( download.length );
    String contentDisposition = "attachment; filename=\"" + fileName + "\"";
    response.setHeader( "Content-Disposition", contentDisposition );
    response.getOutputStream().write( download );
}
}