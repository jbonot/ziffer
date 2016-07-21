<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="";
$server_name="localhost";

$name = !empty($_POST['name']) ? $_POST['name'] : '-1';
$address = !empty($_POST['address']) ? $_POST['address'] : '-1';
$google_id = !empty($_POST['google_id']) ? $_POST['google_id'] : '-1';
$latitude = !empty($_POST['latitude']) ? $_POST['latitude'] : '-1';
$longitude = !empty($_POST['longitude']) ? $_POST['longitude'] : '-1';

$username = !empty($_POST['username']) ? $_POST['username'] : '-1';
$level = !empty($_POST['level']) ? $_POST['level'] : '-1';
$title = !empty($_POST['title']) ? $_POST['title'] : '-1';
$date = !empty($_POST['date']) ? $_POST['date'] : '-1';
$start_time = !empty($_POST['start_time']) ? $_POST['start_time'] : '-1';
$end_time = !empty($_POST['end_time']) ? $_POST['end_time'] : '-1';
$max_attendees = !empty($_POST['max_attendees']) ? $_POST['max_attendees'] : '-1';
$description = !empty($_POST['description']) ? $_POST['description'] : '-1';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "INSERT INTO event_locations (name, address, google_id, latitude, longitude) VALUES
('$name', '$address', '$google_id', $latitude, $longitude);";

mysqli_query($con, $sql);

$sql = "SELECT * FROM event_locations WHERE location_id=@@Identity";
$location_id =  mysqli_fetch_array(mysqli_query($con, $sql))[0];

$sql = "INSERT INTO event (user_name_host, german_level_event, title,
date, start_time, end_time, location_id, max_attendees, description) VALUES
('$username', '$level', '$title', '$date', '$start_time', '$end_time', $location_id, $max_attendees, '$description');";

mysqli_query($con, $sql);

$sql = "SELECT * FROM event WHERE event_id=@@Identity";
echo mysqli_fetch_array(mysqli_query($con, $sql))[0];

?>
