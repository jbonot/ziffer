<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="";
$server_name="localhost";

$event_id = !empty($_POST['event_id']) ? $_POST['event_id'] : '-1';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT profile.user_name, profile.firstName, profile.lastName, 
		profile.german_level, profile.image
		FROM profile_data AS profile JOIN
			(SELECT * FROM event_guests WHERE event_id=$event_id) AS guests
		ON profile.user_name = guests.user_name_guests;";

$result = mysqli_query($con, $sql);

$response = array();
while($row = mysqli_fetch_array($result)){
   array_push($response,array(
   'username' => $row[0],
   'first_name' => $row[1],
   'last_name' => $row[2],
   'german_level' => $row[3],
   'image' => $row[4]));
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

echo json_encode(array("event_guests"=>utf8ize($response)));

?>
