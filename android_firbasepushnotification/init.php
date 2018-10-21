<?php

	$host="localhost";
	$db = "firebasepushnotification";
	$pass="";
	$user = "root";
	$con = mysqli_connect($host,$user,$pass,$db);
	if(!$con)
	{
		echo "Database not connected";
	}
	else
	{
		echo "connected";
	}

    

?>