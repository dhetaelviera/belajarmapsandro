<?php
	include_once('connection.php');

	$username=$_POST['username'];
	$nama=$_POST['nama'];
	$email=$_POST['email'];
	$password=$_POST['password'];

	$insert="INSERT INTO user (username,nama,email,password) VALUES ('$username','$nama','$email','$password')";

	$exeinsert=mysqli_query($koneksi,$insert);

	$response=array();

	if ($exeinsert) {
		$response['code']=1;
		$response['message']="Data ditambahkan";
	} else{
		$response['code']=0;
		$response['message']="Data tidak masuk";
	}

	echo json_encode($response);

?>