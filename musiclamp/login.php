<?php
    header("Content-Type:text/html; charset=utf-8");

    $account = $_POST["account"];
	$password = $_POST["password"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	
	if($conn)
	{
		$q = "select account,password from user where account like '$account' and password like '$password'";
		$result = mysqli_query($conn, $q);
		
		if(mysqli_num_rows($result) > 0)
		{
			echo "登入成功!!!";
			
		}
		else
		{
			echo "登入失敗!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}

	$conn -> close();

?>