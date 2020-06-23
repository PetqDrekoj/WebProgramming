<?php

    $username = $_GET['username'];
    $start_index = $_GET['start'];
    $end_index = $_GET['end'];
    $category = $_GET['cat'];

    include 'dbutils.php';

    $db = new dbutils();
    $db->selectURL($username,$category,$start_index,$end_index);


