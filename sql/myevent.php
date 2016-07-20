<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";
$user_name_host = !empty($_POST['user_name']) ? $_POST['user_name'] : '';
// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT e.german_level_event,
e.title,
e.date,
e.start_time,
e.end_time,
e.min_attendees,
e.max_attendees,
e.description,
l.name,
l.address,
e.event_id
FROM `event` AS e
JOIN 
`event_locations` AS l
ON
e.user_name_host='".$user_name_host."'
AND
e.location_id=l.location_id ;"; 

$result = mysqli_query($con, $sql);

$response = array();
 
while($row = mysqli_fetch_array($result)){
 
 array_push($response,array(
   'german_level_event' => $row[0],
   'title' => $row[1],
   'date' => $row[2],
   'start_time' => $row[3],
   'end_time' => $row[4],
  'min_attendees' => $row[5],
   'max_attendees' => $row[6],
  'description' => $row[7],
   'name' => $row[8],
   'address' => $row[9],
   'event_id' => $row[10]));
   
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

echo json_encode(array("event_data"=>utf8ize($response)));
 
?>