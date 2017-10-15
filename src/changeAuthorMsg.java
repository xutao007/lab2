import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class changeAuthorMsg extends ActionSupport {
    public String authorName;
    public String authorAge;
    public String authorCountry;
    public String authorID;
    public String saveFlag;

    public String getAuthorName(){
        return this.authorName;
    }
    public String getAuthorAge(){
        return this.authorAge;
    }
    public String getAuthorCountry(){
        return this.authorCountry;
    }
    public String getAuthorID(){return this.authorID;}
    public void setAuthorName(String s){
        this.authorName = s;
    }
    public void setAuthorAge(String s){
        this.authorAge = s;
    }
    public void setAuthorCountry(String s){
        this.authorCountry = s;
    }
    public void setAuthorID(String s){this.authorID = s;}

    public String saveAuthor(){
        try{
            myDBConn myConn = new myDBConn();
            boolean res = myConn.addAuthor(authorName, authorID, Integer.parseInt(authorAge), authorCountry);
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
}
