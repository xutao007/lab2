import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.*;


public class changeBookMsg extends ActionSupport {
    public String bookTitle;
    public String bookAuthor;  //此处传入的author是一个ID
    public String bookISBN;
    public String bookPrice;
    public String bookPublisher;
    public String bookPublishDate;
    public String saveFlag;
    public String updateFlag;
    public String getBookTitle(){
        return this.bookTitle;
    }
    public String getBookAuthor(){
        return this.bookAuthor;
    }
    public String getBookISBN(){
        return this.bookISBN;
    }
    public String getBookPrice(){
        return this.bookPrice;
    }
    public String getBookPublisher(){
        return this.bookPublisher;
    }
    public String getBookPublishDate(){
        return this.bookPublishDate;
    }
    public void setBookTitle(String s){
        this.bookTitle = s;
    }
    public void setBookAuthor(String s){
        this.bookAuthor = s;
    }
    public void setBookISBN(String s){
        this.bookISBN = s;
    }
    public void setBookPrice(String s){
        this.bookPrice = s;
    }
    public void setBookPublisher(String s){
        this.bookPublisher = s;
    }
    public void setBookPublishDate(String s){
        this.bookPublishDate = s;
    }


    public String saveBook(){
        try{
            myDBConn myConn = new myDBConn();
            boolean res = myConn.addBook(bookISBN, bookTitle, bookAuthor, bookPublisher, bookPublishDate, bookPrice);
            Map<String, String> jsonMap = new HashMap();
            if(res){
                jsonMap.put("flag", "true");
            }
            else{
                jsonMap.put("flag", "false");
            }
            JSONObject jo = JSONObject.fromObject(jsonMap);
            saveFlag = jo.toString();
            myConn.closeConn();

        }catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    public String updateBook(){
        try{
            myDBConn myConn = new myDBConn();
            boolean res = myConn.updateBook(bookTitle, bookAuthor, bookPublisher, bookPublishDate, bookPrice);
            Map<String, String> jsonMap = new HashMap();
            if(res){
                jsonMap.put("flag", "true");
            }
            else{
                jsonMap.put("flag", "false");
            }
            JSONObject jo = JSONObject.fromObject(jsonMap);
            updateFlag = jo.toString();
            myConn.closeConn();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }
}
