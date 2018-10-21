<?php

	$host="localhost";
	$db = "android_104";
	$pass="";
	$user = "root";
	$con = mysqli_connect($host,$user,$pass,$db);
	if(!$con)
	{
		echo "Database not connected";
	}

	$query= "select * from contact;"; 
	$result = mysqli_query($con,$query);
	$response = array();
    while ($row = mysqli_fetch_array($result))
    {
    	array_push($response,array("name"=>$row["name"],"id"=>$row["id"],"email"=>$row["email"]));
    	
    }

    echo json_encode($response);
  	mysqli_close($con);

?>