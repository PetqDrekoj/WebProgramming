<%@ page import="webubb.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: dyeni
  Date: 5/21/2020
  Time: 10:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  User user = (User) session.getAttribute("user");
    if (user != null) {
%>
<html>
<head>
    <title>Title</title>
    <style>
        @keyframes example {
            from {opacity: 0; transform: rotateX(10deg);}
            to {opacity:1; transform: rotateX(0deg);}
        }
        body{
            width:100%;
            height:100%;
        }
        #quizdiv, #seerez, #next, #previous,#logout{
            animation-name: example;
            animation-duration: 1s;
        }
        #seerez,#next,#previous,#logout{
            text-align:center;
            display:inline-block;
            color:white;
        }
        #quizdiv{
            padding-top:50px;
            padding-bottom: 150px;
            padding-left:100px;
        }


    </style>
</head>
<script src="js/jquery-2.0.3.js"></script>
<script>
    var page = 1;
    var responses = [];
    var correctResponses = [];
    var quest = [];
    function showQuestions(questions) {

        if (questions === "false") {
            console.log(responses);
            page-=1;
        }
        else{
            var s = "";
            s = s + "<ul>";
            $.each(questions, function (key, value) {
                console.log(value["question_id"]);
                var check = 0;
                correctResponses.forEach(p=>{
                    if(p.qid===value["question_id"]) {p.rid=value["value"]; check = 1;}
                });
                if(check === 0) correctResponses.push({qid: value["question_id"], rid: value["value"]});

                s = s + "<li>" + value["question"] + "</li>";
                s = s + "<input id='raspuns' ";
                if(responses.filter(p=>p.qid===value["question_id"]&&p.rid==="1").length>0){s+="checked";}
                s = s + " name='" + value["question_id"] + "' type='radio' value='0'>" + value["0"];
                s = s + "<br><input id='raspuns' ";
                if(responses.filter(p=>p.qid===value["question_id"]&&p.rid==="2").length>0){s+="checked";}
                s = s + " name='" + value["question_id"] + "' type='radio' value='1'>" + value["1"];
                s = s + "<br><input id='raspuns' ";
                if(responses.filter(p=>p.qid===value["question_id"]&&p.rid==="3").length>0){s+="checked";}
                s = s + " name='" + value["question_id"] + "' type='radio' value='2'>" + value["2"];
                s = s + "<br><input id='raspuns' ";
                if(responses.filter(p=>p.qid===value["question_id"]&&p.rid==="4").length>0){s+="checked";}
                s = s + " name='" + value["question_id"] + "' type='radio' value='3'>" + value["3"];
            });
            s = s + "</ul>";
            $("#quizdiv").html(s);
            if(page === 1) $("#previous").hide();
            else $("#previous").show();
            $.get("QuizController", {page: page+1}, function (data) {if(data==="false") {$("#next").hide(); $("#seerez").show();} else {$("#next").show(); $("#seerez").hide();}});
        }
    }

    function registerAnswers() {
        $.map($("input:radio:checked"), function (elem, idx) {
            var a = $(elem).attr("name");
            var b = $(elem).val();
            var used = 0;
            responses.forEach(p=>{if(p.qid === a) {p.rid = b; used=1;}});
            if(used == 0) responses.push({qid: a, rid: b});
        });
    }

    function saveState(){
        $.get("LoginController", {result: responses}, function () {
            console.log("am salvat");
        });
    }

    function loadState(){
        $.get("LoginController", {}, function (data) {
            this.responses = data;
        });
    }


    $(document).ready(function () {


            $.get("QuizController", {page: page}, function (data) {
                showQuestions(data)
            });

            $(document).on('click', '#seerez', function () {
                registerAnswers();
                if (correctResponses.length === responses.length) {
                    $("#next").hide();
                    $("#seerez").hide();
                    $("#error").hide();
                    $("#previous").hide();
                    var score = 0;
                    console.log(correctResponses);
                    console.log(responses);
                    responses.forEach(r => {
                        correctResponses.forEach(c => {
                            if (c.qid === r.qid && c.rid === r.rid) score += 1;
                        });
                    });
                    $.get("QuizController", {score: score}, function (data) {
                        data = data.split(',');
                        var s = "Your best score was ";
                        s += data[0] + " <br>Now your score is " + score + " from " + data[1] + " questions.";
                        s += "<br> You got wrong " + (parseInt(data[1]) - parseInt(score)) + " questions.";
                        $("#quizdiv").html(s);
                    });
                } else $("#error").html("Please complete all the questions!");
            });

            $(document).on('click', '#next', function () {
                $("#error").hide();
                registerAnswers();
                page += 1;
                $.get("QuizController", {page: page}, function (data) {
                    showQuestions(data)
                });
            });

            $(document).on('click', '#previous', function () {
                $("#error").hide();
                registerAnswers();
                page -= 1;
                $.get("QuizController", {page: page}, function (data) {
                    showQuestions(data)
                });

            });

            $("#logout").click(function () {
                $.post("LoginController",
                    {
                        logout: "true"
                    },
                    function (b) {
                        if (b === "true") {
                            sessionStorage.clear();
                            window.location = `../col/index.html`;
                        }
                    }
                );
            });

    });

</script>
<body>
<div id="quizdiv">


</div>
<div id="error" style="color:darkred"></div>
<div id="next" style="border:1px black;background:green; width:100px; height:30px; padding:10px; margin:10px; cursor:pointer;">Next page</div>
<div id="previous" style="border:1px black;background:green; width:100px; height:30px; padding:10px; margin:10px; cursor:pointer;">Previous page</div>
<div id="seerez" style="border:1px black;background:green; width:100px; height:30px; padding:10px; margin:10px; cursor:pointer;">Submit</div>
<div id="logout" style="border:1px black;background:green; width:100px; height:30px; padding:10px; margin:10px; cursor:pointer;">Log out</div>
</body>
</html>
<% } %>