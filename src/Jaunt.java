import com.jaunt.Document;
import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import com.jaunt.component.Form;
import com.jaunt.util.HandlerForBinary;
import com.jaunt.util.HandlerForText;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by aaroncio on 16/07/17.
 */
public class Jaunt {

    public static void useHandlers(UserAgent userAgent){
        try{
            //create UserAgent and content handlers.
            HandlerForText handlerForText = new HandlerForText();
            HandlerForBinary handlerForBinary = new HandlerForBinary();

            //register each handler with a specific content-type
            userAgent.setHandler("text/css", handlerForText);
            userAgent.setHandler("text/javascript", handlerForText);
            userAgent.setHandler("application/x-javascript", handlerForText);
            userAgent.setHandler("image/gif", handlerForBinary);
            userAgent.setHandler("image/jpeg", handlerForBinary);

            //retrieve CSS content as String
            /*userAgent.visit("http://jaunt-api.com/syntaxhighlighter/styles/shCore.css");
            System.out.println(handlerForText.getContent());

            //retrieve JS content as String
            userAgent.visit("http://jaunt-api.com/syntaxhighlighter/scripts/shCore.js");
            System.out.println(handlerForText.getContent());*/

            //retrieve GIF content as byte[], and print its length
            userAgent.visit("https://cfdiau.sat.gob.mx/nidp/jcaptcha.jpg");

            FileOutputStream out = new FileOutputStream("/Users/aaroncio/Documents/captchaTest.jpg");
            byte[] data = new byte[1024];
            out.write(handlerForBinary.getContent());
            out.flush();
            out.close();

            System.out.println(userAgent.cookies);



        }
        catch(Exception e){
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        try{
            UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).

            userAgent.visit("https://portalcfdi.facturaelectronica.sat.gob.mx/");

            System.out.println(userAgent.response);
            System.out.println(userAgent.cookies);
            //System.out.println(userAgent.doc.innerHTML());               //print the document as HTML



            System.out.println("****************************  Pedir formulario *********************************");
            userAgent.doc = userAgent.sendPOST("https://cfdiau.sat.gob.mx/nidp/wsfed/ep?id=SATUPCFDiCon&sid=0&option=credential&sid=0", null);
            System.out.println("POST connection made");
            System.out.println(userAgent.response);
            System.out.println(userAgent.cookies);
            useHandlers(userAgent);


            System.out.println("*************************** Llenar formulario **********************************");

            Scanner in = new Scanner(System.in);
            String rfc = "DOOA860406K73";
            String pass = "";
            String captcha = in.next();


            //userAgent.sendPOST(" https://cfdiau.sat.gob.mx/nidp/wsfed/ep", "credential&Ecom_User_ID=AAA010101AAA&Ecom_Password=Welcome1&jcaptcha=br7t8rs&submit=Enviar");
            userAgent.sendPOST(" https://cfdiau.sat.gob.mx/nidp/wsfed/ep",
                    "credential&Ecom_User_ID=" + rfc +
                            "&Ecom_Password=" + pass +
                            "&jcaptcha=" + captcha +
                            "&submit=Enviar");


            System.out.println("*************************** Respuesta Formulario **********************************");

            System.out.println(userAgent.getLocation());
            System.out.println(userAgent.response);
            System.out.println(userAgent.doc.innerHTML());

        }
        catch(Exception e){         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }
    }
}
