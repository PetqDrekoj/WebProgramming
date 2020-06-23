<?php
    include 'dbutils.php';
    $user = $_POST["username"];
    $pass = $_POST["password"];

    $db = new dbutils();
    if ($db->insertUser($user,$pass)){
        echo "Successfully registered!";
    }
    else{
        echo "Name is already used! ;(";
    }




