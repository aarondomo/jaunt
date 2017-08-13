import com.jaunt.Elements;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import com.jaunt.component.Form;
import sun.plugin.dom.core.Element;

/**
 * Created by aaroncio on 16/07/17.
 */
public class Test {
    public static void main(String[] args) {
        UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
        try {
            userAgent.visit("http://testing-ground.scraping.pro/captcha");
            System.out.println(userAgent.response);
            System.out.println(userAgent.doc.innerHTML());               //print the document as HTML

            //userAgent.doc.fillout("User name:", "admin");       //fill out the component labelled 'Username:' with "tom"
            //userAgent.doc.fillout("Password:", "12345");    //fill out the component labelled 'Password:' with "secret"
            //userAgent.doc.submit();                          //submit the form
            com.jaunt.Elements elements = userAgent.doc.findEach("<form>");

            for(com.jaunt.Element e : elements.toList()){
                System.out.println("form-------> "  + e.outerHTML());
                break;
            }

            elements = elements.findEach("<img>");
            for(com.jaunt.Element e : elements.toList()){
                System.out.println("img-------> "  + e.outerHTML());
                //break;
            }

            Form form = userAgent.doc.getForm("<form>");
            String captcha = "5eTh6W";
            form.setTextField("captcha_code", captcha);
            form.submit("Submit form");

            System.out.println(userAgent.getLocation());     //print the current location (url)

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
