<?php

	header("Content-Type:text/html; charset=utf-8");
	
	$newAccount = $_POST["newAccount"];
	$newPassword = $_POST["newPassword"];
	$newName = $_POST["newName"];
	$newEmail = $_POST["newEmail"];
	$newGender = $_POST["newGender"];
	$newBirthday = $_POST["newBirthday"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	
	
	if($conn)
	{
		$q = "select account,password from user where account like '$newAccount' or password like '$newPassword'";
		$result = mysqli_query($conn, $q);
		if(mysqli_num_rows($result) > 0)
		{
			echo "Duplicate Account or Password";
		}
		else
		{
			$q2 = "INSERT INTO `user` (`account`,`password`,`name`,`gender`,`birthday`,`email`) VALUES ('$newAccount','$newPassword','$newName','$newGender','$newBirthday','$newEmail')";
			if(mysqli_query($conn, $q2))
			{
				echo "註冊成功!!!";
			}
			else
			{
				echo "註冊失敗!!!";
			}
		}
	}
	else
	{
		echo "沒有連線....";
	}
	
	$conn -> close();


?>