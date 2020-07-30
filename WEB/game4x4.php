<?php
$kode = $_GET["kode"];

include('koneksi.php');
$sql1 = "SELECT * FROM 4x4 ORDER BY langkah ASC LIMIT 1";
$query1 = mysqli_query($con, $sql1);
?>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="background.css">
    <link rel="stylesheet" href="css/login.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <style type="text/css">
      .tile1, .tile2, .tile3, .tile4, .tile5, .tile6, .tile7, .tile8, .tile9, .tile10, .tile11, .tile12, .tile13, .tile14, .tile15, .tile16{
        display: table-cell;
        width: 90px;
        height: 90px;
        border: 1px solid white;
        background: url(graphic/Level/<?php echo $kode; ?>.png); 
        cursor: pointer;
      }
        .tile1 {background-position: 0px 0px;}
        .tile2 {background-position: -90px 0px;}
        .tile3 {background-position: -180px 0px;}
        .tile4 {background-position: -270px 0px;}
        .tile5 {background-position: 0px -90px;}
        .tile6 {background-position: -90px -90px;}
        .tile7 {background-position: -180px -90px;}
        .tile8 {background-position: -270px -90px;}
        .tile9 {background-position: 0px -180px;}
        .tile10 {background-position: -90px -180px;}
        .tile11 {background-position: -180px -180px;}
        .tile12 {background-position: -270px -180px;}
        .tile13 {background-position: 0px -270px;}
        .tile14 {background-position: -90px -270px;}
        .tile15 {background-position: -180px -270px;}
        .tile16 {background: white; cursor: default;}
    </style>
    <title>Cartoon Puzzle</title>
  </head>
  <body>
  <div style="padding-left: 35%;padding-top: 30px;">
      <div style="background-image: url(graphic/Ingame/record.png);background-repeat: no-repeat;width: 386px;height: 169px;margin-bottom: -210px;padding-top: 45px;padding-left: 10px;">
        <table>
          <tr>
            <th><h3 id="num">0</h3></th>
            <?php foreach($query1 as $row){ ?>
              <th><h3 style="padding-left: 230px;"><?php echo $row['langkah'] ?></h3></th>
            <?php } ?>
          </tr>
        </table>
      </div>
    </div>
    <header class="container">
      <section class="content" style="padding-left: 30%;">
        <div id="table" style="display: table;">
         <div id="row1" style="display: table-row;">
            <div id="cell11" class="tile1" onClick="clickTile(1,1);"></div>
            <div id="cell12" class="tile2" onClick="clickTile(1,2);"></div>
            <div id="cell13" class="tile3" onClick="clickTile(1,3);"></div>
            <div id="cell14" class="tile4" onClick="clickTile(1,4);"></div>
         </div>
         <div id="row2" style="display: table-row;">
            <div id="cell21" class="tile5" onClick="clickTile(2,1);"></div>
            <div id="cell22" class="tile6" onClick="clickTile(2,2);"></div>
            <div id="cell23" class="tile7" onClick="clickTile(2,3);"></div>
            <div id="cell24" class="tile8" onClick="clickTile(2,4);"></div>
         </div>
         <div id="row3" style="display: table-row;">
            <div id="cell31" class="tile9" onClick="clickTile(3,1);"></div>
            <div id="cell32" class="tile10" onClick="clickTile(3,2);"></div>
            <div id="cell33" class="tile11" onClick="clickTile(3,3);"></div>
            <div id="cell34" class="tile12" onClick="clickTile(3,4);"></div>
         </div>
         <div id="row4" style="display: table-row;">
            <div id="cell41" class="tile13" onClick="clickTile(4,1);"></div>
            <div id="cell42" class="tile14" onClick="clickTile(4,2);"></div>
            <div id="cell43" class="tile15" onClick="clickTile(4,3);"></div>
            <div id="cell44" class="tile16" onClick="clickTile(4,4);"></div>
        </div>
        </div>
        <img src="graphic/Ingame/acakbtn.png" style="width: 100px;height: 70px;margin-top: 20px;margin-right: 20px;" onClick="shuffle();">
        <img src="graphic/Ingame/homebtn.png" style="width: 100px;height: 70px;margin-top: 20px;margin-right: 50px;" onClick="window.location.href='mainmenu.php'">
      </section>
      <img src="graphic/Level/<?php echo $kode; ?>.png" style="width: 130px;height: 130px;margin-top: 100px;margin-right: 50px;">
    </header>
    <!-- Optional JavaScript -->
    <script src="js/script4x4.js"></script>
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  </body>
</html>
