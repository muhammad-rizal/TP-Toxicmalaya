<?php
include('koneksi.php');
$sql1 = "SELECT * FROM 3x3 ORDER BY langkah ASC";
$query1 = mysqli_query($con, $sql1);

$sql2 = "SELECT * FROM 4x4 ORDER BY langkah ASC";
$query2 = mysqli_query($con, $sql2);

$sql3 = "SELECT * FROM 5x5 ORDER BY langkah ASC";
$query3 = mysqli_query($con, $sql3);

$no=1;
$no4=1;
$no5=1;
?>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <link rel="stylesheet" href="css/leaderboard.css">

    <title>Leaderboard</title>
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href="#">
          <img src="graphic/menu/logo.png">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link" href="mainmenu.php">Play</a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="#"> Leaderboard<span class="sr-only">(current)</span></a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container">
      <div class="row nt-3">
        <div class="col">
          <h2>Leaderboard 3x3</h2>
        </div>
      </div>
      <div class="row">
        <table id="rank">
          <tr>
            <th>Rank</th>
            <th>Nama</th>
            <th>Langkah</th>
          </tr>
          <?php foreach($query1 as $row){ ?>
          <tr>
            <td><?php echo $no++; ?></td>
            <td><?php echo $row['nama'] ?></td>
            <td><?php echo $row['langkah'] ?></td>
          </tr>
          <?php } ?>
        </table>
      </div>

      <div class="row nt-3">
        <div class="col">
          <h2>Leaderboard 4x4</h2>
        </div>
      </div>
      <div class="row">
        <table id="rank">
          <tr>
            <th>Rank</th>
            <th>Nama</th>
            <th>Langkah</th>
          </tr>
          <?php foreach($query2 as $row){ ?>
          <tr>
            <td><?php echo $no4++; ?></td>
            <td><?php echo $row['nama'] ?></td>
            <td><?php echo $row['langkah'] ?></td>
          </tr>
          <?php } ?>
        </table>
      </div>

      <div class="row nt-3">
        <div class="col">
          <h2>Leaderboard 5x5</h2>
        </div>
      </div>
      <div class="row">
        <table id="rank">
          <tr>
            <th>Rank</th>
            <th>Nama</th>
            <th>Langkah</th>
          </tr>
          <?php foreach($query3 as $row){ ?>
          <tr>
            <td><?php echo $no5++; ?></td>
            <td><?php echo $row['nama'] ?></td>
            <td><?php echo $row['langkah'] ?></td>
          </tr>
          <?php } ?>
        </table>
      </div>
    </div>

    <div style="bottom: 0;margin-top: 70px;width: 100%;position: relative;height:80px;line-height:0px;background:#3c3a3a;color:#fff;padding-left: 10px;">
      <img src="graphic/menu/tm.gif" style="margin-top: 17px;width: 100px;margin-left: 47%;">
    </div>
   
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script
    src="https://code.jquery.com/jquery-3.5.0.min.js"
    integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ="
    crossorigin="anonymous"></script>
  </body>
</html>
