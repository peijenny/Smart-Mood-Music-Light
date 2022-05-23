<?php
    header("Content-Type:text/html; charset=utf-8");

    $userAccount = $_POST["userAccount"];
	$userEmail = $_POST["userEmail"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	
	if($conn)
	{
		$q = "select account,email from user where account like '$userAccount' and email like '$userEmail'";
		$result = mysqli_query($conn, $q);
		
		if(mysqli_num_rows($result) > 0)
		{
			echo "帳號、Email正確!!!";
			$a = rand(1000000,9999999);

			$subject = "音樂檯燈APP-驗證碼"; 
			$message = "這是您的驗證碼: $a"; 
			$headers = 'From: MusicLamp08@gmail.com'; 

			if(mail($userEmail, $subject, $message, $headers)) { 
				echo 'Email sent successfully!'; 
				echo $a; 
			} else { 
				die('Failure: Email was not sent!'); 
			} 
			
		}
		else
		{
			echo "帳號、Email錯誤!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}

	$conn -> close();

?>