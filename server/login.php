<?php

define('HOST', 'LOCALHOST');
define('USER', 'root');
define('PASS', 'root');
define('DB', 'school_app_db')

$conn = mysqli_connect(HOST,USER,PASS,DB);

$username = $_POST['username'];
$password = $_POST['password'];

$sql = "select * from users username='$username' and password='$password'";

$res = mysqli_query($conn,$sql);

$check = mysqli_fetch_array($res);

if(isset($check)){
echo 'succes';
}else{
echo 'failure';
}

mysqli_close($conn);
?>