<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$location_id = !empty($_POST['location_id']) ? $_POST['location_id'] : '-1';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT * FROM event_locations WHERE location_id=$location_id";

$result = mysqli_query($con, $sql);

$response = array();
while($row = mysqli_fetch_array($result)){
   array_push($response,array(
   'location_id' => $row[0],
   'name' => $row[1],
   'address' => $row[2],
   'google_id' => $row[3],
   'latitude' => $row[4],
   'longitude' => $row[5]));
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

echo json_encode(array("location"=>utf8ize($response)));

?>
