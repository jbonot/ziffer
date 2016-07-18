<?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="";
$server_name="localhost";

$user_recipient = !empty($_POST['user_recipient']) ? $_POST['user_recipient'] : '';

// Create connection
$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "SELECT n.notifications_id, n.event_id, n.event_name,
		n.message_type, n.timestamp, n.read_status,
		n.user_sender, profile_data.firstName, profile_data.lastName,
		profile_data.image as sender_image
		FROM profile_data JOIN
			(SELECT notifications.notifications_id, notifications.event_id,
			notifications.message_type, notifications.timestamp,
			notifications.read_status, notifications.user_sender,
			event.title as event_name
			FROM notifications JOIN event
			ON event.event_id = notifications.event_id
			WHERE user_recipient='$user_recipient') AS n
		ON n.user_sender = profile_data.user_name
		ORDER BY n.timestamp DESC;";


$result = mysqli_query($con, $sql);

$response = array();
while($row = mysqli_fetch_array($result)){
   array_push($response,array(
   'notification_id' => $row[0],
   'event_id' => $row[1],
   'event_name' => $row[2],
   'message_type' => $row[3],
   'timestamp' => $row[4],
   'read_status' => $row[5],
   'user_sender' => $row[6],
   'sender_firstname' => $row[7],
   'sender_lastname' => $row[8],
   'sender_image' => $row[9]));
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

echo json_encode(array("notifications"=>utf8ize($response)));

?>
