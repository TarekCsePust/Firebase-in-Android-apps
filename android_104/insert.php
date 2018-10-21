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

	$contact = $_POST['contact'];
	$contact = json_decode($contact);

    $user_name = $contact->{'name'};
    $user_email = $contact->{'email'};
    $profile_pic = $contact->{'profile_pic'};
    $upload_path = "upload/$user_name.jpg";

	$query= "insert into contact(id,name,email) values(NULL,'".$user_name."','".$user_email."');";
	if(mysqli_query($con,$query))
	{
		file_put_contents($upload_path,base64_decode($profile_pic));
		echo  json_encode(array('responce'=>'Insertion success'));
	}
	else
	{
		echo  json_encode(array('responce'=>'Insertion not success'));
	}

   mysqli_close($con);

?>