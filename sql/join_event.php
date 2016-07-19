<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$event_id = !empty($_POST['event_id']) ? $_POST['event_id'] : '-1';
$username = !empty($_POST['username']) ? $_POST['username'] : '';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "INSERT INTO event_guests (event_id, user_name_guests) values ($event_id, '$username');";

if (mysqli_query($con, $sql)) {
    echo "SUCCESS";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($con);
}

mysqli_close($con);
?>
