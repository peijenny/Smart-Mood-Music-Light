<?php
	
	header("Content-Type:text/html; charset=utf-8");
	
	$updateAccount = $_POST["updateAccount"];
	$updateName = $_POST["updateName"];
	$updateEmail = $_POST["updateEmail"];
	$updateGender = $_POST["updateGender"];
	$updateBirthday = $_POST["updateBirthday"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");

	
	if($conn)
	{
		//UPDATE `user` SET `name` = 'mis32', `gender` = 'female', `birthday` = '1999/09/09', `email` = 'mis0804@mail.com' WHERE `user`.`user_id` = 2;
		$q = "UPDATE `user` SET `name` = '$updateName', `gender` = '$updateGender', `birthday` = '$updateBirthday',
			`email` = '$updateEmail' WHERE account = '$updateAccount'";
		//$result = mysqli_query($conn, $q);
		if(mysqli_query($conn, $q))
		{
			echo "基本資料修改成功!!!";
		}
		else
		{
			echo "基本資料修改失敗!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}


	
	$conn -> close();


?>