<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$event_id = !empty($_POST['event_id']) ? $_POST['event_id'] : '-1';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT * FROM event WHERE event_id=$event_id";

$result = mysqli_query($con, $sql);

$response = array();
while($row = mysqli_fetch_array($result)){
   array_push($response,array(
   'event_id' => $row[0],
   'host_username' => $row[1],
   'german_level' => $row[2],
   'title' => $row[3],
   'date' => $row[4],
   'start_time' => $row[5],
   'end_time' => $row[6],
   'location_id' => $row[7],
   'min_attendees' => $row[8],
   'max_attendees' => $row[9],
   'description' => $row[10]));
}

function utf8ize($d) {
    if (is_array($d)) {
        foreach ($d as $k => $v) {
            $d[$k] = utf8ize($v);
        }
    } else if (is_string ($d)) {
        return utf8_encode($d);
    }
    return $d;
}

echo json_encode(array("event"=>utf8ize($response)));

?>
