<?php  
	
	require_once 'koneksi.php';
	
	$sql = "SELECT * FROM 5x5 ORDER BY langkah ASC";

	$result = array();
	$r = mysqli_query($con,$sql);

	while ($row = mysqli_fetch_array($r)) {
		
		array_push($result, array(
				"id" => $row['id'],
				"nama" => $row['nama'],
				"langkah" => $row['langkah']
			));
	}
	
	echo json_encode(array('5x5' => $result));
	mysqli_close($con);

?>