<?php
    include 'dbutils.php';

    $username = $_POST['username'];
    $password = $_POST['password'];

    $db = new dbutils();

    if($db->selectUser($username,$password)) {
        $cookie_name = "user";
        $cookie_value = $username;
        setcookie($cookie_name, $cookie_value, time() + (86400), "/"); // 86400 = 1 day
        header("Location:main.html");
        exit();

    }
    else{
        echo "Login not successful! ;(";
    }
