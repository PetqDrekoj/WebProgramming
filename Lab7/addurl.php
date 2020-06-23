<?php
    $method = $_GET['method'];
    $username = $_GET['username'];
    $url = $_GET['url'];
    $desc = $_GET['desc'];
    $cat = $_GET['cat'];
    include 'dbutils.php';
    $db = new dbutils();
    if($method == 'add') $db->insertURL($username,$url,$desc,$cat);
    if($method == 'update') $db->updateURL($username,$url,$desc,$cat);
    if($method == 'remove') $db->deleteURL($username,$url);
