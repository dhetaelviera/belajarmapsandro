<?php
	
	include_once('connection.php');

	$username=$_POST['username'];

	$nama=$_POST['nama'];
	$lat=$_POST['lat'];
	$lng=$_POST['lng'];
	
	$getdata=mysqli_query($koneksi, "SELECT*FROM user WHERE username='$username'");
	//$rows=mysqli_num_rows($getdata);
	
	//$query = "UPDATE user SET nama='$nama', email='$email', password='$password' WHERE username='$username'";

	//$exeupdate=mysqli_query($koneksi,$query);

	$response=array();

	if (mysqli_num_rows($getdata)!=0) {
		$query = "UPDATE user SET nama='$nama', email='$email', password='$password' WHERE username='$username' ";
		$exeupdate=mysqli_query($koneksi,$query);

		if ($exeupdate) {
		$response['code']=1;
		$response['message']="berhasil update";
		
	} else{
		$response['code']=0;
		$response['message']="gagal update";
	}
} else{
		$response['code']=0;
		$response['message']="gagal update";
	}

	

	echo json_encode($response);



?>