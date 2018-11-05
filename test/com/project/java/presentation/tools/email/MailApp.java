package com.project.java.presentation.tools.email;

import java.io.*;
import java.util.*;
import org.apache.commons.mail.*;

/** E-Mail mit Anhang versenden */
public class MailApp
{
    private static Map<String,String> mimeContentTypesMap = null;

    /** Nur zum Testen */
    public static void main( String[] args ) throws IOException, EmailException
    {
        String mailserver      = "smtp.strato.de"; // SMTP-Mailserver-Host-Adresse
        String username        = "info@liquendi.com"; // Mailserver-Benutzername
        String password        = "jBdVX22rMUSx"; // Mailserver-Passwort
        String absender        = "info@liquendi.de"; // Absender-Email-Adresse
        String empfaenger      = "digl@gmx.de"; // Empfaenger-Email-Adresse
        String betreff         = "Dies ist ein Test"; // '\u20AC' = Euro
        String text            = "Hello World2";   // '\u20AC' = Euro
        String textCharset     = "UTF-8";
        String anhangDateiName = ( args != null && args.length > 0 ) ? args[0] : "C:\\Users\\john\\Documents\\project\\Presentation\\test\\email\\Liquendi.pdf"; // Anhangdatei

        System.out.println( sendeEmailMitAnhang( mailserver, username, password, absender, empfaenger,
                textCharset, betreff, text, anhangDateiName ) );
    }

    /** E-Mail mit Datei als Anhang versenden */
    public static String sendeEmailMitAnhang(
            String mailserver, String username, String password, String absender, String empfaenger,
            String textCharset, String betreff, String text, String anhangDateiName ) throws IOException, EmailException
    {
        String anhangContentType = getMimeContentTypeInclCharset( anhangDateiName );
        InputStream anhangInputStream = new FileInputStream( anhangDateiName );
        try {
            return sendeEmailMitAnhang( mailserver, username, password, absender, empfaenger, textCharset, betreff, text,
                    anhangContentType, anhangInputStream, anhangDateiName, anhangDateiName );
        } finally {
            anhangInputStream.close();
        }
    }

    /** E-Mail mit Anhang aus Stream mit Apache Commons Email versenden
     (http://commons.apache.org/proper/commons-email/apidocs/index.html) */
    public static String sendeEmailMitAnhang(
            String mailserver, String username, String password, String absender, String empfaenger,
            String textCharset, String betreff, String text,
            String anhangContentType, InputStream anhangInputStream, String anhangDateiName, String anhangBeschreibung )
            throws IOException, EmailException
    {
        MultiPartEmail email = new MultiPartEmail();
        if( username != null && password != null ) {
            email.setAuthenticator( new DefaultAuthenticator( username, password ) );
            email.setSSLOnConnect( true );
        }
        email.setHostName( mailserver  );
        email.setFrom(absender);
        email.addTo(empfaenger);
        email.setCharset(textCharset);
        email.setSubject(betreff);
        email.setMsg(text);
        email.attach(new ByteArrayDataSource( anhangInputStream, anhangContentType ),
                anhangDateiName, anhangBeschreibung, EmailAttachment.ATTACHMENT );
        return email.send();
    }

    /** Ermittlung einiger MIME-Content-Typen aus der Dateiendung.
     Achtung: Dies sind nur Beispiele die eventuell ergaenzt oder modifiziert werden muessen.
     Weitere MIME-Typen siehe: http://de.selfhtml.org/diverses/mimetypen.htm.
     Achtung: Eventuell muessen die MIME-Content-Type-Strings um das Character-Encoding ergaenzt werden,
     z.B. "text/plain; charset=ISO-8859-1" oder "text/xml; charset=UTF-8",
     bzw. die bereits eingetragenen Character-Encodings durch andere ersetzt werden (z.B. bei text/html). */
    public static String getMimeContentTypeInclCharset( String filename )
    {
        final String[] mimeContentTypesArr = {
                "txt", "text/plain; charset=ISO-8859-1", "csv", "text/csv; charset=ISO-8859-1", "pdf", "application/pdf",
                "zip", "application/zip", "htm", "text/html; charset=ISO-8859-1", "html", "text/html; charset=UTF-8",
                "xml", "text/xml; charset=UTF-8", "xls", "application/vnd.ms-excel", "bin", "application/octet-stream" };
        if( filename != null && filename.trim().length() > 0 && filename.indexOf( '.' ) >= 0 ) {
            String fnExt = filename.substring( filename.lastIndexOf( '.' ) ).trim().toLowerCase(Locale.GERMAN);
            if( fnExt.length() > 1 ) {
                fnExt = fnExt.substring( 1 );
                if( mimeContentTypesMap == null ) {
                    Map<String,String> mimeContentTypesMapTemp = new HashMap<String,String>();
                    for( int i = 0; i < mimeContentTypesArr.length; i += 2 ) {
                        mimeContentTypesMapTemp.put( mimeContentTypesArr[i], mimeContentTypesArr[i+1] );
                    }
                    mimeContentTypesMap = mimeContentTypesMapTemp;
                }
                String mimeContentType = mimeContentTypesMap.get(fnExt);
                if( mimeContentType != null ) {
                    return mimeContentType;
                }
            }
        }
        return "application/octet-stream";
    }
}