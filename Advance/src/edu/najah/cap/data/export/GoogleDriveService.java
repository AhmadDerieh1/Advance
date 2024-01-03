package edu.najah.cap.data.export;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveService {

    private static final String APPLICATION_NAME = "AdvanceUserDataFeature";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "Advance/src/edu/najah/cap/resources/token";

    private static final String CREDENTIALS_FILE_PATH = "Advance/src/edu/najah/cap/resources/client_secret.json";

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    
    public GoogleDriveService() {
        // Constructor
    }
    
    private static GoogleCredentials getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
    
        GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                .createScoped(SCOPES);
        credentials.refreshIfExpired();
        return credentials;
    }
    
    
    public Drive getDriveService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredentials credential = getCredentials(HTTP_TRANSPORT);
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpCredentialsAdapter(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
   
    //uploadFile (): Drive obj  to create a new file on Google Drive.
    public void uploadFile(String filePath) throws IOException, GeneralSecurityException {
        System.out.println("dakhaal uploadFile");
         try {
        Drive service = getDriveService();
        System.out.println("dakhaal uploadFile _3ba service ");
        java.io.File fileToUpload = new java.io.File(filePath);
        File fileMetadata = new File();
        fileMetadata.setName(fileToUpload.getName());
        FileContent mediaContent = new FileContent("application/zip", fileToUpload);
        File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("File ID: " + file.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    
}
