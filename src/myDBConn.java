import org.apache.commons.lang3.ObjectUtils;
import org.apache.struts2.components.Push;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

public class myDBConn {

    private Connection conn;

    public myDBConn(){
        //尝试加载MySQL驱动
        try{
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        }catch(ClassNotFoundException e1){
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
        }

        //数据准备
        String url = "jdbc:mysql://123.206.9.180:3306/BookDB";    //JDBC的URL
        String user = "root";
        String pwd = "123456";
        String connectionString = "jdbc:mysql://123.206.9.180:3306/" + "BookDB" + "?user=" + user + "&password=" + pwd + "&useUnicode=true&characterEncoding=UTF-8";
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        try {
//            conn = DriverManager.getConnection(url, user, pwd);
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //关闭连接
    public boolean closeConn(){
        boolean flag = false;
        try{
            conn.close();
            flag = true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    //通过作者姓名获取ID
    public int getIDByName(String name){
        int authorID = -1;

        try{
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！");
            String sql = "SELECT * FROM Author WHERE Name = '" + name + "';";
            ResultSet rs = stmt.executeQuery(sql);

            //如果为空
            if(rs == null){
                System.out.println("查询为空");
                stmt.close();
                return authorID;
            }
            while(rs.next()){
                authorID = rs.getInt("AuthorID");
            }

            System.out.println(authorID);
            System.out.println("数据库链接准备关闭");
            stmt.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return authorID;
    }

    //通过作者ID获取姓名
    public String getNameByID(int authorID){
        String author = "";
        try{
            Statement stmt  = conn.createStatement();
            String sql = "SELECT * FROM Author WHERE AuthorID = " + authorID + ";";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                author = rs.getString("Name");
            }
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return author;
    }

    //通过作者ID查询书本题目
    public ArrayList getTitilesByID(int authorID){
        ArrayList<String> titles = new ArrayList<>();
        try{
            Statement stmt = conn.createStatement();
            System.out.println("getTitlesByID 开始");
            String sql = "SELECT Title FROM Book WHERE AuthorID = " + authorID + ";";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String title = rs.getString("Title");
                titles.add(title);
            }

            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return titles;
    }
    //通过作者姓名获取书本题目
    public ArrayList getTitlesByName(String authorName){
        ArrayList<String> titles = new ArrayList<>();
        try{
            Statement stmt = conn.createStatement();
            System.out.println("getTitlesByName 开始");
            ArrayList<Integer> IDs = new ArrayList<>();
            String sql = "SELECT * FROM Author WHERE Name = '" + authorName + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int authorID = rs.getInt("AuthorID");
                IDs.add(authorID);
            }
            for(int ID : IDs){
                sql = "SELECT Title FROM Book WHERE AuthorID = " + ID + ";";
                 rs = stmt.executeQuery(sql);
                while(rs.next()){
                    String title = rs.getString("Title");
                    titles.add(title);
                }
            }

            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return titles;
    }
    //通过作者ID获取作者的个人信息
    public Map<String, String> getAuthorMsgByID(int authorID){
        Map<String, String> authorMsg = new HashMap();

        String name = "";
        String age = "";
        String country = "";

        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Author WHERE AuthorID = '" + authorID + "';";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                name = rs.getString("Name");
                age = "" + rs.getInt("Age");
                country = rs.getString("Country");
            }
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        //建立键值对关系
        authorMsg.put("authorID", "" + authorID);
        authorMsg.put("Name", name);
        authorMsg.put("Age", age);
        authorMsg.put("Country", country);

        return authorMsg;
    }

    //通过书本题目获取该书信息
    public Map<String, String> getBookMsgByTitle(String Title){
        Map<String, String> bookMsg = new HashMap();

        String ISBN = "";
        String publisher = "";
        String publishDate = "";
        String price = "";
        String author = "";
        int authorID = -1;

        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Book WHERE Title = '" + Title + "';";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                ISBN = rs.getString("ISBN");
                publisher = rs.getString("Publisher");
                publishDate = (new SimpleDateFormat("yyyy-MM-dd")).format(rs.getDate("PublishDate"));
                price = "" + rs.getFloat("Price");
                authorID = rs.getInt("AuthorID");
            }
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        //得到作者姓名
        if(authorID != -1){
            author = getNameByID(authorID);
        }

        bookMsg.put("ISBN", ISBN);
        bookMsg.put("Title", Title);
        bookMsg.put("Publisher", publisher);
        bookMsg.put("PublishDate", publishDate);
        bookMsg.put("Price", price);
        bookMsg.put("AuthorID", "" + authorID);
        bookMsg.put("Author", author);

        return bookMsg;
    }

    //通过书本题目获取作者个人信息
    public Map<String, String> getAuthorMsgByTitle(String Title){
        int authorID = Integer.parseInt(getBookMsgByTitle(Title).get("AuthorID"));
        return getAuthorMsgByID(authorID);
    }

    //根据书本题目删除书籍
    public boolean delBookByTitle(String title){
        boolean flag = false;
        try{
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Book WHERE Title = '" + title + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            flag = true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    //添加作者
    public boolean addAuthor(String name, String authorID, int age, String country){
        boolean flag = false;
        try{
            String sql = "INSERT INTO Author(Name, AuthorID, Age, Country)" + "VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setInt(2, Integer.parseInt(authorID));
            stmt.setInt(3, age);
            stmt.setString(4, country);

            int i = stmt.executeUpdate();
            if(i == 1)
                flag = true;

            stmt.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    //添加书本
    public boolean addBook(String ISBN, String Title, String AuthorID, String Publisher, String PublishDate, String Price){
        boolean flag = false;
        try{
            String sql = "insert into Book(ISBN, Title, AuthorID, Publisher, PublishDate, Price)" +
                    " values(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            //System.out.println(ISBN);
            stmt.setString(1, ISBN);
            //System.out.println(Title);
            stmt.setString(2, Title);
            //System.out.println(AuthorID);
            stmt.setInt(3, Integer.parseInt(AuthorID));
            //System.out.println(Publisher);
            stmt.setString(4, Publisher);

            System.out.println(PublishDate);
            java.util.Date  date  =  new SimpleDateFormat("yyyy-MM-dd").parse(PublishDate);
            java.sql.Date  sqlDate  =  new java.sql.Date(date.getTime());
            stmt.setDate(5, sqlDate);
            System.out.println(Price);
            stmt.setFloat(6, Float.parseFloat(Price));
            int i = stmt.executeUpdate();
            if(i == 1){
                flag = true;
            }
            stmt.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    //更新书本
    public boolean updateBook(String bookTitle, String authorName, String publisher, String publishDate, String price){
        boolean flag = false;
        System.out.println("updateBook" + bookTitle + authorName + publishDate + publisher + price);
        try{
            //int authorID = getIDByName(authorName);
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(publishDate);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Statement stmt = conn.createStatement();
            String sql = "SET FOREIGN_KEY_CHECKS=0;";
            stmt.executeUpdate(sql);
            sql = "UPDATE Book SET AuthorID = " + authorName +
                         ", Publisher = '" + publisher +
                         "', PublishDate = '" + sqlDate +
                         "', Price = '" + price +
                         "'WHERE Title = '" + bookTitle + "';";
            int i = stmt.executeUpdate(sql);
            if(i == 1)
                flag = true;
            sql = "SET FOREIGN_KEY_CHECKS = 1;";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    //查询书籍是否存在
    public boolean isTitleExist(String bookTitle){
        boolean flag = false;
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Book WHERE Title = '" + bookTitle + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                flag = true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return  flag;
    }
    public boolean isIDExist(String authorID){
        boolean flag = false;
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Author WHERE AuthorID = " + authorID + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                flag = true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }
}