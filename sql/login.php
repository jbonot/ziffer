/*
<?php  
require "init.php";  
$user_name = $_POST["login_name"];  
$user_pass =  $_POST["login_pass"];  
$sql_query = "select name from user_info where user_name like '$user_name' and user_pass like '$user_pass';";  
$result = mysqli_query($con,$sql_query);  
if(mysqli_num_rows($result) >0 )  
{  
$row = mysqli_fetch_assoc($result);  
$name =$row["name"];  
echo "Login Success..Welcome ".$name;  
}  
else  
{   
echo "Login Failed.......Try Again..";  
}  
?>  

*/
<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";
$user_name = $_POST["login_name"];  
$user_pass =  $_POST["login_pass"];  
// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "select name from user_info where user_name like '$user_name' and user_pass like '$user_pass';"; 
$result = $con->query($sql);

if(mysqli_num_rows($result) >0 )  
{  
$row = mysqli_fetch_assoc($result);  
$name =$row["name"];  
echo "Login Success..Welcome ".$name;  
}  
else  
{   
echo "Login Failed.......Try Again..";  
}  
$conn->close();
?>
