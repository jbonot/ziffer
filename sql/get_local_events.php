<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$min_lat = !empty($_POST['min_lat']) ? $_POST['min_lat'] : -180;
$max_lat = !empty($_POST['max_lat']) ? $_POST['max_lat'] : 180;
$min_lng = !empty($_POST['min_lng']) ? $_POST['min_lng'] : -180;
$max_lng = !empty($_POST['max_lng']) ? $_POST['max_lng'] : 180;

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT e.event_id, e.german_level_event, e.title, e.date,
		e.max_attendees, e.name, e.address, e.latitude,
		e.longitude, e.start_time, guests.attendees
		FROM (SELECT event_id, COUNT(*) AS attendees
			 FROM event_guests GROUP BY event_id) AS guests
		JOIN (SELECT event.event_id, event.german_level_event, event.title, event.date,
			event.max_attendees, location.name, location.address, location.latitude,
			location.longitude, event.start_time
			FROM event JOIN (SELECT location_id, name, address, latitude, longitude
				FROM event_locations
				WHERE latitude >= $min_lat AND latitude <= $max_lat
				AND longitude >= $min_lng AND longitude <= $max_lng ) AS location
			ON event.location_id = location.location_id) AS e
		ON e.event_id=guests.event_id
		ORDER BY e.date;";

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
   'longitude' => $row[8],
   'start_time' => $row[9],
   'attendees' => $row[10]));
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
