<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";
$user_name_host = $_POST["user_name"];  
  
// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT * FROM `event` WHERE `user_name_host`='".$user_name_host."' ;";
$result = mysqli_query($con, $sql);
 
$response = array();
 
while($row = mysqli_fetch_array($result)){
/*   $response = array("event_id"=>$row[0],
   "user_name_host"=>$row[1],
   "german_level_event"=>$row[2],
   "title"=>$row[3],
   "location"=>$row[4],
   "date"=>$row[5],
   "start_time"=>$row[6],
   "end_time"=>$row[7],
   "min_attendees"=>$row[8],
   "max_attendees"=>$row[9],
   "description"=>$row[10]); */
   array_push($response,array('event_id'=>$row[0],
   'user_name_host'=>$row[1],
   'german_level_event'=>$row[2],
   'title'=>$row[3],
   'location'=>$row[4],
   'date'=>$row[5],
   'start_time'=>$row[6],
   'end_time'=>$row[7],
   'min_attendees'=>$row[8],
   'max_attendees'=>$row[9],
   'description'=>$row[10]));
   
//  $response = array("user_info.user_info_id"=>$row[0],"user_info.user_name"=>$row[1],"user_info.user_pass"=>$row[2],"profile_data.firstName"=>$row[5],"profile_data.lastName"=>$row[6],"profile_data.dob"=>$row[7],"profile_data.german_level"=>$row[8],"profile_data.description"=>$row[9]);
}

echo json_encode(array("event_data"=>$response));
 
?>