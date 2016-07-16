<?php
$db_name="webappdb_new";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";
//$user_name = $_POST["user_name"];
$user_name = "jasimwhd";


  
  
// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT * FROM `profile_data` WHERE `user_name`='".$user_name."' ;";
$result = mysqli_query($con, $sql);
 
$response = array();
 
while($row = mysqli_fetch_array($result)){
   $response = array("profile_data_id"=>$row[0],
   "user_name"=>$row[1],
   "firstName"=>$row[2],
   "lastName"=>$row[3],
   "dob"=>$row[4],
   "german_level"=>$row[5],
   "description"=>$row[6],
   "image"=>$row[8]);

}

echo json_encode(array("profile_data"=>$response));
 
?>