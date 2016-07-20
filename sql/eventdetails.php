<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";
$event_id=$_POST["event_id"];
//$event_id=15;


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
e.event_id,
p.image,
p.firstName,
p.german_level
FROM `event` AS e
JOIN `event_locations` AS l 
ON e.event_id='$event_id' AND e.location_id=l.location_id
JOIN `profile_data` AS p
ON e.user_name_host=p.user_name;"; 

$result = mysqli_query($con, $sql);

$response = array();
 
while($row = mysqli_fetch_array($result)){
/* echo $row[0]."<br>";
 echo $row[1]."<br>";
 echo $row[2]."<br>";
 echo $row[3]."<br>";
 echo $row[4]."<br>";
 echo $row[5]."<br>";
 echo $row[6]."<br>";
 echo $row[7]."<br>";
 echo $row[8]."<br>";
 echo $row[9]."<br>";
 */
 
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
   'event_id' => $row[10],
   'image' => $row[11],
   'firstName' => $row[12],
   'german_level' => $row[13]));
   
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

echo json_encode(array("event_details"=>utf8ize($response)));
 
?>