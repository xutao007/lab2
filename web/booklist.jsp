<%--
  Created by IntelliJ IDEA.
  User: xutao
  Date: 2017/9/20
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>书目列表</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <title></title>
    <style>
        .bg{
            filter: brightness(45%) blur(1px);
            top: -4px;
            left: -4px;
            width: 101%;
            height: 101%;
            position: fixed;
            z-index: -2;
        }
        .table-container{
            background: whitesmoke;
            margin-top: 6%;
            height: 600px;
            width: 80%;
            top: 10%;
            left: 10%;
            overflow-y: scroll;
            padding: 3em;
        }
        #msgModal, #updateModal{
            left: 50%;
            top: 40%;
            transform: translate(-50%, -50%);
            min-width: 80%; /*这个比例可以自己按需调节*/
        }
        .modal-body .col-sm-12+.col-sm-12{
            margin: 0.5em;
        }
        .modal-body span{
            font-weight: 500;
            font-size: 1.1em;
        }
        nav{
            filter :brightness(45%);
        }
        nav a{
            color: white;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid row">
        <div class="col-sm-4 col-sm-offset-4">
            <a class="navbar-brand" href="index.jsp">返回主页</a>
        </div>
    </div>
</nav>
<div>
    <img src="timg.jpg" class="bg">
</div>
<span id="authorID" class="hidden"><s:property value="id" /></span>
<div class="table-container col-md-6">
    <table class="table table-striped col-md-6">
        <caption class="h2" id="tableName"><s:property value="authorName" /></caption>

        <thead>
        <tr>
            <th style="width: 60%">书籍名称</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%--<tr>--%>
            <%--<td class="bookName">Tanmay</td>--%>
            <%--<td class="delBook">删除</td>--%>
            <%--<td class="changeBook">修改</td>--%>
        <%--</tr>--%>
        </tbody>
    </table>
</div>

<!--查询模态框-->
<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h3 class="modal-title">
                    书籍详细信息
                </h3>
            </div>
            <div class="modal-body row">
                <div style="height: auto">
                    <div class="col-md-6 container" id="bookMsg">
                        <div class="col-sm-12">
                            <h3>图书信息</h3>
                        </div>
                        <div class="col-sm-12">
                            <b>标题:</b>
                            <span id="bookTitle"></span>
                        </div>
                        <div class="col-sm-12">
                            <b>作者:</b>
                            <span id="bookAuthor"></span>
                        </div>
                        <div class="col-sm-12 hidden">
                            <b>作者ID:</b>
                            <span id="bookAuthorID"></span>
                        </div>
                        <div class="col-sm-12">
                            <b>ISBN:</b>
                            <span id="bookISBN"></span>
                        </div>
                        <div class="col-sm-12">
                            <b>出版社:</b>
                            <span id="bookPublisher"></span>
                        </div>
                        <div class="col-sm-12">
                            <b>出版日期:</b>
                            <span id="bookPublishDate"></span>
                        </div>
                        <div class="col-sm-12">
                            <b>价格:</b>
                            <span id="bookPrice"></span>
                        </div>
                    </div>

                    <div class="col-md-6 container" id="authorMsg">
                        <div class="col-sm-12">
                            <h3>作者信息</h3>
                        </div>
                        <div class="col-sm-12">
                            <b>姓名:</b>
                            <span id="authorName"></span>
                        </div>
                        <div class="col-sm-12">
                            <b>ID:</b>
                            <span id="authorID2"></span>
                        </div>
                        <div class="col-sm-12">
                            <b>年龄:</b>
                            <span id="authorAge"></span>
                        </div>
                        <div class="col-sm-12">
                            <b>国籍:</b>
                            <span id="authorCountry"></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<!--修改模态框-->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h3 class="modal-title" id="updateModalLabel">
                    修改书籍
                </h3>
            </div>
            <div class="modal-body row">
                    <div class="col-sm-12">
                        <h3 id="updateTitle">作者信息</h3>
                    </div>
                    <div class="col-sm-12">
                        <b>作者:</b>
                        <input class="input-sm" type="text" name="bookAuthor" id="updateAuthor">
                        <p></p>
                    </div>
                    <div class="col-sm-12">
                        <b>出版社:</b>
                        <input class="input-sm" type="text" name="bookPublisher" id="updatePublisher">
                        <p></p>
                    </div>
                    <div class="col-sm-12">
                        <b>出版日期:</b>
                        <input class="input-sm" type="text" name="bookPublishDate" id="updatePublishDate">
                        <p></p>
                    </div>
                    <div class="col-sm-12">
                        <b>价格:</b>
                        <input  class="input-sm" type="text" name="bookPrice" id="updatePrice">
                        <p></p>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" id="updateBtn" class="btn btn-primary">
                    修改
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>

<script src="jquery-3.1.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">

    function getTrString(bookname){
        var trString = "        <tr>\n" +
            "            <td class=\"bookName\">";
        trString += bookname;
        trString += "</td>\n" +
                "            <td class=\"delBook\" style='color:red'>删除</td>\n" +
                "            <td class=\"updateBook\" style='color:deepskyblue'>修改</td>\n" +
                "        </tr>";
        return trString;
    }

    //通过作者姓名得到书目
    function setTitlesByName(authorName){
        $.ajax({
            type:"post",
            url:"executeAjaxAction",
            data:{
                authorName: authorName
            },
            dataType:"json",
            success:function(data){
                var obj = JSON.parse(data);
                for(var i = 0; i < obj.length; i++){
                    $("tbody").append(getTrString(obj[i]));
                }
            }
        })
    }

    //ajax请求加载书籍信息
    function setBookMsg(title){
        $.ajax({
            type:"post",
            url:"setBookMsg",
            data:{
                title: title
            },
            dataType:"json",
            success :function(data){
                var obj = JSON.parse(data);
                $("#bookTitle").text(obj.Title);
                $("#bookAuthor").text(obj.Author);
                $("#bookISBN").text(obj.ISBN);
                $("#bookPrice").text(obj.Price);
                $("#bookPublishDate").text(obj.PublishDate);
                $("#bookPublisher").text(obj.Publisher);
                $("#bookAuthorID").text(obj.AuthorID);
            }
        });
    }

    //ajax请求加载作者个人信息
    function setAuthorMsg(title) {
        $.ajax({
            type:"post",
            url:"setAuthorMsg",
            data:{
                title:title
            },
            dataType:"json",
            success:function(data){
                var obj = JSON.parse(data);
                $("#authorName").text(obj.Name);
                $("#authorAge").text(obj.Age);
                $("#authorCountry").text(obj.Country);
                $("#authorID2").text(obj.authorID);
            }
        });
    }

    //文件加载完毕后的请求


    //点击标题后书籍以及作者的信息显示
    $("table").on("click", ".bookName", function(){
        var bookname = $(this).text();
        setBookMsg(bookname);
        setAuthorMsg(bookname);
        $("#msgModal").modal("show");
    });

    //点击删除之后的操作
    $("table").on("click", ".delBook", function(){
        var title = $(this).parent().find(".bookName").text();
        $.ajax({
            type:"post",
            url:"delbook",
            data:{
                title:title
            },
            dataType:"json",
            success:function(){
                $(this).parent().remove();
            }
        });
        $(this).parent().remove();
    });

    //点击修改后的操作
    $("table").on("click", ".updateBook", function(){
        var title = $(this).parent().find(".bookName").text();
        $.ajax({
            type:"post",
            url:"setBookMsg",
            data:{
                title: title
            },
            dataType:"json",
            success :function(data){
                var obj = JSON.parse(data);
                $("#updateTitle").text(title);
                $("#updateAuthor").attr("value", obj.AuthorID);
                $("#updatePrice").attr("value", obj.Price);
                $("#updatePublishDate").attr("value", obj.PublishDate);
                $("#updatePublisher").attr("value", obj.Publisher);
            }
        });
        $("#updateModal").modal("show");
    });


    //点击修改模态框中的修改
    $("#updateBtn").on("click", function(){
        var bookAuthor = $("#updateAuthor").val();
        var bookPrice = $("#updatePrice").val();
        var bookPublisher = $("#updatePublisher").val();
        var bookPublishDate = $("#updatePublishDate").val();
        var bookTitle = $("#updateTitle").text();
        var flag = true;
        var typeInt = "^-?\\d+$";
        var typeFloat = "^\\d+(\\.\\d+)?$";
        var rInt = new RegExp(typeInt);
        var rFloat = new RegExp(typeFloat);
        var s = "请输入有效数据";

        if(rInt.test(bookPrice) || rFloat.test(bookPrice)){
            $("#updatePrice").parent().children("p").text("");
            flag &= true;
        }
        else{
            $("#updatePrice").parent().children("p").text(s);
            flag &= false;
        }

        if(bookPublishDate.length != 10){
            $("#updatePublishDate").parent().children("p").text(s);
            flag &= false;
        }
        else{
            $("#updatePublishDate").parent().children("p").text("");
            flag &= true;
        }

        if(bookPublisher == ""){
            $("#updatePublisher").parent().children("p").text(s);
            flag &= false;
        }
        else{
            $("#updatePublisher").parent().children("p").text("");
            flag &= true;
        }

        if(bookAuthor == ""){
            $("#bookAuthor").parent().children("p").text(s);
            flag &= false;
        }
        else{
            $("#bookAuthor").parent().children("p").text("");
            flag &= true;
        }

        if(flag){
            //确认输入无误后ajax请求后台数据，判明作者是否存在，如果不存在要求填写作者信息，存在则直接提交表单
            $.ajax({
                type:"post",
                url:"isIDExist",
                data:{
                    id:bookAuthor
                },
                dataType:"json",
                success:function(data){
                    var obj = JSON.parse(data);
                    if(obj.isExist == "0"){
                        alert("输入的作者ID不存在，请先返回主页加入作者");
                    }
                    else{
                        //alert("开始修改");
                        //var authorID = obj.authorID;
                        //alert(bookTitle + " " + authorID + " " +  bookPublisher + " " +  bookPublishDate + " " +  bookPrice);
                        updateBook(bookTitle, bookAuthor, bookPublisher, bookPublishDate, bookPrice);
                    }
                }
            })
        }
    });

    //更新书籍
    function updateBook(updateTitle, updateAuthorID, updatePublisher, updatePublishDate, updatePrice){
        $.ajax({
            type:"post",
            url:"updateBook",
            data:{
                bookTitle: updateTitle,
                bookAuthor: updateAuthorID,  //此处的bookAuthor是一个ID
                bookPrice: updatePrice,
                bookPublisher: updatePublisher,
                bookPublishDate: updatePublishDate
            },
            dataType: "json",
            success:function(data){
                var obj = JSON.parse(data);
                if(obj.flag == "true"){
                    alert("更新成功");
                }
                else{
                    alert("更新失败");
                }
            }
        })
    }

    $(document).ready(function(){
        var authorName = $("#tableName").text();
        var authorID = $("#authorID").text();
        if(authorID == "-1"){
            $("#tableName").append("不存在");
        }
        else{
            setTitlesByName(authorName);
        }
//        alert(authorName);
    });
</script>
</html>
