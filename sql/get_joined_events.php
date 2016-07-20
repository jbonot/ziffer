<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$username = !empty($_POST['username']) ? $_POST['username'] : '';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT event_locations.name, event_locations.address,
events.german_level_event, events.title, events.date,
events.attending, events.max_attendees, events.event_id
FROM event_locations JOIN
	(SELECT event.event_id, event.german_level_event, event.title,
	event.date, guests.attending, event.max_attendees, event.location_id
	FROM event JOIN
		(SELECT my_events.event_id, guest_count.attending
		FROM event_guests AS my_events JOIN
			(SELECT event_id, COUNT(*) AS attending
			FROM event_guests GROUP BY event_id) as guest_count
		ON my_events.event_id = guest_count.event_id
		WHERE my_events.user_name_guests='$username') AS guests
	ON event.event_id=guests.event_id) AS events
ON event_locations.location_id = events.location_id;";

$result = mysqli_query($con, $sql);

$response = array();
while($row = mysqli_fetch_array($result)){
   array_push($response,array(
   'location_name' => $row[0],
   'location_address' => $row[1],
   'german_level' => $row[2],
   'title' => $row[3],
   'date' => $row[4],
   'count_attending' => $row[5],
   'max_attendees' => $row[6],
   'event_id' => $row[7]));
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

echo json_encode(array("joined_events"=>utf8ize($response)));

?>
