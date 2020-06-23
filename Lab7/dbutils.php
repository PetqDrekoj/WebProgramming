<?php

class dbutils
{
    private $username = "root";
    private $password = "";
    private $ip = "127.0.0.1";
    private $dbname = "lab7";

    function selectURL($username, $category,$start_index, $end_index)
    {
        $conn = new mysqli($this->ip, $this->username, $this->password, $this->dbname);
        $sqlcommand = "SELECT * FROM urls U INNER JOIN 
                    (Select Us.user_id from users Us where Us.name = '" . $username . "') UU ON UU.user_id = U.user_id where U.category LIKE '%".$category . "%'";
        $result = $conn->query($sqlcommand);
        if ($result->num_rows > 0) {
            $i = 0;
            while ($row = $result->fetch_assoc()) {
                if($i >= $start_index && $i<$end_index) echo $row['url'] . ' ' . $row['description'] . ' ' . $row['category'] . '<br>';
                $i += 1;
            }
        } else {
            echo "You have no urls";
        }
        $conn->close();

    }

    function insertUser($username, $password)
    {
        $conn = new mysqli($this->ip, $this->username, $this->password, $this->dbname);
        $sqlcommand = "INSERT INTO users(name,password) VALUES('" . $username . "','" . $password . "')";
        $result = $conn->query($sqlcommand);
        $conn->close();
        if ($result === True) return True;
        return False;

    }

    function selectUser($username, $password)
    {
        $conn = new mysqli($this->ip, $this->username, $this->password, $this->dbname);
        $sqlcommand = "SELECT * FROM users U where U.name = '" . $username . "' and U.password = '" . $password . "'";
        $result = $conn->query($sqlcommand);
        $conn->close();
        if ($result->num_rows > 0) return True;
        return False;
    }

    function insertURL($username, $url, $description, $category)
    {
        $conn = new mysqli($this->ip, $this->username, $this->password, $this->dbname);
        $user_id = $conn->query("SELECT * FROM users U where U.name = '" . $username . "'")->fetch_assoc()['user_id'];
        $sqlcommand = "INSERT INTO urls(user_id, url, description,category) VALUES(" . $user_id . ",'" . $url . "','" . $description . "','" . $category . "')";
        $result = $conn->query($sqlcommand);
        $conn->close();
        if ($result === True) return True;
        return False;
    }

    function deleteURL($username, $url)
    {
        $conn = new mysqli($this->ip, $this->username, $this->password, $this->dbname);
        $user_id = $conn->query("SELECT * FROM users U where U.name = '" . $username . "'")->fetch_assoc()['user_id'];
        $sqlcommand = "DELETE FROM urls where url='" . $url . "' and user_id=" . $user_id;
        $result = $conn->query($sqlcommand);
        $conn->close();
        if ($result === True) return True;
        return False;
    }

    function updateURL($username, $url, $description, $category)
    {
        $conn = new mysqli($this->ip, $this->username, $this->password, $this->dbname);
        $user_id = $conn->query("SELECT * FROM users U where U.name = '" . $username . "'")->fetch_assoc()['user_id'];
        $sqlcommand = "UPDATE urls set description = '" . $description . "', category = '" . $category . "' where url ='" . $url . "' and user_id=" . $user_id;;
        $result = $conn->query($sqlcommand);
        $conn->close();
        if ($result === True) return True;
        return False;
    }
}

?>