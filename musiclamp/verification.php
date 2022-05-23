<?php
	
	header("Content-Type:text/html; charset=utf-8");
	
	$changeAccount = $_POST["changeAccount"];
	$changePassword = $_POST["changePassword"];

	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");

	
	if($conn)
	{
		//UPDATE `user` SET `name` = 'mis32', `gender` = 'female', `birthday` = '1999/09/09', `email` = 'mis0804@mail.com' WHERE `user`.`user_id` = 2;
		$q = "UPDATE `user` SET `password` = '$changePassword' WHERE `user`.`account` = '$changeAccount'";
		//$result = mysqli_query($conn, $q);
		if(mysqli_query($conn, $q))
		{
			echo "密碼修改成功!!!";
		}
		else
		{
			echo "密碼修改失敗!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}


	
	$conn -> close();


?>