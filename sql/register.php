/* <?php  
 require "init.php";  
 $name = $_POST["name"];  
 $user_name = $_POST["user_name"];  
 $user_pass = $_POST["user_pass"];  
 $sql_query = "insert into user_info values('$name','$user_name','$user_pass');"; 
 mysqli_query()
 ?>  
 */
 
 <?php
$db_name="webappdb";
$mysql_user="root";
$mysql_pass="admin";
$server_name="localhost";

$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
if(!$con)
{
echo "Connection fail..".mysqli_connect_error();
}
else
{
echo "<h3>Database connection successful..</h3>";
}
$user_name_host = $_POST["user_name_host"];  
$german_level_event = $_POST["german_level_event"];  
$title = $_POST["title"];
$location = $_POST["location"];
$date = $_POST["date"];
$start_time = $_POST["start_time"];
$end_time = $_POST["end_time"];
$min_attendees = $_POST["min_attendees"];
$max_attendees = $_POST["max_attendees"];
$description = $_POST["description"];

$sql = "insert into event values(DEFAULT,'$user_name_host','$german_level_event','$title','$location','$date','$start_time','$end_time','$min_attendees','$max_attendees','$description');";
 

if (mysqli_query($con, $sql)) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>