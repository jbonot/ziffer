<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$notification_ids = !empty($_POST['notification_ids']) ? $_POST['notification_ids'] : '-1';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql_condition = implode(' OR notifications_id=', explode(' ', $notification_ids));
$sql = "UPDATE notifications SET read_status=1 WHERE notifications_id=$sql_condition;";

if (mysqli_query($con, $sql)) {
    echo "SUCCESS";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($con);
}

mysqli_close($con);
?>
