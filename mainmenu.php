<?php
$data = file_get_contents('data/puzzle.json');
$menu = json_decode($data, true);

$menu = $menu["menu"];
?>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <title>Main Menu</title>
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href="#">
          <img src="graphic/logo.png" width="260">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item active">
              <a class="nav-link" href="#">Play <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Leaderboard</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Account</a>
            </li>
            <li class="nav-item">
              <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container">
      
      <div class="row nt-3">
        <div class="col">
          <h1>Play</h1>
        </div>
      </div>
    
      <div class="row">
        <?php foreach ($menu as $row) : ?>
        <div class="card mb-3">
          <img src="graphic/menu/<?= $row["gambar"]; ?>" class="card-img-top" alt="..." style="width: 1100px;">
          <div class="card-body">
            <h5 class="card-title"><?= $row["nama"]; ?></h5>
            <p class="card-text"><?= $row["komen"]; ?></p>
            <p class="card-text"><a href="<?= $row["href"]; ?>" class="btn btn-primary">Play Now</a></p>
          </div>
        </div>\
        <?php endforeach; ?>
      </div>
      
    </div>
    <div class="footer" style="width: 100%;height: 115px;padding-left: 0px;line-height: 0px;background: #333;color: #fff;">
    <img src="graphic/menu/tm.gif" style="width: 180px;margin-left: 45%;">
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