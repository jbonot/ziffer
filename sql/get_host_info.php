<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="";
$server_name="localhost";

$username = !empty($_POST['username']) ? $_POST['username'] : '';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT firstName, lastName, german_level, image FROM profile_data WHERE user_name='$username'";

$result = mysqli_query($con, $sql);

$response = array();
while($row = mysqli_fetch_array($result)){
   array_push($response,array(
   'first_name' => $row[0],
   'last_name' => $row[1],
   'german_level' => $row[2],
   'image' => $row[3]));
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

echo json_encode(array("host_info"=>utf8ize($response)));

?>
