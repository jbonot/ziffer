<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$user_recipient = $_POST["user_recipient"];
$user_sender = $_POST["user_sender"];
$event_id = $_POST["event_id"];
$message_type = $_POST["message_type"];

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "INSERT INTO notifications (user_recipient, user_sender, event_id, message_type)
	values ('$user_recipient', '$user_sender', $event_id, $message_type);";

if (mysqli_query($con, $sql)) {
    echo "SUCCESS";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($con);
}

mysqli_close($con);
?>
