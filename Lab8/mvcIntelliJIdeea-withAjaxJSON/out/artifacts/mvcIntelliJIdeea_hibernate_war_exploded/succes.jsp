<%@ page import="webubb.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Quiz</title>
    <style>
    </style>
    <script src="js/jquery-2.0.3.js"></script>
    <script src="js/ajax-utils.js"></script>

    <script>
        function valid(val){
            if(val === "" || val == null){
                return "Please enter a value in the field!"
            }
            return "true";
        }


        $(document).ready(function() {
            $("#button").click(function() {
                var v1 = $("#numberOfQuestions").val();
                var v2 = $("#questions_per_page").val();
                if(valid(v1)==="true" && valid(v2)==="true"){
                var nr = $("#numberOfQuestions").val();
                var nr_per_page = $("#questions_per_page").val();
                console.log(nr,nr_per_page);
                $.post("QuizController",
                    {
                        number_questions: nr,
                        questions_per_page: nr_per_page
                    },
                    function(b){
                            console.log(b);
                            if(b === "true")
                                window.location = `../col/quiz.jsp`;
                            else window.location=`../col/error.jsp`;
                    }
                );
                }
                else if(valid(v2)==="true"){
                    $("#error").html(valid(v2));
                }
                else {
                    $("#error").html(valid(v1));
                }
            });

            $("#logout").click(function() {
                $.post("LoginController",
                    {
                        logout: "true"
                    },
                    function (b) {
                        if (b === "true"){
                            sessionStorage.clear();
                            window.location = `../col/index.html`;}
                    }
                );
            });

        });
    </script>
</head>


<body>
<%! User user; %>
<div style="width:200px; max-height:400px; margin:auto; margin-top:30px;text-align: center; line-height: 40px;border:1px solid black; border-radius: 2px;padding-top:30px;">
<%  user = (User) session.getAttribute("user");
    if (user != null) {
        out.println("Welcome "+user.getUsername());
%>

<br>Number of Questions<input id="numberOfQuestions" type="number">
<br>Number of Questions per page<input id="questions_per_page"  type="number">
<div id="error"></div>
<button id="button"> Start quiz </button>
    <button id="logout">Log out</button>
</div>


<%
    }
%>

</body>
</html>