<?php

	include_once('connection.php');

	$username=$_POST['username'];
	echo "string";
	echo ($username);

	$getdata=mysqli_query($koneksi, "SELECT*FROM user WHERE username='$username'");
	$rows=mysqli_num_rows($getdata);
	$delete= "DELETE FROM user WHERE username='$username'";
	$exedelete=mysqli_query($koneksi,$delete);

	$response=array();

	if ($rows>0) {
		# code...
	
	if ($exedelete) {
		$response['code']=1;
		$response['message']="berhasil dihapus";
		
	} else{
		$response['code']=0;
		$response['message']="gagal dihapus";
	}
} else{
	$response['code']=0;
	$response['message']="gdata gak ada";
}

	echo json_encode($response);

?>