import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class queryTitle extends ActionSupport {
    public String title;
    public String AuthorID;
    public String bookresult;
    public String authorresult;
    public String isExist;


    public queryTitle(){}

    public queryTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthorID(){
        return this.AuthorID;
    }

    public void setAuthorID(String ID){
        this.AuthorID = ID;
    }

    public String executeBookAjax(){
        try{
            String bookTitle = title;
            myDBConn myConn = new myDBConn();
            Map<String, String> jsonMap = myConn.getBookMsgByTitle(bookTitle);
            System.out.println(jsonMap);

            JSONObject jo = JSONObject.fromObject(jsonMap);
            System.out.println(jo);

            bookresult = jo.toString();
            myConn.closeConn();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    public String executeAuthorAjax(){
        try{
            myDBConn myConn = new myDBConn();
            Map<String, String> jsonMap = myConn.getAuthorMsgByTitle(title);

            JSONObject jo = JSONObject.fromObject(jsonMap);
            System.out.println(jo);

            authorresult = jo.toString();
            myConn.closeConn();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    public String executeDel(){
        try{
            myDBConn myConn = new myDBConn();
            myConn.delBookByTitle(title);
            myConn.closeConn();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    public String isTitleExist(){
        try{
            myDBConn myConn = new myDBConn();
            Map<String, String> jsonMap = new HashMap();
            String p = myConn.isTitleExist(title) ? "1" : "0";
            System.out.println("isexist" + p);
            jsonMap.put("isExist", p);
            JSONObject jo = JSONObject.fromObject(jsonMap);
            isExist = jo.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }
}
