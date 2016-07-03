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
$user_name = $_POST["user_name"];  
$user_pass = $_POST["user_pass"];  

$sql = "insert into user_info values(DEFAULT,'$user_name','$user_pass');";
 

if (mysqli_query($con, $sql)) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>