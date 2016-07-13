<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$min_lat = $_POST["min_lat"];
$max_lat = $_POST["max_lat"];
$min_lng = $_POST["min_lng"];
$max_lng = $_POST["max_lng"];
$level = $_POST["level"];

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "select event.event_id, event.german_level_event, event.title, event.date, event.max_attendees, location.name, location.address, location.latitude, location.longitude"
. " from event join (select location_id, name, address, latitude, longitude"
. "	 from event_locations"
. "	 where latitude >= $min_lat and latitude <= $max_lat and longitude >= $min_lng and longitude <= $max_lng"
. "	 ) as location"
. " on event.location_id = location.location_id"
. " order by event.date;";

$result = mysqli_query($con, $sql);

$response = array();
while($row = mysqli_fetch_array($result)){
   array_push($response,array(
   'event_id' => $row[0],
   'german_level' => $row[1],
   'title' => $row[2],
   'date' => $row[3],
   'max_attendees' => $row[4],
   'location_name' => $row[5],
   'address' => $row[6],
   'latitude' => $row[7],
   'longitude' => $row[8]));
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

echo json_encode(array("local_events"=>utf8ize($response)));

?>
