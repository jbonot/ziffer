<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$user_recipient = $_POST["user_recipient"];

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT * FROM notifications
	WHERE user_recipient = '$user_recipient'
	AND read_status = 0;";

$result = mysqli_query($con, $sql);

$count = 0;
while($row = mysqli_fetch_array($result)){
   $count++;
}

echo $count;

?>
