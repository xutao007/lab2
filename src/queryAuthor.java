import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import net.sf.json.*;

import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class queryAuthor extends ActionSupport {
    public String authorName;
    public String result;
    public String id;
    public String isExist;

    public  queryAuthor(){}

    public queryAuthor(String authorName){
        this.authorName = authorName;
    }
    @Override
    public String toString(){
        return "queryAuthor{" +
                "authorName=" + authorName +
                '}';
    }
    public String getResult(){
        return this.result;
    }
    public void setResult(){
        this.result = result;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String ID){
        this.id = ID;
    }
    public String getIsExist(){
        return this.isExist;
    }
    public void setIsExist(String isExist){
        this.isExist = isExist;
    }
    public String getauthorName(){
        return this.authorName;
    }

    public void setauthorName(String name){
        this.authorName = name;
    }

    public String query(){
//        myDBConn myConn = new myDBConn();
//        System.out.println(myConn.getIDByName("川端康成"));
//        System.out.println(myConn.getBookMsgByTitle("战争与和平"));
//        System.out.println(myConn.getAuthorMsgByID(5));
//        System.out.println(myConn.getTitilesByID(9));
//        System.out.println(myConn.delBookByTitle("准备删除的书"));

//        System.out.println(this);
        String name = authorName;
        System.out.println(name);
        myDBConn myConn = new myDBConn();
        int authorID = myConn.getIDByName(name);
        id = "" + authorID;
        System.out.println(id + "hello");
        myConn.closeConn();
        return "success";

    }
    public String isAuthorExist(){
        try{
            String name = authorName;
            myDBConn myConn = new myDBConn();
            int authorID = myConn.getIDByName(name);
            Map<String, String> jsonMap = new HashMap();
            if(authorID < 0){
                jsonMap.put("authorID", "-1");
            }
            else{
                jsonMap.put("authorID", "" + authorID);
            }

            JSONObject jo = JSONObject.fromObject(jsonMap);
            isExist = jo.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    public String isIDExist(){
        try{
            myDBConn myConn = new myDBConn();
            Map<String, String> jsonMap = new HashMap();
            String p = myConn.isIDExist(id) ? "1" : "0";
            System.out.println("isexist" + p);
            jsonMap.put("isExist", p);
            JSONObject jo = JSONObject.fromObject(jsonMap);
            isExist = jo.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    public String executeAjax(){
        try{
            String name = authorName;
            System.out.println(name);
            myDBConn myConn = new myDBConn();
//            int authorID = myConn.getIDByName(name);
//            ArrayList<String> titles = myConn.getTitilesByID(authorID);
            ArrayList<String> titles = myConn.getTitlesByName(name);
            System.out.println(titles);
            Map<String, String> jsonMap = new HashMap();
            for(int i = 0; i < titles.size(); i++){
                jsonMap.put(""+i, titles.get(i));
            }

            JSONArray ja = JSONArray.fromObject(titles);
            System.out.print(ja);

            result = ja.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

}
