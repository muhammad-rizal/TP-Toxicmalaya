<?php  

	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		
		$nama = $_POST['nama'];
		$langkah = $_POST['langkah'];
		$salary = $_POST['salary'];

		$sql = "INSERT INTO 3x3 (nama,langkah) VALUES ('$nama','$langkah')";

		require_once('koneksi.php');

		if (mysqli_query($con,$sql)) {
			echo "Berhasil disimpan dalam database";
		}else{
			echo mysqli_error();
		}

		mysqli_close($con);
	}
?>
